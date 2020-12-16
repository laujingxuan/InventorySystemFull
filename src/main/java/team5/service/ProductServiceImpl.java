package team5.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	     
	         
	      public List<Product> listAllpart(String keyword) {
	            if (keyword != null) {
	                return prepo.searchpart(keyword);
	            }
	            return prepo.findAll();
	        }
	         
	       
	    
}
