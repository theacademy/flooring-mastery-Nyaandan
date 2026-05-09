package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.OrderDao;
import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.dao.ProductDao;
import com.wileyedge.flooringmastery.dao.TaxInfoDao;
import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;
import java.util.List;

public class ServiceLayerImpl implements ServiceLayer {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TaxInfoDao taxInfoDao;

    public ServiceLayerImpl(
            OrderDao orderDao,
            ProductDao productDao,
            TaxInfoDao taxInfoDao) {

        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxInfoDao = taxInfoDao;
    }

    private LocalDate validateDate(String date) throws DataValidationException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        } catch (DateTimeException ex) {
            throw new DataValidationException("The entered date is not valid.");
        }
    }

    @Override
    public Order addOrder(String date, String customerName,
                          String stateCode, String productType, String area)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);

        if (!customerName.matches("([A-Za-z0-9,.])+")) {
            throw new DataValidationException("The entered name is invalid.");
        }

        TaxInfo taxInfo = taxInfoDao.getTaxInfo(stateCode);
        if (taxInfo == null) {
            throw new DataValidationException("The entered state is invalid or not serviced.");
        }

        Product selectedProduct = productDao.getProductInfo(productType);
        if (selectedProduct == null) {
            throw new DataValidationException("The entered product is invalid or not available.");
        }

        BigDecimal areaVar;
        try {
            areaVar = new BigDecimal(area);
            if (areaVar.intValue() < 100) {
                throw new DataValidationException("The minimum accepted area is 100 sqft.");
            }
        } catch (NumberFormatException ex) {
            throw new DataValidationException("The entered area is not a valid number.");
        }

        Order newOrder = new Order();
        newOrder.setInfo(customerName, taxInfo, selectedProduct, areaVar);
        return orderDao.addOrder(dateObj, newOrder);
    }

    @Override
    public Order getOrder(String date, int orderNumber)
            throws DataValidationException, PersistenceException {
        LocalDate dateObj = validateDate(date);
        return orderDao.getOrder(dateObj, orderNumber);
    }

    @Override
    public Collection<Order> getAllOrders(String date)
            throws DataValidationException, PersistenceException {
        LocalDate dateObj = validateDate(date);
        return orderDao.getAllOrders(dateObj);
    }

    @Override
    public Order editOrder(String date, int orderNumber,
                           String newCustomerName, String newState,
                           String newProductType, String newArea)
            throws DataValidationException, PersistenceException {
        LocalDate dateObj = validateDate(date);
        Order editedOrder = orderDao.getOrder(dateObj, orderNumber);

        if (newCustomerName != null) {
                if (newCustomerName.matches("([A-Za-z0-9,.])+")) {
                    editedOrder.setCustomerName(newCustomerName);
                } else {
                    throw new DataValidationException("The entered name is invalid.");
                }
        }

        if (newState != null) {
            TaxInfo newTaxInfo = taxInfoDao.getTaxInfo(newState);
            if (newTaxInfo != null) {
                editedOrder.setTaxInfo(newTaxInfo);
            } else {
                throw new DataValidationException("The entered state is invalid or not serviced.");
            }
        }

        if (newProductType != null) {
            Product newSelectedProduct = productDao.getProductInfo(newProductType);
            if (newSelectedProduct != null) {
                editedOrder.setProductInfo(newSelectedProduct);
            } else {
                throw new DataValidationException("The entered product is invalid or not available.");
            }
        }

        if (newArea != null){
            BigDecimal areaVar;
            try {
                areaVar = new BigDecimal(newArea);
                if (areaVar.intValue() >= 100) {
                    editedOrder.setArea(areaVar);
                } else {
                    throw new DataValidationException("The minimum accepted area is 100 sqft.");
                }
            } catch (NumberFormatException ex) {
                throw new DataValidationException("The entered area is not a valid number.");
            }
        }

        return orderDao.editOrder(dateObj, orderNumber, editedOrder);
    }

    public void validateCustomerName(Order order, String value) {}
    public void validateTaxInfo(Order order, String value) {}
    public void validateProductInfo(Order order, String value) {}
    public void validateArea(Order order, String value) {}

    @Override
    public Order removeOrder(String date, int orderNumber)
            throws DataValidationException, PersistenceException {
        LocalDate dateObj = validateDate(date);
        return orderDao.removeOrder(dateObj, orderNumber);
    }
}
