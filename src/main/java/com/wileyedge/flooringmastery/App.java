package com.wileyedge.flooringmastery;

import com.wileyedge.flooringmastery.controller.Controller;
import com.wileyedge.flooringmastery.dao.*;
import com.wileyedge.flooringmastery.service.ServiceLayer;
import com.wileyedge.flooringmastery.service.ServiceLayerImpl;
import com.wileyedge.flooringmastery.view.UserIOConsoleImpl;
import com.wileyedge.flooringmastery.view.View;

public class App {
    public static void main(String[] args) throws PersistenceException {
        OrderDaoFileImpl orderDao = new OrderDaoFileImpl();
        ProductDaoFileImpl productDao = new ProductDaoFileImpl();
        TaxInfoDaoFileImpl taxInfoDao = new TaxInfoDaoFileImpl();
        ServiceLayerImpl service = new ServiceLayerImpl(orderDao, productDao, taxInfoDao);

        UserIOConsoleImpl console = new UserIOConsoleImpl();
        View view = new View(console);

        Controller controller = new Controller(service, view);

        controller.run();
    }
}
