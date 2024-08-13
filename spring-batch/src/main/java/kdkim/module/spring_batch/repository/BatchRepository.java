package kdkim.module.spring_batch.repository;

import kdkim.module.spring_batch.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Long> {
}
