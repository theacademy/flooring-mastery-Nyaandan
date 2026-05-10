package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Collection;

public class ProductDaoFileImplTest {
    ProductDaoFileImpl testProductDao;

    @BeforeEach
    void setUp() throws Exception {
        String testfile = "testdata/Data/Products.txt";
        testProductDao = new ProductDaoFileImpl(testfile);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProductInfo() {
        String product1 = "Carpet";
        String product2 = "Tile";

        Product carpet = testProductDao.getProductInfo(product1);
        Product tile = testProductDao.getProductInfo(product2);

        Assertions.assertNotNull(carpet, "There should be a line for carpet.");
        Assertions.assertNotNull(tile, "There should be a line for tile.");
    }

    @Test
    void getAllProductInfo() {
        Collection<Product> products = testProductDao.getAllProductInfo();
        BigDecimal costPerSqFt = new BigDecimal("5.15");
        BigDecimal laborCostPerSqFt = new BigDecimal("4.75");
        Product wood = new Product("Wood", costPerSqFt, laborCostPerSqFt);

        Assertions.assertEquals(4, products.size(), "The should be 4 elements.");
        Assertions.assertTrue(products.contains(wood), "There should be a line for wood.");
    }
}
