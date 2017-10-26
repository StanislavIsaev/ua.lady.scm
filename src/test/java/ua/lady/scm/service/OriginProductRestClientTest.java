package ua.lady.scm.service;

import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lady.scm.domain.Product;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class OriginProductRestClientTest {
    @Autowired
    private OriginProductRestClient restClient;

    @Test
    public void allByPaging() throws Exception {
        Integer pageSize = 200;
        Set<Product> all = new HashSet<>();
        PagedResources<Product> products = restClient.findAll(0, pageSize);
        PagedResources.PageMetadata page = products.getMetadata();
        all.addAll(products.getContent());
        for (int i = 1; i < page.getTotalPages(); i++) {
            products = restClient.findAll(i, pageSize);
            all.addAll(products.getContent());
        }
        assertEquals(all.size(), page.getTotalElements());
    }

    @Test
    public void idGreaterThan() throws Exception {
        Resources<Product> products = restClient.findAllByIdGreaterThan(14500);
        assertThat(products.getContent().size(), greaterThan(0));
        Product product = products.getContent().stream().findFirst().get();
        log.info(product.toString());
    }
}