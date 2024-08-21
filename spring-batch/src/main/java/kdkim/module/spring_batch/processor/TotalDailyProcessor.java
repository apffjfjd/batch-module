package kdkim.module.spring_batch.processor;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.entity.Daily;
import kdkim.module.spring_batch.entity.composite_key.DailyCompositeKey;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TotalDailyProcessor {

    @Bean
    public ItemProcessor<Batch, Daily> batchToDailyProcessor() {
        Map<DailyCompositeKey, Daily> dailyMap = new HashMap<>();

        return batch -> {
            // Composite Key 생성
            DailyCompositeKey key = DailyCompositeKey.builder()
                    .account(batch.getAccount())
                    .date(batch.getTransactionTime().toLocalDate())
                    .build();

            // 이미 존재하는 Daily 엔티티가 있으면 업데이트, 없으면 새로 생성
            Daily existingDaily = dailyMap.get(key);

            if (existingDaily == null) {
                // Daily 엔티티가 존재하지 않으면 새로운 객체 생성
                existingDaily = Daily.builder()
                        .id(new DailyCompositeKey(0000000000L, LocalDate.of(1000, 01, 01)))
                        .depositPerDay(0L)
                        .withdrawalPerDay(0L)
                        .build();
            }

            // 입금 및 출금액 합산
            Daily updatedDaily = Daily.builder()
                    .id(key)
                    .depositPerDay(existingDaily.getDepositPerDay() + (batch.getDeposit() != null ? batch.getDeposit() : 0L))
                    .withdrawalPerDay(existingDaily.getWithdrawalPerDay() + (batch.getWithdrawal() != null ? batch.getWithdrawal() : 0L))
                    .build();

            // 업데이트된 Daily 객체를 맵에 저장
            dailyMap.put(key, updatedDaily);

            return updatedDaily;
        };
    }
}
