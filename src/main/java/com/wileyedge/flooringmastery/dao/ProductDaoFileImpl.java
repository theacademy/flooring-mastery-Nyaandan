package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

public class ProductDaoFileImpl implements ProductDao {
    private final String PRODUCTS_FILE = "data/Data/Products.txt";
    private final HashMap<String, Product> products = new HashMap<>();

    public ProductDaoFileImpl() throws PersistenceException {
        readData();
    }

    private void readData() throws PersistenceException {
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            br.lines().skip(1).forEach(line -> {
                Product product = unmarshallData(line);
                products.put(product.getProductType(), product);
            });
        } catch (IOException ex) {
            throw new PersistenceException("Could not load products data...");
        }
    }

    private Product unmarshallData(String line) {
        String[] data = line.split(",");
        BigDecimal costPerSqFt = new BigDecimal(data[1]);
        BigDecimal laborCostPerSqFt = new BigDecimal(data[2]);
        return new Product(data[0], costPerSqFt, laborCostPerSqFt);
    }

    @Override
    public Product getProductInfo(String productType) {
        return products.get(productType);
    }

    @Override
    public Collection<Product> getAllProductInfo() {
        return products.values();
    }
}
