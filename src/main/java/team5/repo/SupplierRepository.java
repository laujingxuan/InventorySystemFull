package team5.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import team5.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
