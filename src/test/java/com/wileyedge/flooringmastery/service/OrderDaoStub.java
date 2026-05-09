package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.OrderDao;
import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class OrderDaoStub implements OrderDao {
    private final Order stubbedOrder = new Order();

    public OrderDaoStub() {
        TaxInfo taxInfo = new TaxInfo("SY");
        taxInfo.setStateName("Sharlayan");
        taxInfo.setTaxRate(new BigDecimal("3.00"));

        stubbedOrder.setInfo("Ameliance", taxInfo,
                new Product("Granite",
                        new BigDecimal("4.00"),
                        new BigDecimal("6.20")),
                new BigDecimal("800"));
        stubbedOrder.setOrderNumber(14);
        stubbedOrder.setOrderDate(LocalDate.parse("2022-04-12"));
    }


    @Override
    public Order addOrder(LocalDate date, Order order) throws PersistenceException {
        return stubbedOrder;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        if (date.isEqual(stubbedOrder.getOrderDate())
                && orderNumber == stubbedOrder.getOrderNumber()) {
            return stubbedOrder;
        }
        return null;
    }

    @Override
    public Collection<Order> getAllOrders(LocalDate date) throws PersistenceException {
        return List.of(stubbedOrder);
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order order) throws PersistenceException {
        if (date.isEqual(stubbedOrder.getOrderDate())
                && orderNumber == stubbedOrder.getOrderNumber()) {
            return stubbedOrder;
        }
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        if (date.isEqual(stubbedOrder.getOrderDate())
                && orderNumber == stubbedOrder.getOrderNumber()) {
            return stubbedOrder;
        }
        return null;
    }
}
