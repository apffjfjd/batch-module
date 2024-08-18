package kdkim.module.spring_batch.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BatchDto {
    private Long account;
    private Long deposit;
    private Long withdrawal;
    private LocalDate transactionDate;
}
