package ua.lady.scm.repositories;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lady.scm.domain.Price;
import ua.lady.scm.domain.Product;
import ua.lady.scm.domain.Supplier;
import ua.lady.scm.domain.SupplierProduct;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Log
public class SupplierRepositoryTest {

    public static final String SUPPLIER_NAME = "My Test Supplier";
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierProductRepository supplierProductRepository;
    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName(SUPPLIER_NAME);
        supplier = supplierRepository.saveAndFlush(supplier);
    }

    @Test
    public void createSupplier() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName(SUPPLIER_NAME);
        supplier = supplierRepository.saveAndFlush(supplier);
        assertNotNull(supplier.getId());
        SupplierProduct product = new SupplierProduct();
        product.setName("Some Product");
        product.setSupplier(supplier);
        supplier.setProducts(Lists.newArrayList(product));
        product.setProperties(new HashSet<>(Arrays.asList("Prop1", "Prop2")));
        product = supplierProductRepository.saveAndFlush(product);

        supplierRepository.findAll().stream().map(Supplier::toString).forEach(log::info);
    }

    @Test
    public void mapping() throws Exception {
        Product product = Product.builder()
                .id(111)
                .brand("brand")
                .gender("M")
                .group("edp")
                .name("some long name")
                .type("type")
                .volume("100")
                .build();
        product = productRepository.save(product);

        Supplier supplier = supplierRepository.findByName(SUPPLIER_NAME);

        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setName("some name");
        supplierProduct.setBusinessId("bus_id");
        supplierProduct.setProduct(product);
        supplierProduct.setSupplier(supplier);
        supplierProduct.setPrice(new Price(100d));
        supplierProductRepository.save(supplierProduct);

        SupplierProduct byProductAndSupplier = supplierProductRepository.findByProductAndSupplier(product, supplier);

        log.info(byProductAndSupplier.toString());
        Assert.assertNotNull(byProductAndSupplier);
        Assert.assertNotNull(byProductAndSupplier.getProduct());
        Assert.assertNotNull(byProductAndSupplier.getSupplier());
    }
}