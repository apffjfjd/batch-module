package kdkim.module.spring_batch.reader;

import kdkim.module.spring_batch.entity.Batch;
import org.springframework.batch.item.ItemReader;

public class TotalDailyReader implements ItemReader<Batch> {

    @Override
    public Batch read(){
        return null;
    }
}
