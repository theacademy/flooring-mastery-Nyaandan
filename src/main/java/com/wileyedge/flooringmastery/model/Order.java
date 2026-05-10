package com.wileyedge.flooringmastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private TaxInfo taxInfo;
    private Product productInfo;
    private BigDecimal area;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    // <editor-fold desc="Getters/Setters">
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public TaxInfo getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(TaxInfo taxInfo) {
        this.taxInfo = taxInfo;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }
    //</editor-fold>

    public void setInfo(String customerName, TaxInfo taxInfo, Product productInfo, BigDecimal area) {
        this.customerName = customerName;
        this.taxInfo = taxInfo;
        this.productInfo = productInfo;
        this.area = area;
        calculateCosts();
    }

    public void calculateCosts() {
        materialCost = area.multiply(productInfo.getCostPerSqFt());
        laborCost = area.multiply(productInfo.getLaborCostPerSqFt());
        tax = materialCost.add(laborCost).multiply(taxInfo.getTaxRate())
                .divide(new BigDecimal(100), RoundingMode.HALF_UP);
        total = materialCost.add(laborCost).add(tax);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber
                && Objects.equals(orderDate, order.orderDate)
                && Objects.equals(customerName, order.customerName)
                && Objects.equals(taxInfo, order.taxInfo)
                && Objects.equals(productInfo, order.productInfo)
                && Objects.equals(area, order.area)
                && Objects.equals(materialCost, order.materialCost)
                && Objects.equals(laborCost, order.laborCost)
                && Objects.equals(tax, order.tax)
                && Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, orderDate,
                customerName, taxInfo, productInfo, area,
                materialCost, laborCost, tax, total);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                ", taxInfo=" + taxInfo +
                ", productInfo=" + productInfo +
                ", area=" + area +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }

    public String fullPrint() {
        return String.format(
                "**********************%n" +
                        "%s%n" +
                        "DATE: %s%n" +
                        "CUSTOMER: %s%n" +
                        "STATE: %s - %s%n" +
                        "TAX RATE: %,.2f%n" + // Adds commas to tax rate
                        "PRODUCT: %s%n" +
                        "MATERIAL UNIT COST: %,.2f%n" +
                        "LABOR UNIT COST: %,.2f%n" +
                        "AREA: %,.2f%n" +
                        "TOTAL MATERIAL COST: %,.2f%n" + // e.g., 1,500.00
                        "TOTAL LABOR COST: %,.2f%n" +
                        "TAX AMOUNT: %,.2f%n" +
                        "ORDER TOTAL: %,.2f%n" +
                        "**********************",
                orderNumber == 0 ? "ORDER NO. PENDING" : "ORDER NO. " + orderNumber,
                orderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                customerName,
                taxInfo.getStateCode(), taxInfo.getStateName(),
                taxInfo.getTaxRate(),
                productInfo.getProductType(),
                productInfo.getCostPerSqFt(),
                productInfo.getLaborCostPerSqFt(),
                area,
                materialCost,
                laborCost,
                tax,
                total
        );

    }

    public String compactPrint() {
        return String.format("%s | %s | %s | %s | %s",
                orderNumber, customerName, taxInfo.getStateCode(),
                productInfo.getProductType(), area);
    }
}
