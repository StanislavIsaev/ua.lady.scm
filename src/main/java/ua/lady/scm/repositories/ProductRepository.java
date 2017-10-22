package ua.lady.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lady.scm.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
