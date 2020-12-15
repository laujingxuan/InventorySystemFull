package team5.service;

import java.util.ArrayList;
import java.util.List;

import team5.model.Product;

public interface ProductService {
	public ArrayList<String> FindAllPartNumber();
	 public List<Product> listAll(String keyword);
}
