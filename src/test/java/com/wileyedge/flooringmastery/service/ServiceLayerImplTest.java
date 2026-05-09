package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServiceLayerImplTest {
    ServiceLayerImpl service;
    Order amisOrder;

    public ServiceLayerImplTest() {
        service = new ServiceLayerImpl(
                new OrderDaoStub(),
                new ProductDaoStub(),
                new TaxInfoDaoStub());
        amisOrder = new Order();
    }


    @BeforeEach
    void setUp() {
        TaxInfo taxInfo = new TaxInfo("SY");
        taxInfo.setStateName("Sharlayan");
        taxInfo.setTaxRate(new BigDecimal("3.00"));

        amisOrder.setInfo("Ameliance", taxInfo,
                new Product("Granite",
                        new BigDecimal("4.00"),
                        new BigDecimal("6.20")),
                new BigDecimal("800"));
        amisOrder.setOrderNumber(14);
        amisOrder.setOrderDate(LocalDate.parse("2022-04-12"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addOrder() throws Exception {
        String date = "2022-04-12";
        String customerName = "Ameliance";
        String state = "SY";
        String product = "Granite";
        String area = "800";

        try {
            Order newOrder = service.addOrder(date, customerName, state, product, area);
            assertNotNull(newOrder);
        } catch (DataValidationException e) {
            fail("Exception thrown despite valid data.");
        }
    }

    @Test
    void getOrder() throws Exception {
        Order normallyAmisOrder = service.getOrder("2022-04-12", 14);
        assertNotNull(normallyAmisOrder);
        assertEquals(amisOrder, normallyAmisOrder);

        Order nonexistent = service.getOrder("2022-04-12", 15);
        assertNull(nonexistent);
    }

    @Test
    void getAllOrders() throws Exception {
        Collection<Order> orders = service.getAllOrders("2022-04-12");
        assertEquals(1, orders.size());
        assertTrue(orders.contains(amisOrder));
    }

    @Test
    void editOrder() throws Exception {
        String date = "2022-04-12";
        int orderNumber = 14;
        String customerName = "Ameliance";
        String state = "SY";
        String product = "Granite";
        String area = "800";

        Order edittedOrder = service.editOrder(date, orderNumber, customerName, state, product, area);
        assertNotNull(edittedOrder);
    }

    @Test
    void removeOrder() throws Exception {
        Order normallyAmisOrder = service.removeOrder("2022-04-12", 14);
        assertNotNull(normallyAmisOrder);
        assertEquals(amisOrder, normallyAmisOrder);

        Order nonexistent = service.removeOrder("2022-04-12", 15);
        assertNull(nonexistent);
    }
}