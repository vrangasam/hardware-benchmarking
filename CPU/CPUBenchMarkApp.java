import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//IntOperation calculates the GIOPs by performing Integer Operations for Integer.MAX_VALUE times
class IntOperation implements Runnable {
	int id = 0;
	
	//constructor for creating a thread with thread id
	public IntOperation(int id) {
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		//Integer on which operations are stored.
		int d = 0;
		
		int i = 0;
		
		//An empty for-loop to calculate the time taken for executing for-loop during the integer operations.
		//Timer starts
		long start1 = System.nanoTime();
		for (i = 0; i < Integer.MAX_VALUE / 15; i++) {

		}
		//Timer ends
		long end1 = System.nanoTime();
		
		//Elapsed time for empty for-loop is calculated
		double timeTaken1 = end1 - start1;
		
		//Performing integer operations
		//Timer starts
		long start = System.nanoTime();
		for (i = 0; i < Integer.MAX_VALUE / 15; i++) {
			d = 1 + 2 - 3 + 5 + 9 + 9 + 1 + 2 + 2 + 3 + 8 - 2 + 6 + 4 + 8 + 4;
		}
		//Timer ends
		long end = System.nanoTime();
		
		//Elapsed time for performing the integer operation, excluding the for-loop time
		double timeTaken = end - start - timeTaken1;
		timeTaken = (timeTaken * 1e-9);
		
		//Calculating the IOPS
		double giops = Integer.MAX_VALUE / timeTaken;
		
		//Converting IOPS to GIOPS
		giops = giops * 1e-9;
		
		System.out.format("Thread %d results in %f giops and it took %.3f secs to run\n", id, giops, timeTaken);
	}

}

//FloatOperation calculates the GFLOPs by performing Integer Operations for Integer.MAX_VALUE times
class FloatOperation implements Runnable {
	int id = 0;

	public FloatOperation(int id) {
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		//Floating point on which operations are stored.
		double d = 0.0;
		int i = 0;
		
		
		//An empty for-loop to calculate the time taken for executing for-loop during the integer operations.
		//Timer starts
		long start1 = System.nanoTime();
		for (i = 0; i < Integer.MAX_VALUE / (15); i++) {

		}
		
		//Timer ends
		long end1 = System.nanoTime();
		
		//Elapsed time for empty for-loop is calculated
		double timeTaken1 = end1 - start1;
		
		//Performing floating point operations
		//Timer starts
		long start = System.nanoTime();
		for (i = 0; i < Integer.MAX_VALUE / (15); i++) {
			d = 1.2 + 2.3 - 3.1 + 5.2 + 9.1 + 9.1 + 1.2 + 2.3 + 2.5 + 3.1 + 8.0 - 2.1 + 6.5 + 4.1 + 8.1 + 4.3;
		}
		//Timer ends
		long end = System.nanoTime();
		
		//Elapsed time for performing the integer operation, excluding the for-loop time
		double timeTaken = end - start - timeTaken1;
		timeTaken = (timeTaken * 1e-9);
		
		//Calculating the FLOPS
		double gflops = Integer.MAX_VALUE / timeTaken;
		
		//Converting IOPS to GIOPS
		gflops = gflops * 1e-9;
		
		System.out.format("Thread %d results in %f gflops and it took %.3f secs to run\n", id, gflops,
				timeTaken);
	}

}

public class CPUBenchMarkApp {


	public static void main(String[] args) {

	System.gc();
	//Performing float experiments with varying threads
		for (int i = 1; i <= 4; i=i*2) {
			floatExperiment(i);
		}
		
	//Performing integer experiments with varying threads	
		for (int i = 1; i <= 4; i=i*2) {
			intExperiment(i);
		}
		
		System.gc();
		
	}

	//floatExperiment encapsulates all the thread handling functionalities and floating point operations
	static void floatExperiment(int threadCount) {
		
		//Java's ExecutorService API is used for handling threads.
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

		//Threads are being spawned
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(new FloatOperation(i));
		}
		
		//Waiting for a minimum 10 seconds until all thread ends
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Shutting down the executorService after completing the experiments.
		executorService.shutdown();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//intExperiment encapsulates all the thread handling functionalities and integer operations
	static void intExperiment(int threadCount){
		
		//Java's ExecutorService API is used for handling threads.
		ExecutorService executorServicef = Executors.newFixedThreadPool(threadCount);
		
		//Threads are being spawned
		for (int i = 0; i < threadCount; i++) {
			executorServicef.submit(new IntOperation(i));
		}
		
		//Waiting for a minimum 10 seconds until all thread ends
		try {
			executorServicef.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Shutting down the executorService after completing the experiments.
		executorServicef.shutdown();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
