package com.wileyedge.flooringmastery.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.print(msg);
    }

    @Override
    public void println(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        System.out.print(prompt);

        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            String errorMsg = "Invalid data entered.";
            System.out.println(errorMsg);
            return 0;

        } finally {
            scanner.nextLine();
        }
    }
}
