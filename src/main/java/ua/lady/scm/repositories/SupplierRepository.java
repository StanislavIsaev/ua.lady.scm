package ua.lady.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lady.scm.domain.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Supplier findByName(String name);

    Optional<Supplier> findOptionalByName(String name);
}
