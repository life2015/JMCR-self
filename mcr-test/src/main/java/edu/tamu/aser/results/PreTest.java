package edu.tamu.aser.results;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(JUnit4MCRRunner.class)
public class PreTest {
    public static int x = 0;

    @Test
    public void test() {
        Thread t1 = new Thread(() -> {
            Thread.currentThread().setName("t1");
            x = 2; // 写入变量值 Static Field Write Event
        });
        Thread t2 = new Thread(() -> {
            Thread.currentThread().setName("t2");
            if (x == 0) { // 读取变量值 Static Field Read Event
                System.out.println(" X = 0 ");
                fail();
            } else if (x == 2) { // 读取变量值
                System.out.println(" X = 2 ");
                fail();
            } else if (x == 3) {
                System.out.println(" X = 3 ");
                fail();
            }
        });
        t1.start(); // 线程开始 Thread Start Event
        t2.start();
        x = 3; // 写入变量值 Static Field Write
    }


}
