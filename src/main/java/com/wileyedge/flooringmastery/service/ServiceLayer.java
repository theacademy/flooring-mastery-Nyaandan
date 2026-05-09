package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.model.Order;

import java.util.Collection;

public interface ServiceLayer {
    public Order addOrder(String date, String customerName, String stateAbbr, String productType, String area)
            throws DataValidationException, PersistenceException;

    public Order getOrder(String date, int orderNumber)
            throws PersistenceException, DataValidationException;

    public Collection<Order> getAllOrders(String date)
            throws PersistenceException, DataValidationException;

    public Order editOrder(String date, int orderNumber, String newCustomerName, String newState, String newProductType, String newArea)
            throws PersistenceException, DataValidationException;

    public Order removeOrder(String date, int orderNumber)
            throws PersistenceException, DataValidationException;
}
