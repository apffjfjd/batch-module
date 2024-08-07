package kdkim.module.spring_batch.config;

import kdkim.module.spring_batch.entity.Ledger;
import kdkim.module.spring_batch.repository.LedgerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class BatchJobConfiguration {
    @Bean
    public CommandLineRunner loadData(LedgerRepository repository) {
        return args -> {
            List<Ledger> ledgers = new ArrayList<>();
            Map<Long, Long> accountBalances = new HashMap<>(); // 계좌 번호별 잔액을 추적하기 위한 맵

            for (int i = 1; i <= 10; i++) {
                long accountNumber = 100000000000L + (long)(Math.random() * 10);
                boolean classification = Math.random() < 0.5;
                long deposit = 0;
                long withdrawal = 0;
                long previousBalance = accountBalances.getOrDefault(accountNumber, 0L);

                if (classification) {
                    deposit = (long)(Math.random() * 100000);
                    previousBalance += deposit;
                } else {
                    withdrawal = (long)(Math.random() * 10000);
                    previousBalance -= withdrawal;
                }

                // 계좌 번호별 마지막 잔액 업데이트
                accountBalances.put(accountNumber, previousBalance);

                ledgers.add(Ledger.builder()
                        .account(accountNumber)
                        .classification(classification)
                        .deposit(deposit)
                        .withdrawal(withdrawal)
                        .balance(previousBalance)
                        .build());
            }

            repository.saveAll(ledgers);
        };
    }
}
