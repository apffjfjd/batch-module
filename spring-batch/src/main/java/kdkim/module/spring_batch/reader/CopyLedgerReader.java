package kdkim.module.spring_batch.reader;

import kdkim.module.spring_batch.entity.Ledger;
import kdkim.module.spring_batch.repository.LedgerRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class CopyLedgerReader implements ItemReader<Ledger> {

    private final LedgerRepository ledgerRepository;
    private Iterator<Ledger> ledgerIterator;

    @Autowired
    public CopyLedgerReader(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public Ledger read() {
        // Lazy initialization of ledgerIterator
        if (ledgerIterator == null) {
            List<Ledger> ledgers = ledgerRepository.findAll();
            System.out.println("-----------------------------------------");
            System.out.println(ledgers);
            System.out.println("-----------------------------------------");
            ledgerIterator = ledgers.iterator();
        }

        // Return the next Ledger object if available, otherwise return null to indicate the end
        if (ledgerIterator.hasNext()) {
            return ledgerIterator.next();
        } else {
            ledgerIterator = null;
            System.out.println("------------null--------------");
            return null; // Indicate the end of the data
        }
    }

}
