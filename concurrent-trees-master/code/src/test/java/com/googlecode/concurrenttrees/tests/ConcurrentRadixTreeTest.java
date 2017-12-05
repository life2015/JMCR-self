package com.googlecode.concurrenttrees.tests;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.concurrenttrees.common.Iterables;
import com.googlecode.concurrenttrees.common.PrettyPrinter;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.RadixTree;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;

import edu.tamu.aser.exploration.JUnit4MCRRunner;

/**
 * @author Alan Huang
 */
@RunWith(JUnit4MCRRunner.class)
public class ConcurrentRadixTreeTest {
	
	
	static RadixTree<Integer> tree = new ConcurrentRadixTree<Integer>(new DefaultCharArrayNodeFactory());

    public static void main(String[] args) {
  
    	Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				tree.put("TEST", 1);
		        tree.put("TOAST", 2);
		        tree.remove("TEST");
			}
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				tree.put("TEST", 2);
				tree.put("test", 2);
				tree.remove("TEST");
			}
		});

		t1.start();
		t2.start();
		
		tree.remove("TEST");
        
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        System.out.println("Tree structure:");
        // PrettyPrintable is a non-public API for testing, prints semi-graphical representations of trees...
        PrettyPrinter.prettyPrint((PrettyPrintable) tree, System.out);

        System.out.println();
        System.out.println("Value for 'TEST' (exact match): " + tree.getValueForExactKey("TEST"));
        System.out.println("Value for 'TOAST' (exact match): " + tree.getValueForExactKey("TOAST"));
        System.out.println();
        System.out.println("Keys starting with 'T': " + Iterables.toString(tree.getKeysStartingWith("T")));
        System.out.println("Keys starting with 'TE': " + Iterables.toString(tree.getKeysStartingWith("TE")));
        System.out.println();
        System.out.println("Values for keys starting with 'TE': " + Iterables.toString(tree.getValuesForKeysStartingWith("TE")));
        System.out.println("Key-Value pairs for keys starting with 'TE': " + Iterables.toString(tree.getKeyValuePairsForKeysStartingWith("TE")));
        System.out.println();
        System.out.println("Keys closest to 'TEMPLE': " + Iterables.toString(tree.getClosestKeys("TEMPLE")));
    }
    
    
    @Test
	public void test() throws InterruptedException {
		try {
			ConcurrentRadixTreeTest.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}