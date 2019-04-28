package com.retrox.code;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static {

    }

    public int local1;
    public String sloc2;

    public void test(int x) {
//        String s = local1 + sloc2 + x;
//        System.out.println(s);

        List list = new ArrayList();
        list.add(1);
        list.add(null);
        list.add("22");


        List<Object> list2 = new ArrayList<Object>();
        list2.add(1);
        list2.add(null);
        list2.add("22");

    }

    public static void main(String[] args) {

    }

}
