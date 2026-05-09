package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.Collection;

public interface OrderDao {
    public Order addOrder(LocalDate date, Order order)  throws PersistenceException;
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;
    public Collection<Order> getAllOrders(LocalDate date) throws PersistenceException;
    public Order editOrder(LocalDate date, int orderNumber, Order order) throws PersistenceException;
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException;
}
