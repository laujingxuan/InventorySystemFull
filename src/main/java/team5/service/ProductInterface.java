package team5.service;

import java.util.List;

import team5.model.Product;

public interface ProductInterface {
	public List<Product> findAll();
	public void createProduct(Product product);
	public void updateProduct(Product product);
	public List<Product> listAllProducts(String keyword);
	public Product findProductById(long id);
	public void deleteProduct(Product product);
	//public List<Product> productsByName();
	public void updateStock(Long quantity, Long id);
}
