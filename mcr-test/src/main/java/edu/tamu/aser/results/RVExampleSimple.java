package edu.tamu.aser.results;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class RVExampleSimple {

    private static int x;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread.currentThread().setName("主线程");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("线程1");
                synchronized (lock) {
                    x = 0;
                }
                if (x > 0) {
                    x = 2;
                }
            }

        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("线程2");
                if (x > 1) {
                    System.err.println("Find the error!");
                    fail("FAIL!!!!!!!!!!! x > 1  ");
                }
            }

        });
        t1.start();
        t2.start();
        synchronized (lock) {
            x = 1;
        }
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
            x = 0;
            RVExampleSimple.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}

class TestUnit {
    public static int x = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            x = 2;
        });
        Thread t2 = new Thread(() -> {
            if (x == 0) {
                System.out.println(" X = 0 ");
            } else  if (x == 2) {
                System.out.println(" X = 2 ");
            }
        });
        t1.start();
        t2.start();
    }
}