package com.wileyedge.flooringmastery;

import com.wileyedge.flooringmastery.view.UserIOConsoleImpl;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome and well met.");
        UserIOConsoleImpl console = new UserIOConsoleImpl();
        Random rng = new Random();
        boolean off = false;

        String entry; int n;

        do {
            if (rng.nextInt(2) == 0) {
                entry = console.readString("[String] ");
                console.print(entry);
                if (entry.isBlank()) off = true;
            } else {
                n = console.readInt("[int] ");
                console.print("" + n);
                if (n == -1) off = true;
            }
        } while (!off);
    }
}