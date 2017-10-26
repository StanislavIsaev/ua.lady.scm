package ua.lady.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.lady.scm.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select MAX(id) from Product")
    Integer findMaxId();
}
