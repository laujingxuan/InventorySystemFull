package team5.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import team5.model.Product;
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
	public Product createProduct(Product product) {
		return productRepo.save(product);
		
	}

	@Override
	public Product updateProduct(Product product) {
		return productRepo.save(product);
		
	}	

	@Override
	public void deleteProduct(Product product) {
		productRepo.delete(product);
		
	}
	
	@Override
	public void deleteById(Long id) {
		productRepo.deleteById(id);
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

	@Override
	public void updateStock(Long quantity, Long id) {		
		productRepo.reduceStock(quantity, id);	
	}

	@Override
	public Optional<Product> findById(long id) {
		return productRepo.findById(id);
	}

//	@Override
//	public List<Product> productsByName() {
//		return productRepo.findProductByName();
//	}

}
