package team5.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team5.model.UsageRecordDetail;



public interface UsageRecordDetailRepository extends JpaRepository<UsageRecordDetail, Long> {

}