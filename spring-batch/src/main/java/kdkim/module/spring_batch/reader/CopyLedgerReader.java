package kdkim.module.spring_batch.reader;

import kdkim.module.spring_batch.entity.Ledger;
import kdkim.module.spring_batch.repository.LedgerRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CopyLedgerReader {

    @Bean
    public RepositoryItemReader<Ledger> ledgerReader(LedgerRepository ledgerRepository) {
        RepositoryItemReader<Ledger> reader = new RepositoryItemReader<>();
        reader.setRepository(ledgerRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return reader;
    }



}
