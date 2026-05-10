package com.wileyedge.flooringmastery.view;

import com.wileyedge.flooringmastery.model.Order;

import java.util.Collection;

public class View {
    private final UserIO io;

    public View(UserIO io) {
        this.io = io;
    }


    public int printMenuAndGetSelection() {
        String sep = "=========================";
        io.println('\n' + sep);
        io.println("== WILEY EDGE FLOORING ==");
        io.println(sep);
        io.println("*\t1. Display Orders\t*");
        io.println("*\t2. Create   Order\t*");
        io.println("*\t3. Update   Order\t*");
        io.println("*\t4. Remove   Order\t*");
        io.println("*\t5. Export  Orders\t*");
        io.println("*\t0. Quit\t\t\t\t*");
        io.println(sep);

        int option = io.readInt("Select an option: ");
        io.println("");
        return option;
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

    //<editor-fold desc="Responses">
    public void displayResponseAddSuccess() {
        io.println("Order successfully added.");
    }

    public void displayResponseEditSuccess() {
        io.println("Order successfully edited.");
    }

    public void displayResponseRemoveSuccess() {
        io.println("Order successfully removed.");
    }

    public void displayResponseOperationCanceled() {
        io.println("Operation canceled...");
    }

    public void displayResponseExportSuccess(String filename) {
        io.println("Order successfully exported: " + filename);
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

    public String promptExportOption() {
        return io.readString("Select export option (active/all): ");
    }

    public void promptContinue() {
        io.readString("Press Enter to continue...");
    }

    public boolean promptConfirmation() {
        String res = io.readString("Press enter to confirm order details, or write anything to cancel... ");
        return res.isEmpty();
    }
    //</editor-fold>

    //<editor-fold desc="Error Messages">
    public void alertUnknownCommand() {
        io.println("The requested command is not recognized.");
    }

    public void alertObjectNotFound() {
        io.println("The requested order does not exist.");
    }

    public void displayExternalError(String msg) {
        io.println(msg);
    }
    //</editor-fold>

}
