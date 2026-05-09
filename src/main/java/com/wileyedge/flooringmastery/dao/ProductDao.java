package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Product;

import java.util.Collection;

public interface ProductDao {
    public Product getProductInfo(String productType) throws PersistenceException;
    public Collection<Product> getAllProductInfo() throws PersistenceException;
}
