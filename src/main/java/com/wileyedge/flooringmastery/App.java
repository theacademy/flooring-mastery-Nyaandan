package com.wileyedge.flooringmastery;

import com.wileyedge.flooringmastery.controller.Controller;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        Controller controller = new ClassPathXmlApplicationContext(
                "applicationContext.xml")
                .getBean("controller", Controller.class);
        controller.run();
    }
}
