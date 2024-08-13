package kdkim.module.spring_batch.writer;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.repository.BatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CopyLedgerWriter implements ItemWriter<Batch> {

    private BatchRepository batchRepository;
    @Override
    public void write(Chunk<? extends Batch> chunk){
        batchRepository.saveAll(chunk.getItems());
        System.out.println(batchRepository.findAll());
    }
}
