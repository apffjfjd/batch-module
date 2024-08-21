package kdkim.module.spring_batch.writer;

import kdkim.module.spring_batch.entity.Daily;
import kdkim.module.spring_batch.repository.DailyRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TotalDailyWriter {
    @Bean
    public RepositoryItemWriter<Daily> dailyWriter(DailyRepository dailyRepository) {
        RepositoryItemWriter<Daily> writer = new RepositoryItemWriter<>();
        writer.setRepository(dailyRepository);
        writer.setMethodName("save");
        return writer;
    }
}
