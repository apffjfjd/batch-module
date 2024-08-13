package kdkim.module.spring_batch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "batch")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(name = "ledger_id")
    private Long ledgerId;
    @Column(name = "account")
    private Long account;
    @Column(name = "classification")
    private String classification;
    @Column(name = "deposit")
    private Long deposit;
    @Column(name = "withdrawal")
    private Long withdrawal;
    @Column(name = "balance")
    private Long balance;
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;
}
