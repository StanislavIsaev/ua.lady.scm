package ua.lady.scm.repositories;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lady.scm.domain.Price;
import ua.lady.scm.domain.PriceList;
import ua.lady.scm.domain.Supplier;
import ua.lady.scm.domain.SupplierProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Log
public class SupplierRepositoryTest {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierProductRepository supplierProductRepository;

    @Test
    public void createSupplier() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("My Test Supplier");
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
    public void priceLists() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("My Test Supplier");
        PriceList priceList = new PriceList();
        priceList.setSupplier(supplier);
        supplier.setPriceLists(Lists.newArrayList(priceList));
        supplier = supplierRepository.saveAndFlush(supplier);
        log.info(supplier.toString());

    }

    @Test
    public void prices() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("My Test Supplier");
        PriceList priceList = new PriceList();
        priceList.setSupplier(supplier);
        supplier.setPriceLists(Lists.newArrayList(priceList));
        supplier = supplierRepository.saveAndFlush(supplier);
        log.info(supplier.toString());

        SupplierProduct product = new SupplierProduct();
        product.setId("Some ID");
        product.setName("Some Product");
        product = supplierProductRepository.save(product);
        Price price = new Price();
        price.setValue(BigDecimal.TEN);
        priceList.setPrices(new HashMap<SupplierProduct, Price>());
        priceList.getPrices().put(product, price);
        supplier = supplierRepository.saveAndFlush(supplier);
        log.info(supplier.toString());
        log.info(supplier.getPriceLists().get(0).getPrices().toString());

    }
}