package team5.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team5.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query("Select p from Product as p where p.name LIKE %?1%" + " OR p.description LIKE %?1%" + " OR p.barcode LIKE %?1%"
			+ " OR p.type LIKE %?1%" + " OR p.color LIKE %?1%" + " OR p.originalPrice LIKE %?1%" + " OR p.category LIKE %?1%"
			+ " OR p.priceFWholesale LIKE %?1%" + " OR p.priceFRetail LIKE %?1%" + " OR p.PriceFPartner LIKE %?1%"
			+ " OR p.subcategory LIKE %?1%" + " OR p.unit LIKE %?1%" + " OR p.partNumber LIKE %?1%" + " OR p.reorderLevel LIKE %?1%"
			+ " OR p.minReoderLevel LIKE %?1%")
	public List<Product> search(String keyword);
	
	//public ArrayList<Product> findProductByName();
}
