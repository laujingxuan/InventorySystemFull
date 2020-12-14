package team5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team5.model.Product;
import team5.model.User;
import team5.repo.ProductRepository;

@Service
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
}
