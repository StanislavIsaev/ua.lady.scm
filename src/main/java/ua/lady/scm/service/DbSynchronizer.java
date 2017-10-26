package ua.lady.scm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import ua.lady.scm.domain.Product;
import ua.lady.scm.repositories.ProductRepository;

import java.util.Objects;

@Service
@Slf4j
public class DbSynchronizer {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OriginProductRestClient restClient;
    @Value("${origindb.pagesize}")
    private Integer pageSize;

    public void updateAll() {
        PagedResources<Product> products = restClient.findAll(0, pageSize);
        PagedResources.PageMetadata page = products.getMetadata();
        productRepository.save(products.getContent());
        for (int i = 1; i < page.getTotalPages(); i++) {
            products = restClient.findAll(i, pageSize);
            productRepository.save(products.getContent());
        }
    }

    public void loadNewest() {
        Integer maxId = productRepository.findMaxId();
        log.debug("Max Id:" + maxId);
        if (Objects.isNull(maxId)) {
            maxId = 0;
        }
        Resources<Product> products = restClient.findAllByIdGreaterThan(maxId);
        products.getContent().stream().map(Product::toString).forEach(log::debug);
        productRepository.save(products);
    }


}
