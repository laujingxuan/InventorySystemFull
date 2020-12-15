package team5.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team5.model.UsageRecord;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {
	
	//@Query("Select u from UsageRecord u where u.usageRecordDetail.product.id =:pid")
	//public List<UsageRecord> findUsageRecord(@Param("pid")long pid);
}
