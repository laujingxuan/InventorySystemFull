package team5.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import team5.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	public ArrayList<Product> findAll();

    @Query("SELECT DISTINCT p FROM Product p  WHERE p.partNumber LIKE :Partnum%")
    @Transactional(readOnly = true)
    Collection<String> findByPartNumber(@Param("Partnum") String partNumber);

    @Query("SELECT p FROM Product p WHERE p.partNumber LIKE %?1%"
            + " OR p.description LIKE %?1%"
            + " OR p.color LIKE %?1%")
    public List<Product> search(String keyword);

}
