package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.model.Product;
import com.wileyedge.flooringmastery.model.TaxInfo;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDaoFileImpl implements OrderDao {
    private final String ORDERS_FOLDER = "data/Orders/";
    private final String ORDER_NUMBER = "data/Data/OrderNumber.txt";
    HashMap<Integer, Order> dayOrders;

    public OrderDaoFileImpl() {
        dayOrders = new HashMap<>();
    }

    // Quick read and write function for the order number counter
    // Got the insight from the AI, and edited it to fit the goal.
    public int getNextOrderNumber() throws PersistenceException {
        int nextOrderNumber;

        try (BufferedReader br = new BufferedReader(new FileReader(ORDER_NUMBER))) {
            nextOrderNumber = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }

        try (FileWriter fw = new FileWriter(ORDER_NUMBER)) {
            fw.write(nextOrderNumber + 1 + "");
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }

        return nextOrderNumber;
    }


    @Override
    public Order addOrder(LocalDate date, Order order) throws PersistenceException {
        try {
            readData(date);
        } catch (PersistenceException ignored) {}

        int nextOrderNumber = getNextOrderNumber();
        order.setOrderNumber(nextOrderNumber);
        order.setOrderDate(date);
        dayOrders.put(order.getOrderNumber(), order);
        writeData(date);
        return order;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        readData(date);
        return dayOrders.get(orderNumber);
    }

    @Override
    public Collection<Order> getAllOrders(LocalDate date) throws PersistenceException {
        readData(date);
        return dayOrders.values();
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order edittedOrder) throws PersistenceException {
        readData(date);
        dayOrders.put(orderNumber, edittedOrder);
        writeData(date);
        return edittedOrder;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber)  throws PersistenceException {
        readData(date);
        Order removedOrder = dayOrders.remove(orderNumber);
        writeData(date);
        return removedOrder;
    }

    private String marshallData(Order order) {
        return String.format("%d,%s,%s,%.2f,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f",
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getTaxInfo().getStateCode(),
                order.getTaxInfo().getTaxRate(),
                order.getProductInfo().getProductType(),
                order.getArea(),
                order.getProductInfo().getCostPerSqFt(),
                order.getProductInfo().getLaborCostPerSqFt(),
                order.getMaterialCost(),
                order.getLaborCost(),
                order.getTax(),
                order.getTotal());
    }

    private Order unmarshallData(String line, LocalDate date) {
        String[] data = line.split(",");
        Iterator<String> it = Arrays.stream(data).iterator();
        int nameBlocks = data.length - 11;

        Order order = new Order();
        int orderNumber = Integer.parseInt(it.next());

        StringBuilder customerName = new StringBuilder();
        for (int i = 0; i < nameBlocks; i++) {
            customerName.append(it.next());
        }

        String taxState = it.next();
        BigDecimal taxRate = new BigDecimal(it.next());
        String productType = it.next();
        BigDecimal area = new BigDecimal(it.next());
        BigDecimal costPerSqFt = new BigDecimal(it.next());
        BigDecimal laborCostSqFt = new BigDecimal(it.next());

        TaxInfo taxInfo = new TaxInfo(taxState);
        taxInfo.setTaxRate(taxRate);
        // Empty State Name, not needed at this point

        order.setOrderDate(date);
        order.setOrderNumber(orderNumber);
        order.setInfo(customerName.toString(), taxInfo,
                new Product(productType, costPerSqFt, laborCostSqFt), area);
        return order;
    }

    private void writeData(LocalDate date) throws PersistenceException {
        String orderFile = String.format("%sOrders_%s.txt",
                ORDERS_FOLDER, date.format(DateTimeFormatter.ofPattern("MMddyyyy")));

        try (PrintWriter writer = new PrintWriter(new FileWriter(orderFile))) {
            String headers = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                    + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
            writer.println(headers);
            writer.flush();

            dayOrders.values().forEach(order -> {
                String line = marshallData(order);
                writer.println(line);
                writer.flush();
            });
        } catch (IOException ex) {
            throw new PersistenceException(
                    "Could not write data to file.");
        }
    }

    private void readData(LocalDate date) throws PersistenceException {
        String orderFile = String.format("%sOrders_%s.txt",
                ORDERS_FOLDER, date.format(DateTimeFormatter.ofPattern("MMddyyyy")));

        dayOrders.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
            br.lines().skip(1).forEach(line -> {
                 Order currentOrder = unmarshallData(line, date);
                dayOrders.put(currentOrder.getOrderNumber(), currentOrder);
            });
        } catch (IOException ex) {
            throw new PersistenceException("Could not read data.");
        }
    }
}
