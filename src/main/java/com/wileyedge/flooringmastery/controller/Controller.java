package com.wileyedge.flooringmastery.controller;

import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.model.Order;
import com.wileyedge.flooringmastery.service.DataValidationException;
import com.wileyedge.flooringmastery.service.ServiceLayer;
import com.wileyedge.flooringmastery.view.View;

public class Controller {
    private final ServiceLayer service;
    private final View view;

    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean running = true;

        try {
            do {
                switch (view.printMenuAndGetSelection()) {
                    case 1 -> listOrders();
                    case 2 -> addOrder();
                    case 3 -> editOrder();
                    case 4 -> removeOrder();
                    case 5 -> exportData();
                    case 0 -> running = false;
                    default -> view.alertUnknownCommand();
                }
            } while (running);

        } catch (PersistenceException ex) {
            view.displayExternalError(ex.getMessage());

        } finally {
            view.displayBannerExit();
        }
    }

    public void listOrders() throws PersistenceException {
        view.displayBannerList();
        // Get option: order number or 0 for all
        int option = view.promptListOption();
        String date = view.promptDate();

        try {
            if (option == 0) {
                view.displayAllOrders(service.getAllOrders(date));

            } else {
                Order order = service.getOrder(date, option);
                if (order != null) { view.displayOrder(order); }
                else { view.alertObjectNotFound(); }
            }
            view.promptContinue();

        } catch (DataValidationException ex) {
            view.displayExternalError(ex.getMessage());
        }
    }

    public void addOrder() throws PersistenceException {
        view.displayBannerAdd();
        String date = view.promptDate();
        String customerName = view.prompt("Customer name: ");
        String stateCode = view.prompt("State code: ");
        String productType = view.prompt("Product name: ");
        String area = view.prompt("Area to cover (number): ");

        /*String customerName = "Ashe";
        String stateCode = "TX";
        String productType = "Tile";
        String area = "1400";*/

        try {
            Order orderConf = service.prepareOrder(date, customerName, stateCode, productType, area);
            view.displayOrder(orderConf);

            if (view.promptConfirmation()) {
                service.addOrder(date, orderConf);
                view.displayResponseAddSuccess();
            } else {
                view.displayResponseOperationCanceled();
            }
        } catch (DataValidationException ex) {
            view.displayExternalError(ex.getMessage());
        }
    }

    public void editOrder() throws PersistenceException {
        view.displayBannerEdit();
        String date = view.promptDate();
        int orderNumber = view.promptOrderNumber();
        try {
            Order order = service.getOrder(date, orderNumber);
            if (order == null) {
                view.alertObjectNotFound();
                return;
            }

            String newCustomerName = view.prompt(
                    String.format("Customer name (%s): ", order.getCustomerName()));
            String newStateCode = view.prompt(
                    String.format("State (%s): ", order.getTaxInfo().getStateCode()));
            String newProductType = view.prompt(
                    String.format("Product (%s): ", order.getProductInfo().getProductType()));
            String newArea = view.prompt(
                    String.format("Area (%s): ", order.getArea()));

            Order orderConf = service.prepareOrder(date, orderNumber,
                    newCustomerName, newStateCode, newProductType, newArea);
            view.displayOrder(orderConf);

            if (view.promptConfirmation()) {
                service.editOrder(date, orderConf.getOrderNumber(), orderConf);
                view.displayResponseEditSuccess();
            } else {
                view.displayResponseOperationCanceled();
            }

        } catch (DataValidationException ex) {
            view.displayExternalError(ex.getMessage());
        }
    }

    public void removeOrder() throws PersistenceException {
        view.displayBannerRemove();

        String date = view.promptDate();
        int orderNumber = view.promptOrderNumber();

        try {
            Order targetOrder = service.getOrder(date, orderNumber);
            if (targetOrder == null) {
                view.alertObjectNotFound();
                return;
            }

            view.displayOrder(targetOrder);

            if (view.promptConfirmation()) {
                service.removeOrder(date, orderNumber);
                view.displayResponseRemoveSuccess();
            } else {
                view.displayResponseOperationCanceled();
            }

        } catch (DataValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportData() throws PersistenceException {
        view.displayBannerExport();

        String option = view.promptExportOption();
        switch (option) {
            case "active" -> view.displayResponseExportSuccess(
                    service.exportData(false));
            case "all" -> view.displayResponseExportSuccess(
                    service.exportData(true));
            default -> view.alertUnknownCommand();
        }
    }
}
