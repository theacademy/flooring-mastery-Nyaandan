package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public class OrderDaoFileImplTest {
    private OrderDaoFileImpl testOrderDao;
    private Order testOrder;
    private final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private final String NumberTracker = "testdata/Data/OrderNumber.txt";

    @BeforeEach
    void setUp() {
        String orders = "testdata/Orders/";
        testOrderDao = new OrderDaoFileImpl(orders, NumberTracker);

        TaxInfo taxInfo = new TaxInfo("KY");
        taxInfo.setStateName("Kentucky");
        taxInfo.setTaxRate(new BigDecimal("6.00"));

        testOrder = new Order();
        testOrder.setInfo("Kraken Co.", taxInfo,
                new Product("Carpet",
                        new BigDecimal("2.25"),
                        new BigDecimal("2.10")),
                new BigDecimal("800"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addOrder() throws Exception {
        int orderNumber = 4;
        try (PrintWriter out = new PrintWriter(new FileWriter(NumberTracker))) {
            out.write(orderNumber + "");
        } catch (IOException ignored) {}

        Order addedOrder = testOrderDao.addOrder(TOMORROW, testOrder);
        Assertions.assertEquals(4, addedOrder.getOrderNumber());
        Assertions.assertEquals(TOMORROW, addedOrder.getOrderDate());
    }

    @Test
    void getOrder() throws Exception {
        LocalDate date = LocalDate.parse("2013-06-01");
        Order order = testOrderDao.getOrder(date, 1);

        Assertions.assertEquals(1, order.getOrderNumber());
        Assertions.assertEquals(date, order.getOrderDate());
        Assertions.assertEquals("Ada Lovelace", order.getCustomerName());
    }

    @Test
    void getAllOrders() throws Exception {
        LocalDate date = LocalDate.parse("2013-06-02");
        Collection<Order> orders = testOrderDao.getAllOrders(date);

        Assertions.assertNotNull(orders);
        Assertions.assertEquals(2, orders.size());
    }
}
