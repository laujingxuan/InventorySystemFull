package team5.service;

import java.util.List;

import team5.model.Product;

public interface ProductInterface {
	public List<Product> findAll();
	public Product findByProductId(long id);
}
