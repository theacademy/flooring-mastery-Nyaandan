package com.wileyedge.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String productType;
    private final BigDecimal costPerSqFt;
    private final BigDecimal laborCostPerSqFt;

    public Product(String productType, BigDecimal costPerSqFt, BigDecimal laborCostPerSqFt) {
        this.productType = productType;
        this.costPerSqFt = costPerSqFt;
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(productType, product.productType)
                && Objects.equals(costPerSqFt, product.costPerSqFt)
                && Objects.equals(laborCostPerSqFt, product.laborCostPerSqFt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSqFt, laborCostPerSqFt);
    }
}
