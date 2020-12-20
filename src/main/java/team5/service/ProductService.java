package team5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import team5.model.Product;

public interface ProductService extends IService<Product> {
	
	public ArrayList<String> FindAllPartNumber();
	 public List<Product> searchByKeyword(String keyword);
	public Page<Product> listProducts(String keywords, int page,int size);
}
