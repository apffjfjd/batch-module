package kdkim.module.spring_batch.processor;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.entity.Ledger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CopyLedgerProcessor {
    @Bean
    public ItemProcessor<Ledger, Batch> ledgerToBatchProcessor() {
        return ledger -> Batch.builder()
                .ledgerId(ledger.getId())
                .account(ledger.getAccount())
                .balance(ledger.getBalance())
                .deposit(ledger.getDeposit())
                .withdrawal(ledger.getWithdrawal())
                .classification(ledger.getClassification())
                .transactionTime(ledger.getTransactionTime())
                .build();
    }
}
