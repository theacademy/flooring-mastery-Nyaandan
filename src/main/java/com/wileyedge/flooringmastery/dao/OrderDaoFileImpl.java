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
    HashMap<Integer, Order> dayOrders;
    private String orderFile;
    int nextOrderNumber;

    public OrderDaoFileImpl() {
        dayOrders = new HashMap<>();
        nextOrderNumber = 1;
    }

    @Override
    public Order addOrder(LocalDate date, Order order) throws PersistenceException {
        readData(date);
        order.setOrderNumber(nextOrderNumber++);
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
        return String.format("%d,%s,%s,%f,%s,%f,%f,%f,%f,%f,%f,%f",
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

    private Order unmarshallData(String line) {
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

        order.setOrderNumber(orderNumber);
        order.setInfo(customerName.toString(), taxInfo,
                new Product(productType, costPerSqFt, laborCostSqFt), area);
        return order;
    }

    private void writeData(LocalDate date) throws PersistenceException {
        orderFile = String.format("%sOrders_%s.txt",
                ORDERS_FOLDER, date.format(DateTimeFormatter.ofPattern("MMddyyyy")));
        PrintWriter writer;

        try {
            writer = new PrintWriter(new FileWriter(orderFile));
        } catch (IOException ex) {
            throw new PersistenceException(
                    "Could not write data to file.");
        }

        String headers = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        writer.println(headers);
        writer.flush();

        String line;
        for (Order order : dayOrders.values()) {
            line = marshallData(order);
            writer.println(line);
            writer.flush();
        }
        writer.close();
    }

    private void readData(LocalDate date) throws PersistenceException {
        orderFile = String.format("%sOrders_%s.txt",
                ORDERS_FOLDER, date.format(DateTimeFormatter.ofPattern("MMddyyyy")));
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(new FileReader(orderFile)));
        } catch (FileNotFoundException ex) {
            throw new PersistenceException(
                    "Could not read data.");
        }

        String currentLine;
        Order currentOrder;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallData(currentLine);
            dayOrders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
    }
}
