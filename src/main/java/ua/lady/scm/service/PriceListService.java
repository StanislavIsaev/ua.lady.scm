package ua.lady.scm.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lady.scm.domain.Price;
import ua.lady.scm.domain.Supplier;
import ua.lady.scm.domain.SupplierProduct;
import ua.lady.scm.parser.ExcelParser;
import ua.lady.scm.repositories.SupplierProductRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Log
public class PriceListService {

    @Autowired
    private SupplierProductRepository supplierProductRepository;

    public void updatePrices(Supplier supplier, InputStream inputStream) {
        ExcelParser excelParser = new ExcelParser();
        excelParser.setConfig(supplier.getExcelConfig());
        try {
            List<Map<String, String>> data = excelParser.parse(inputStream);
            //TODO: check business ids is unique
            data.forEach(m -> {
                String businessId = m.get("businessId");
                if (Objects.isNull(businessId)) {
                    // TODO: need implement generator for business Id
                }
                Optional<SupplierProduct> productOptional = supplierProductRepository.findByBusinessId(businessId);
                SupplierProduct product = null;
                if (productOptional.isPresent()) {
                    product = productOptional.get();
                    product.setPrice(new Price(Double.valueOf(m.get("price"))));
                } else {
                    product = SupplierProduct.builder()
                            .businessId(businessId)
                            .name(m.get("name"))
                            .type(m.get("type"))
                            .brand(m.get("brand"))
                            .group(m.get("group"))
                            .gender(m.get("gender"))
                            .volume(m.get("volume"))
                            .price(new Price(Double.valueOf(m.get("price"))))
                            .supplier(supplier)
                            .build();
                }
                supplierProductRepository.save(product);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
