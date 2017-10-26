package ua.lady.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lady.scm.domain.Product;
import ua.lady.scm.domain.Supplier;
import ua.lady.scm.domain.SupplierProduct;

import java.util.List;
import java.util.Optional;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {

    SupplierProduct findByProductAndSupplier(Product product, Supplier supplier);

    Optional<SupplierProduct> findByBusinessId(String businessId);

    List<SupplierProduct> findBySupplier(Supplier supplier);

}
