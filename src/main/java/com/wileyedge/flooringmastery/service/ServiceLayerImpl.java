package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.*;
import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class ServiceLayerImpl implements ServiceLayer {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TaxInfoDao taxInfoDao;
    private final ExportDao exportDao;

    public ServiceLayerImpl(
            OrderDao orderDao,
            ProductDao productDao,
            TaxInfoDao taxInfoDao,
            ExportDao exportDao) {

        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxInfoDao = taxInfoDao;
        this.exportDao = exportDao;
    }

    @Override
    public Order prepareOrder(String date, String customerName,
                              String stateCode, String productType, String area)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);
        if (dateObj.isBefore(LocalDate.now())) {
            throw new DataValidationException(
                    "The order date must be in the future.");
        }

        Order newOrder = new Order();
        newOrder.setOrderDate(dateObj);
        validateCustomerName(newOrder, customerName);
        validateTaxInfo(newOrder, stateCode);
        validateProductInfo(newOrder, productType);
        validateArea(newOrder, area);
        newOrder.calculateCosts();

        return newOrder;
    }

    @Override
    public Order prepareOrder(String date, int orderNumber,
                              String newCustomerName, String newState,
                              String newProductType, String newArea)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);
        Order editedOrder = orderDao.getOrder(dateObj, orderNumber);
        fillTaxInfo(editedOrder);
        boolean hasChanged = false;

        if (!newCustomerName.isBlank()) {
            validateCustomerName(editedOrder, newCustomerName);
        }
        if (!newState.isBlank()) {
            validateTaxInfo(editedOrder, newState);
            hasChanged = true;
        }
        if (!newProductType.isBlank()) {
            validateProductInfo(editedOrder, newProductType);
            hasChanged = true;
        }
        if (!newArea.isBlank()){
            validateArea(editedOrder, newArea);
            hasChanged = true;
        }

        if (hasChanged) {
            editedOrder.calculateCosts();
        }

        return editedOrder;
    }

    @Override
    public Order addOrder(String date, Order order)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);
        return orderDao.addOrder(dateObj, order);
    }

    @Override
    public Order getOrder(String date, int orderNumber)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);
        Order order = orderDao.getOrder(dateObj, orderNumber);
        if (order != null) {
            fillTaxInfo(order);
        }
        return order;
    }

    @Override
    public Collection<Order> getAllOrders(String date)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);
        return orderDao.getAllOrders(dateObj);
    }

    @Override
    public Order editOrder(String date, int orderNumber, Order editedOrder)
            throws PersistenceException, DataValidationException {

        LocalDate dateObj = validateDate(date);
        return orderDao.editOrder(dateObj, orderNumber, editedOrder);
    }

    @Override
    public Order removeOrder(String date, int orderNumber)
            throws DataValidationException, PersistenceException {

        LocalDate dateObj = validateDate(date);
        return orderDao.removeOrder(dateObj, orderNumber);
    }

    @Override
    public String exportData(boolean fullExport)
            throws PersistenceException {

        return fullExport ?
                exportDao.exportAllData() :
                exportDao.exportActiveData();
    }

    // <editor-fold desc="Field Validation">
    private LocalDate validateDate(String date) throws DataValidationException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        } catch (DateTimeException ex) {
            throw new DataValidationException("The entered date is not valid.");
        }
    }

    private void validateCustomerName(Order order, String value)
            throws DataValidationException {

        if (value.matches("([A-Za-z0-9,.])+")) {
            order.setCustomerName(value);
        } else {
            throw new DataValidationException("The entered name is invalid.");
        }
    }

    private void validateTaxInfo(Order order, String value)
            throws DataValidationException, PersistenceException {
        TaxInfo newTaxInfo = taxInfoDao.getTaxInfo(value);
        if (newTaxInfo != null) {
            order.setTaxInfo(newTaxInfo);
        } else {
            throw new DataValidationException("The entered state is invalid or not serviced.");
        }
    }

    private void validateProductInfo(Order order, String value)
            throws DataValidationException, PersistenceException  {
        Product newSelectedProduct = productDao.getProductInfo(value);
        if (newSelectedProduct != null) {
            order.setProductInfo(newSelectedProduct);
        } else {
            throw new DataValidationException("The entered product is invalid or not available.");
        }
    }

    private void validateArea(Order order, String value)
            throws DataValidationException {
        BigDecimal areaVar;
        try {
            areaVar = new BigDecimal(value);
            if (areaVar.intValue() >= 100) {
                order.setArea(areaVar);
            } else {
                throw new DataValidationException("The minimum accepted area is 100 sqft.");
            }
        } catch (NumberFormatException ex) {
            throw new DataValidationException("The entered area is not a valid number.");
        }
    }

    private void fillTaxInfo(Order order)
            throws PersistenceException {

        String taxState = order.getTaxInfo().getStateCode();
        order.setTaxInfo(taxInfoDao.getTaxInfo(taxState));
    }
    // </editor-fold>
}
