package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class ProductDaoFileImpl implements ProductDao {
    private final String PRODUCTS_FILE = "data/Data/Products.txt";
    private HashMap<String, Product> products = new HashMap<>();

    public ProductDaoFileImpl() throws PersistenceException {
        readData();
    }

    private void readData() throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException ex) {
            throw new PersistenceException(
                    "Could not load products data...");
        }

        String currentLine;
        Product currentProduct;
        scanner.nextLine(); // Skip the headers
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallData(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    private Product unmarshallData(String line) {
        String[] data = line.split(",");
        BigDecimal costPerSqFt = new BigDecimal(data[1]);
        BigDecimal laborCostPerSqFt = new BigDecimal(data[2]);
        return new Product(data[0], costPerSqFt, laborCostPerSqFt);
    }

    @Override
    public Product getProductInfo(String productType)  throws PersistenceException {
        return products.get(productType);
    }

    @Override
    public Collection<Product> getAllProductInfo() throws PersistenceException {
        return products.values();
    }
}
