package kdkim.module.spring_batch.repository;

import kdkim.module.spring_batch.entity.Daily;
import kdkim.module.spring_batch.entity.composite_key.DailyCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRepository extends JpaRepository<Daily, DailyCompositeKey> {
}
