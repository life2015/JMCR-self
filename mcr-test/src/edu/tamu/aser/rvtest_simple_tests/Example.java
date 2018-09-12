package edu.tamu.aser.rvtest_simple_tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.tamu.aser.reexecution.JUnit4MCRRunner;
import junit.framework.Assert;

@RunWith(JUnit4MCRRunner.class)
public class Example {

	static int x, y;
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				int b = x;
				y = 1;
			}
		});

		t1.start();

		int a = y;
		x = 1;

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
			x = 0;
			y = 0;
		Example.main(null);
		} catch (Exception e) {
			fail();
		}
	}
}