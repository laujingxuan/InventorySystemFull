package team5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team5.model.UsageRecord;
import team5.repo.UsageRepo;

@Service
public class UsageRecordServiceImpl implements UsageRecordService {
	
	@Autowired
	UsageRepo urepo;
	
	@Transactional
	public void addUsage(UsageRecord ur) {
		urepo.save(ur);
	}
	@Transactional
	public UsageRecord findUsageById(long id) {
		return urepo.findById(id).get();
	}
	//@Transactional
	//public List<UsageRecord> checkTransectionHistory(long id ) {
	//	return urepo.findUsageRecord(id);
	//}
	@Transactional
	public List<UsageRecord> listUsageRecord(){
		return urepo.findAll();
	}
	

	
}
