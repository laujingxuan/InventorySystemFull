package team5.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team5.model.Product;
import team5.model.UsageRecord;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {

}
