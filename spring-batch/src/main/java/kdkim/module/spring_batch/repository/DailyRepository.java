package kdkim.module.spring_batch.repository;

import kdkim.module.spring_batch.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily, Long> {
}
