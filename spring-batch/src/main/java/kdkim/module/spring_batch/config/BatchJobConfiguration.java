package kdkim.module.spring_batch.config;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.entity.Daily;
import kdkim.module.spring_batch.entity.Ledger;
import kdkim.module.spring_batch.entity.composite_key.DailyCompositeKey;
import kdkim.module.spring_batch.reader.CopyLedgerReader;
import kdkim.module.spring_batch.reader.TotalDailyReader;
import kdkim.module.spring_batch.repository.BatchRepository;
import kdkim.module.spring_batch.repository.LedgerRepository;
import kdkim.module.spring_batch.writer.CopyLedgerWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
@AllArgsConstructor
public class BatchJobConfiguration extends DefaultBatchConfiguration {

    @Bean
    public Job copyLedgerJob(JobRepository jobRepository, Step copyLedgerStep){
        return new JobBuilder("copyLedgerJob",jobRepository)
                .start(copyLedgerStep)
                .build();
    }

    @Bean
    public Job totalDailyJob(JobRepository jr, Step totalDailyStep){
        return new JobBuilder("totalDailyJob", jr)
                .start(totalDailyStep)
                .build();
    }

    @Bean
    public Step copyLedgerStep(
            JobRepository jobRepository,
            PlatformTransactionManager ptm,
            RepositoryItemReader<Ledger> ledgerReader,
            ItemProcessor<Ledger, Batch> itemProcessor,
            RepositoryItemWriter<Batch> batchWriter
    ){
        return new StepBuilder("copyLedgerStep", jobRepository)
                .<Ledger, Batch>chunk(10, ptm)
                .reader(ledgerReader)
                .processor(itemProcessor)
                .writer(batchWriter)
                .build();
    }

    @Bean
    public Step totalDailyStep(JobRepository jobRepository,
                               PlatformTransactionManager ptm,
                               RepositoryItemReader<Batch> batchReader,
                               ItemProcessor<Batch, Daily> itemProcessor,
                               RepositoryItemWriter<Daily> dailyWriter
    ){
        return new StepBuilder("totalDailyStep", jobRepository)
                .<Batch, Daily>chunk(10,ptm)
                .reader(batchReader)
                .processor(itemProcessor)
                .writer(dailyWriter)
                .build();
    }


    @Bean
    public ItemProcessor<Batch, Daily> batchToDailyProcessor() {
        DailyCompositeKey dck = new DailyCompositeKey();
        return batch -> Daily.builder()
                .id()
                .build();
    }


}
