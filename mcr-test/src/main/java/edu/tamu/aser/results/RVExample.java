package edu.tamu.aser.results;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import edu.tamu.aser.reex.JUnit4MCRRunner;

@RunWith(JUnit4MCRRunner.class)
public class RVExample {

	private static int x;
	private static int y;
	private static Object lock = new Object();
	
	public static void main(String[] args) {
		Thread.currentThread().setName("主线程");
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("线程1");
				for (int i = 0; i < 2; i++) {
					synchronized (lock) {
						x = 0;
					}
					if (x > 0) {
						y++;
						x = 2;
					}
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("线程2");
				for (int i = 0; i < 2; i++) {
					if (x > 1) {
						System.err.println("Find the error!");
						fail("FAIL!!!!!!!!!!! x > 1 FUCK ");
						if (y == 3) {
							System.err.println("Find the error!");
							fail("FAIL!!!!!!!!!!!");
						} else
							y = 2;
					}
				}
			}

		});
		t1.start();
		t2.start();

		for (int i = 0; i < 2; i++) {
			synchronized (lock) {
				x = 1;
				y = 1;
			}
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
			y = 0;
//			lock = new Object();
			RVExample.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}