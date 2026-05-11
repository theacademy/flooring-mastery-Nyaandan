package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServiceLayerImplTest {
    private final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    ServiceLayerImpl service;
    Order amisOrder;

    public ServiceLayerImplTest() {
        service = new ServiceLayerImpl(
                new OrderDaoStub(),
                new ProductDaoStub(),
                new TaxInfoDaoStub(),
                new ExportDaoStub());
        amisOrder = new Order();
    }


    @BeforeEach
    void setUp() {
        TaxInfo taxInfo = new TaxInfo("KY");
        taxInfo.setStateName("Kentucky");
        taxInfo.setTaxRate(new BigDecimal("6.00"));

        amisOrder.setInfo("Ameliance", taxInfo,
                new Product("Tile",
                        new BigDecimal("3.50"),
                        new BigDecimal("4.15")),
                new BigDecimal("800"));
        amisOrder.setOrderNumber(14);
        amisOrder.setOrderDate(TOMORROW);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addOrder() throws Exception {
        String date = TOMORROW.format(DATE_FORMAT);
        String customerName = "Ameliance";
        String state = "KY";
        String product = "Tile";
        String area = "800";

        try {
            Order newOrder = service.prepareOrder(date, customerName, state, product, area);
            Order addedOrder = service.addOrder(date, newOrder);
            assertNotNull(addedOrder);
        } catch (DataValidationException e) {
            fail("Exception thrown despite valid data.");
        }
    }

    @Test
    void getOrder() throws Exception {
        Order normallyAmisOrder = service.getOrder(TOMORROW.format(DATE_FORMAT), 14);
        assertNotNull(normallyAmisOrder);
        // assertEquals(amisOrder, normallyAmisOrder);

        Order nonexistent = service.getOrder(TOMORROW.format(DATE_FORMAT), 15);
        assertNull(nonexistent);
    }

    @Test
    void getAllOrders() throws Exception {
        Collection<Order> orders = service.getAllOrders(TOMORROW.format(DATE_FORMAT));
        assertEquals(1, orders.size());
        assertTrue(orders.contains(amisOrder));
    }

    @Test
    void editOrder() throws Exception {
        String date = TOMORROW.format(DATE_FORMAT);
        int orderNumber = 14;
        String customerName = "Ameliance";
        String state = "KY";
        String product = "Tile";
        String area = "800";

        Order editedOrder = service.prepareOrder(date, orderNumber, customerName, state, product, area);
        Order editedOrderInFile = service.editOrder(date, orderNumber, editedOrder);
        assertNotNull(editedOrderInFile);
    }

    @Test
    void removeOrder() throws Exception {
        Order normallyAmisOrder = service.removeOrder(TOMORROW.format(DATE_FORMAT), 14);
        assertNotNull(normallyAmisOrder);
        assertEquals(amisOrder, normallyAmisOrder);

        Order nonexistent = service.removeOrder(TOMORROW.format(DATE_FORMAT), 15);
        assertNull(nonexistent);
    }
}