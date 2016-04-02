

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class Sample2 {
	
	private static final class FloatOperationTenMint extends TimerTask {
		private final Timer timer;
		private int id = 1;
		private int count = 2;

		
		
		private FloatOperationTenMint(Timer timer) {
			this.timer = timer;
		}

		public void run()
		{

			floatOperation(timer);
		}

		private void floatOperation(final Timer timer) {
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
			
			 if (--count < 1) timer.cancel();
		}
	}

	public static void main(String[] args) {

		final Timer timer = new Timer();
		final Timer timer1 = new Timer();
		final Timer timer2 = new Timer();
		final Timer timer3 = new Timer();
		FloatOperationTenMint one =  new FloatOperationTenMint(timer);
		FloatOperationTenMint two =  new FloatOperationTenMint(timer1);
		FloatOperationTenMint three =  new FloatOperationTenMint(timer2);
		FloatOperationTenMint four =  new FloatOperationTenMint(timer3);
		// Note that timer has been declared final, to allow use in anon. class below
		timer.schedule(one , 1000, 1000 //Note the second argument for repetition
		);
		timer1.schedule( two, 1000, 1000 //Note the second argument for repetition
				);
		timer2.schedule(three, 1000, 1000 //Note the second argument for repetition
				);
		timer3.schedule(four, 1000, 1000 //Note the second argument for repetition
				);
		
		
		
		
		
		
	}
}
