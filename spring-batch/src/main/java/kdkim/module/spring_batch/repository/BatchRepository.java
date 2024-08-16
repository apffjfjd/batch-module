package kdkim.module.spring_batch.repository;

import kdkim.module.spring_batch.entity.Batch;
import kdkim.module.spring_batch.entity.Daily;
import kdkim.module.spring_batch.entity.composite_key.DailyCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    default List<Daily> findAllBy(){
        return null;
    }
}
