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

    /**
     * 첫 번째 배치 작업을 정의합니다.
     *
     * @param jobRepository 잡 리포지토리
     * @param copyLedgerStep 실행할 스텝
     * @return Job 객체
     */
    @Bean
    public Job copyLedgerJob(JobRepository jobRepository, Step copyLedgerStep) {
        // JobBuilder를 사용하여 잡 이름과 JobRepository를 설정하고, copyLedgerStep을 포함하는 잡을 생성합니다.
        return new JobBuilder("copyLedgerJob", jobRepository)
                .start(copyLedgerStep) // 이 잡은 copyLedgerStep으로 시작합니다.
                .build();
    }

    /**
     * 두 번째 배치 작업을 정의합니다.
     *
     * @param jobRepository 잡 리포지토리
     * @param totalDailyStep 실행할 스텝
     * @return Job 객체
     */
    @Bean
    public Job totalDailyJob(JobRepository jobRepository, Step totalDailyStep) {
        // JobBuilder를 사용하여 잡 이름과 JobRepository를 설정하고, totalDailyStep을 포함하는 잡을 생성합니다.
        return new JobBuilder("totalDailyJob", jobRepository)
                .start(totalDailyStep) // 이 잡은 totalDailyStep으로 시작합니다.
                .build();
    }

    /**
     * 첫 번째 잡에서 사용할 Step을 정의합니다.
     *
     * @param jobRepository 잡 리포지토리
     * @param ptm 트랜잭션 매니저
     * @param ledgerReader Ledger 엔티티를 읽어오는 리더
     * @param itemProcessor Ledger를 Batch로 변환하는 프로세서
     * @param batchWriter 변환된 Batch 데이터를 저장하는 라이터
     * @return Step 객체
     */
    @Bean
    public Step copyLedgerStep(
            JobRepository jobRepository,
            PlatformTransactionManager ptm,
            RepositoryItemReader<Ledger> ledgerReader,
            ItemProcessor<Ledger, Batch> itemProcessor,
            RepositoryItemWriter<Batch> batchWriter
    ) {
        // StepBuilder를 사용하여 Step을 생성하며, Ledger 타입의 데이터를 읽고 Batch 타입으로 처리한 후 저장합니다.
        return new StepBuilder("copyLedgerStep", jobRepository)
                .<Ledger, Batch>chunk(100, ptm) // 청크 단위로 데이터를 처리하며, 트랜잭션 매니저를 사용합니다.
                .reader(ledgerReader) // Ledger 데이터를 읽어오는 리더를 설정합니다.
                .processor(itemProcessor) // Ledger 데이터를 Batch로 변환하는 프로세서를 설정합니다.
                .writer(batchWriter) // 변환된 Batch 데이터를 저장하는 라이터를 설정합니다.
                .build();
    }

    /**
     * 두 번째 잡에서 사용할 Step을 정의합니다.
     *
     * @param jobRepository 잡 리포지토리
     * @param ptm 트랜잭션 매니저
     * @param batchReader Batch 엔티티를 읽어오는 리더
     * @param itemProcessor Batch를 Daily로 변환하는 프로세서
     * @param dailyWriter 변환된 Daily 데이터를 저장하는 라이터
     * @return Step 객체
     */
    @Bean
    public Step totalDailyStep(
            JobRepository jobRepository,
            PlatformTransactionManager ptm,
            RepositoryItemReader<Batch> batchReader,
            ItemProcessor<Batch, Daily> itemProcessor,
            RepositoryItemWriter<Daily> dailyWriter
    ) {
        // StepBuilder를 사용하여 Step을 생성하며, Batch 타입의 데이터를 읽고 Daily 타입으로 처리한 후 저장합니다.
        return new StepBuilder("totalDailyStep", jobRepository)
                .<Batch, Daily>chunk(100, ptm) // 청크 단위로 데이터를 처리하며, 트랜잭션 매니저를 사용합니다.
                .reader(batchReader) // Batch 데이터를 읽어오는 리더를 설정합니다.
                .processor(itemProcessor) // Batch 데이터를 Daily로 변환하는 프로세서를 설정합니다.
                .writer(dailyWriter) // 변환된 Daily 데이터를 저장하는 라이터를 설정합니다.
                .build();
    }
}
