package kdkim.module.spring_batch.entity.composite_key;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@Builder
public class DailyCompositeKey implements Serializable {
    private Long account;
    private Date date;
}
