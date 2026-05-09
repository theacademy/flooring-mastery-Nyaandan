package com.wileyedge.flooringmastery.view;

public interface UserIO {
    void print(String msg);
    void println(String msg);
    String readString(String prompt);
    int readInt(String prompt);
}
