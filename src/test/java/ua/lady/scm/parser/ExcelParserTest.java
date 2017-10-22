package ua.lady.scm.parser;

import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.lady.scm.domain.ExcelConfig;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public class ExcelParserTest {

    private ExcelParser parser = new ExcelParser();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parse_1() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get("data/ПАРФУМ_17.10.2015.xls"));
        ExcelConfig config = new ExcelConfig();
        config.setHeaderRowIndex(4);
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 3);
        map.put("name", 4);
        map.put("price", 5);
        config.setColumnMapping(map);
        config.setBrandStrategy(ExcelConfig.BrandStrategy.FIRST);
        parser.setConfig(config);
        parser.setConfig(config);
        List<Map<String, String>> result = parser.parse(inputStream);
        result.forEach(m -> log.info(m.toString()));
    }

    @Test
    public void parse_2() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 2);
        map.put("name", 3);
        map.put("price", 4);
        templateTest("data/19,10,15  прайс опт.xls", 8, map, ExcelConfig.BrandStrategy.FIRST);
    }

    @Test
    public void parseWithoutBrands() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 1);
        map.put("name", 2);
        map.put("price", 3);
        templateTest("data/pure_without_brands.xls", 17, map, ExcelConfig.BrandStrategy.FIRST);
    }

    @Test
    public void parse_3() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 2);
        map.put("name", 3);
        map.put("price", 4);
        templateTest("data/ИЛТА_16.10.2015.xls", 2, map, ExcelConfig.BrandStrategy.FIRST);
    }

    @Test
    public void firstBrandNotAllIds() throws Exception {
        // very strange: view of businessId and the value don't match
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 1);
        map.put("name", 2);
        map.put("price", 3);
        templateTest("data/firstBrandNotAllIds.xls", 3, map, ExcelConfig.BrandStrategy.LAST);
    }

    @Test
    public void parfex() throws Exception {
        // very strange: view of businessId and the value don't match
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 7);
        map.put("name", 8);
        map.put("volume", 9);
        map.put("type", 10);
        map.put("gender", 11);
        map.put("price", 12);
        templateTest("data/parfex.xls", 9, map, ExcelConfig.BrandStrategy.LAST);
    }

    @Test
    public void parse_4() throws Exception {
        // very strange: view of businessId and the value don't match
        Map<String, Integer> map = new HashMap<>();
        map.put("businessId", 1);
        map.put("name", 2);
        map.put("volume", 3);
        map.put("gender", 4);
        map.put("type", 5);
        map.put("price", 6);
        templateTest("data/ПРАЙС ХОРОШИХ ЦЕН 15.10.15.xls", 1, map, ExcelConfig.BrandStrategy.LAST);
    }

    private void templateTest(String filePath,
                              int headerRowIndex,
                              Map<String, Integer> mapping,
                              ExcelConfig.BrandStrategy brandStrategy) throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(filePath));
        ExcelConfig config = new ExcelConfig();
        config.setHeaderRowIndex(headerRowIndex);
        config.setColumnMapping(mapping);
        config.setBrandStrategy(brandStrategy);
        parser.setConfig(config);
        parser.setConfig(config);
        List<Map<String, String>> result = parser.parse(inputStream);
        result.forEach(m -> log.info(m.toString()));
    }

}