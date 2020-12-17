package team5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team5.model.UsageRecordDetail;
import team5.repo.UsageRecordDetailRepo;


@Service
public class UsageRecordDetailServiceImpl implements UsageRecordDetailService {
	
	
	@Autowired
	UsageRecordDetailRepo urdrepo;
	
	public void save(UsageRecordDetail urd) {
		urdrepo.save(urd);
	}
	
	public void delete(UsageRecordDetail urd) {
		urdrepo.delete(urd);
	}

	@Override
	public UsageRecordDetail findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsageRecordDetail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
