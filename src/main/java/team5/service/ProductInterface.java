package team5.service;

import java.util.List;
import java.util.Optional;

import team5.model.Product;

public interface ProductInterface {
	public List<Product> findAll();
	public Product createProduct(Product product);
	public Product updateProduct(Product product);
	public List<Product> listAllProducts(String keyword);
	public Product findProductById(long id);
	public void deleteProduct(Product product);
	//public List<Product> productsByName();
	public void updateStock(Long quantity, Long id);
	public Optional<Product> findById(long id);
	public void deleteById(Long id);
}
