package team5.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import team5.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
