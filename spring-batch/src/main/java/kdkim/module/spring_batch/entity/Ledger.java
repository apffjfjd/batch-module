package kdkim.module.spring_batch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ledger")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account")
    private Long account;
    @Column(name = "classification")
    private Boolean classification;
    @Column(name = "deposit")
    private Long deposit;
    @Column(name = "withdrawal")
    private Long withdrawal;
    @Column(name = "balance")
    private Long balance;
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

}
