package kdkim.module.spring_batch.repository;

import kdkim.module.spring_batch.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {
}
