package concurrent.arrayblockingqueue.tests;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.tamu.aser.exploration.JUnit4MCRRunner;

@RunWith(JUnit4MCRRunner.class)
public class BlockingQueueTest {
	public static void main(String[] args) throws Exception {

        BlockingQueue queue = new ArrayBlockingQueue(1024);
        BlockingQueueTest ins = new BlockingQueueTest();

        Producer producer = ins.new Producer(queue);
        Consumer consumer = ins.new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }
	
	public class Producer implements Runnable{

	    protected BlockingQueue queue = null;

	    public Producer(BlockingQueue queue) {
	        this.queue = queue;
	    }

	    public void run() {
	        try {
	            queue.put("1");
	            Thread.sleep(1000);
	            queue.put("2");
	            Thread.sleep(1000);
	            queue.put("3");
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public class Consumer implements Runnable{

	    protected BlockingQueue queue = null;

	    public Consumer(BlockingQueue queue) {
	        this.queue = queue;
	    }

	    public void run() {
	        try {
	            System.out.println(queue.take());
	            System.out.println(queue.take());
	            System.out.println(queue.take());
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	@Test
	public void test() throws InterruptedException {
		try {
			BlockingQueueTest.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}