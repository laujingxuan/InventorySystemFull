package team5.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import team5.model.Product;

public interface ProductService extends IService<Product> {
	
	public ArrayList<String> FindAllPartNumber();
	 public List<Product> searchByKeyword(String keyword);
	Product findByName(String name);
	void updateStock(Long quantity, Long id);
	Optional<Product> OptionalFindById(Long id);
	 
}
