package team5.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team5.model.Product;
import team5.repo.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository prepo;
	
	@Transactional
	public ArrayList<String> FindAllPartNumber(){
		List<Product> product = prepo.findAll();
		ArrayList<String> partnum = new ArrayList<String>();
		for (Iterator<Product> iterator = product.iterator(); iterator.hasNext();) {
			Product product2 = (Product) iterator.next();
			partnum.add(product2.getPartNumber());
			
		}
		return partnum;
	}
	
	
	    public List<Product> listAll(String keyword) {
	        if (keyword != null) {
	            return prepo.search(keyword);
	        }
	        return prepo.findAll();
	    }
	     
	    @Override
	    public Page<Product> findBookNoCriteria(Integer page, Integer size) {
	        Sort sort = Sort.by(Sort.Direction.ASC,"id");
	        Pageable pageable=PageRequest.of(page,size, sort);
	        return prepo.findAll(pageable);
	    }
}
