package team5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team5.model.UsageRecord;
import team5.model.UsageRecordDetail;
import team5.repo.UsageRecordDetailRepository;

@Service
public class UsageRecordDetailServiceImpl implements UsageRecordDetailService {
	
	@Autowired
	UsageRecordDetailRepository urdrepo;
	
	@Transactional
	public void addUsage(UsageRecordDetail ur) {
		urdrepo.save(ur);
	}

}