package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.OrderDao;
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
        TaxInfo taxInfo = new TaxInfo("KY");
        taxInfo.setStateName("Kentucky");
        taxInfo.setTaxRate(new BigDecimal("6.00"));

        stubbedOrder.setInfo("Ameliance", taxInfo,
                new Product("Tile",
                        new BigDecimal("3.50"),
                        new BigDecimal("4.15")),
                new BigDecimal("800"));
        stubbedOrder.setOrderNumber(14);
        stubbedOrder.setOrderDate(LocalDate.now().plusDays(1));
    }


    @Override
    public Order addOrder(LocalDate date, Order order) {
        return stubbedOrder;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        if (date.isEqual(stubbedOrder.getOrderDate())
                && orderNumber == stubbedOrder.getOrderNumber()) {
            return stubbedOrder;
        }
        return null;
    }

    @Override
    public Collection<Order> getAllOrders(LocalDate date) {
        return List.of(stubbedOrder);
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order order) {
        if (date.isEqual(stubbedOrder.getOrderDate())
                && orderNumber == stubbedOrder.getOrderNumber()) {
            return stubbedOrder;
        }
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        if (date.isEqual(stubbedOrder.getOrderDate())
                && orderNumber == stubbedOrder.getOrderNumber()) {
            return stubbedOrder;
        }
        return null;
    }
}
