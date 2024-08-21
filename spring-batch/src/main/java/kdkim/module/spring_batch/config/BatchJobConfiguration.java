package kdkim.module.spring_batch.config;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.entity.Daily;
import kdkim.module.spring_batch.entity.Ledger;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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
    public Job totalDailyJob(JobRepository jobRepository, Step totalDailyStep){
        return new JobBuilder("totalDailyJob", jobRepository)
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
                .<Ledger, Batch>chunk(100, ptm)
                .reader(ledgerReader)
                .processor(itemProcessor)
                .writer(batchWriter)
                .build();
    }

    @Bean
    public Step totalDailyStep(
            JobRepository jobRepository,
            PlatformTransactionManager ptm,
            RepositoryItemReader<Batch> batchReader,
            ItemProcessor<Batch, Daily> itemProcessor,
            RepositoryItemWriter<Daily> dailyWriter
    ){
        return new StepBuilder("totalDailyStep", jobRepository)
                .<Batch, Daily>chunk(100,ptm)
                .reader(batchReader)
                .processor(itemProcessor)
                .writer(dailyWriter)
                .build();
    }
}
