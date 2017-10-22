package ua.lady.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lady.scm.domain.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findByName(String name);
}
