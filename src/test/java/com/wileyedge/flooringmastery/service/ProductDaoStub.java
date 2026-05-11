package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.ProductDao;
import com.wileyedge.flooringmastery.model.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class ProductDaoStub implements ProductDao {
    private final Product stubbedProduct = new Product(
            "Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));

    public ProductDaoStub() {}

    @Override
    public Product getProductInfo(String productType) {
        return stubbedProduct;
    }

    @Override
    public Collection<Product> getAllProductInfo() {
        return List.of(stubbedProduct);
    }
}
