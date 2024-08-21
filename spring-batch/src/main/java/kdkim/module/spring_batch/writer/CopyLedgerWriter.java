package kdkim.module.spring_batch.writer;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.repository.BatchRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CopyLedgerWriter{
    @Bean
    public RepositoryItemWriter<Batch> batchWriter(BatchRepository batchRepository) {
        RepositoryItemWriter<Batch> writer = new RepositoryItemWriter<>();
        writer.setRepository(batchRepository);
        writer.setMethodName("save");
        return writer;
    }
}
