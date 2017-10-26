package ua.lady.scm.service;

import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lady.scm.domain.ExcelConfig;
import ua.lady.scm.domain.Supplier;
import ua.lady.scm.domain.SupplierProduct;
import ua.lady.scm.repositories.SupplierProductRepository;
import ua.lady.scm.repositories.SupplierRepository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class PriceListServiceTest {
    @Autowired
    private PriceListService service;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierProductRepository supplierProductRepository;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void updatePrices() throws Exception {
        Supplier supplier = supplierRepository.findByName("Parfex");
        if (Objects.isNull(supplier)) {
            supplier = new Supplier();
            supplier.setName("Parfex");
            ExcelConfig config = new ExcelConfig();
            config.setBrandStrategy(ExcelConfig.BrandStrategy.LAST);
            config.setHeaderRowIndex(9);
            config.put("businessId", 7);
            config.put("name", 8);
            config.put("volume", 9);
            config.put("type", 10);
            config.put("gender", 11);
            config.put("price", 12);
            supplier.setExcelConfig(config);
            supplierRepository.saveAndFlush(supplier);
        }

        InputStream inputStream = Files.newInputStream(Paths.get("data/parfex.xls"));
        service.updatePrices(supplier, inputStream);
        supplier = supplierRepository.findByName("Parfex");
        supplierProductRepository.findBySupplier(supplier).stream().map(SupplierProduct::toString).forEach(log::info);
    }

}