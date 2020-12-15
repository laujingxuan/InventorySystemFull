package team5.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team5.model.Product;
import team5.model.User;
import team5.repo.ProductRepository;

@Service
@Transactional
public class ProductImplementation implements ProductInterface {
	
	@Autowired
	ProductRepository productRepo;
	
	@Override
	public List<Product> findAll() {
		List<Product> products = productRepo.findAll();
		return products;
	}
	
	@Override
	public Product findByProductId(long id) {
		return productRepo.findById(id).get();
	}

	@Override
	public void createProduct(Product product) {
		productRepo.save(product);
		
	}

	@Override
	public void updateProduct(Product product) {
		productRepo.save(product);
		
	}	

	@Override
	public void deleteProduct(Product product) {
		productRepo.delete(product);
		
	}

	@Override
	public Product findProductById(long id) {
		return productRepo.findById(id).get();
	}

	@Override
	public List<Product> listAllProducts(String keyword) {		
		System.out.println(keyword);
			if(keyword != null) {
				return productRepo.search(keyword);
			}
			return productRepo.findAll();		
	}

//	@Override
//	public List<Product> productsByName() {
//		return productRepo.findProductByName();
//	}

}
