package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.model.Order;

import java.util.Collection;

public interface ServiceLayer {
    Order prepareOrder(
            String date, String customerName,
            String stateAbbr, String productType, String area)
            throws DataValidationException, PersistenceException;

    Order prepareOrder(
            String date, int orderNumber, String customerName,
            String stateAbbr, String productType, String area)
            throws DataValidationException, PersistenceException;

    Order addOrder(String date, Order order)
            throws DataValidationException, PersistenceException;

    Order getOrder(String date, int orderNumber)
            throws PersistenceException, DataValidationException;

    Collection<Order> getAllOrders(String date)
            throws PersistenceException, DataValidationException;

    Order editOrder(String date, int orderNumber, Order editedOrder)
            throws PersistenceException, DataValidationException;

    Order removeOrder(String date, int orderNumber)
            throws PersistenceException, DataValidationException;

    String exportData(boolean fullExport)
            throws PersistenceException;
}
