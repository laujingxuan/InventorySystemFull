package team5.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team5.model.Product;
import team5.repo.ProductRepo;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepo prepo;
	
	@Transactional
	public void save(Product product) {
		prepo.save(product);
	}
	
	@Transactional
	public Product findById(Long id) {
		return prepo.findById(id).get();
	}
	
	@Transactional
	public Product findByName(String name) {
		return prepo.findByName(name).get(0);
	}
	
	@Transactional
	public ArrayList<Product> findAll(){
		return prepo.findAll(); 
	}
	

	
	
	public List<Product> listAllProducts(String keyword) {		
		System.out.println(keyword);
			if(keyword != null) {
				return prepo.search(keyword);
			}
			return prepo.findAll();		
	}
	
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
	     
	
	/*
	@Override
	public void updateStock(Long quantity, Long id) {		
		productRepo.reduceStock(quantity, id);	
	}*/
	
	@Override
	public void delete(Product product) {
		prepo.delete(product);
		
	}

}
