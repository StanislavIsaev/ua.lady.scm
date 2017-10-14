package ua.lady.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lady.scm.domain.SupplierProduct;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {


}
