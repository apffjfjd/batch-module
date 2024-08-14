package kdkim.module.spring_batch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kdkim.module.spring_batch.entity.composite_key.DailyCompositeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Daily {

    @EmbeddedId
    private DailyCompositeKey id;

    @Column(name = "deposit_per_day")
    private Long deposit;
    @Column(name = "withdrawal_per_day")
    private Long withdrawal;


}
