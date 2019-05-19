package com.retrox.code;

import com.retrox.RealClient;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public int local1;
    public String sloc2;
    public RealClient client;
    private static String staticString = "Hello";

    public void testGetStatic() {
        String local = staticString;
        System.out.print(local);
    }

    public void test(int x) {
        String a = sloc2;

    }

    public void loop() {
        for (int i = 0; i < 10; i++) {
            int a = i;
        }
    }

    public static void logValue(String desc, Object value) {
        System.out.println("Desc" + desc + "Value" + value);
    }


    public static void main(String[] args) {

    }

}
