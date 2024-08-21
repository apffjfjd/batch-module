package kdkim.module.spring_batch.reader;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.repository.BatchRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TotalDailyReader{

    @Bean
    public RepositoryItemReader<Batch> batchReader(BatchRepository batchRepository) {
        RepositoryItemReader<Batch> reader = new RepositoryItemReader<>();
        reader.setRepository(batchRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("batchId", Sort.Direction.ASC));
        return reader;
    }
}
