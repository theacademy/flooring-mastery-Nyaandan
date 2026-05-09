package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.dao.ProductDao;
import com.wileyedge.flooringmastery.model.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ProductDaoStub implements ProductDao {
    private final Product stubbedProduct = new Product(
            "Granite", new BigDecimal("4.00"), new BigDecimal("6.20"));

    public ProductDaoStub() {}

    @Override
    public Product getProductInfo(String productType) throws PersistenceException {
        return stubbedProduct;
    }

    @Override
    public Collection<Product> getAllProductInfo() throws PersistenceException {
        return List.of(stubbedProduct);
    }
}
