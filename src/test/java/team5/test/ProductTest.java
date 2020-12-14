package team5.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team5.model.Product;
import team5.repo.ProductRepository;
import team5.repo.UserRepository;

@SpringBootTest
public class ProductTest {
	
	@Autowired
	public ProductRepository productRepo;
	@Autowired
	public UserRepository urepo;
	
	@Test
	public void saveProduct() {
		productRepo.save(new Product(2345,"Collision Cart","The original high quality, low friction dynamics carts made from durable machined aluminum.",
									"Type A","Blue",200,"Category A", 2000 ,  3000, 1200, 2, 2, 12345 , 1, 1));
		productRepo.save(new Product(3432,"Large Table Clamp","Can attach to tables, shelves, or other boards up to 10 cm thick.",
				"Type B","Black",200,"Category B", 2500 ,  3000, 1260, 3, 2, 12345 , 1, 1));
		productRepo.save(new Product(5432,"Tension Protractor","ME-6855","Type C","White",500,"Category C", 8000 ,  3500, 1240, 6, 4, 2314 , 1, 4));
		
		
		ArrayList<Product> plist = new ArrayList<Product>();		
		plist = (ArrayList<Product>) productRepo.findAll();
		
		System.out.println("Print all products");
		for (Iterator<Product> iterator = plist.iterator(); iterator.hasNext();) {
			Product product =  iterator.next();
			System.out.println(product.toString());
		}
	}
	

}
