package team5.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team5.model.UsageRecord;

public interface UsageRecordService{

	
	public void addUsage(UsageRecord ur);
	public UsageRecord findUsageById(long id);
	//public List<UsageRecord> checkTransectionHistory(long id );
	public List<UsageRecord> listUsageRecord();
}
