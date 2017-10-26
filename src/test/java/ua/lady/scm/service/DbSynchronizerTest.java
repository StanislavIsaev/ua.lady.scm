package ua.lady.scm.service;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lady.scm.domain.Product;
import ua.lady.scm.repositories.ProductRepository;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DbSynchronizerTest {

    @Autowired
    private DbSynchronizer dbSynchronizer;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void updateAll() throws Exception {
        dbSynchronizer.updateAll();
        assertThat(productRepository.count(), Matchers.greaterThan(0L));
    }

    @Test
    public void loadNewest() throws Exception {
        productRepository.save(Product.builder().id(14500).build());
        productRepository.save(Product.builder().id(14400).build());
        productRepository.save(Product.builder().id(14300).build());
        dbSynchronizer.loadNewest();
        assertThat(productRepository.count(), Matchers.greaterThan(3L));
    }
}