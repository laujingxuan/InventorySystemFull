package team5.service;

import java.util.ArrayList;
import java.util.List;

import team5.model.Product;


// methods specific to Product service
public interface ProductService extends IService<Product> {
	public ArrayList<String> FindAllPartNumber();
	 public List<Product> listAll(String keyword);
}
