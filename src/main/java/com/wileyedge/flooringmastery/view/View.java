package com.wileyedge.flooringmastery.view;

import com.wileyedge.flooringmastery.model.Order;

import java.util.Collection;

public class View {
    private UserIO io;

    public View(UserIO io) {
        this.io = io;
    }


    public int printMenuAndGetSelection() {
        String sep = "============";
        io.println(sep);
        io.println("<Flooring Program>");
        io.println("1. Display Orders");
        io.println("2. Add an Order");
        io.println("3. Edit an Order");
        io.println("4. Remove an Order");
        io.println("5. Export Active Orders");
        io.println("6. Export All Data");
        io.println("0. Quit");
        io.println(sep);

        return io.readInt("Select an option: ");
    }

    public void getNewOrderInfo() {

    }

    public void displayOrder(Order order) {
        io.println(order.fullPrint());
    }

    public void displayAllOrders(Collection<Order> orders) {
        orders.forEach(o -> io.println(o.compactPrint()));
    }

    //<editor-fold desc="Banner Display">
    public void displayBannerList() {
        io.println("***   GET ORDERS   ***");
    }

    public void displayBannerAdd() {
        io.println("***   ADD  ORDER   ***");
    }

    public void displayBannerEdit() {
        io.println("***   EDIT ORDER   ***");
    }

    public void displayBannerRemove() {
        io.println("***  REMOVE ORDER  ***");
    }

    public void displayBannerExport() {
        io.println("***  EXPORT  DATA  ***");
    }

    public void displayBannerExit() {
        io.println("****     EXIT     ****");
    }
    //</editor-fold>

    //<editor-fold desc="Prompts">
    public String prompt(String msg) {
        return io.readString(msg);
    }

    public String promptDate() {
        return io.readString("Order date (MM-DD-YYYY): ");
    }

    public int promptOrderNumber() {
        return io.readInt("Order number: ");
    }

    public int promptListOption() {
        return io.readInt("Enter the desired order number or '0' to list all: ");
    }

    public void promptContinue() {
        io.readString("Press Enter to continue...");
    }
    //</editor-fold>

    //<editor-fold desc="Error Messages">
    public void alertUnknownCommand() {
        io.println("Unrecognized command entered.");
    }

    public void displayExternalError(String msg) {
        io.println(msg);
    }
    //</editor-fold>

}
