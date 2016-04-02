
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//DiskBenchMarkApp performs the experiment for calculating throughput and latency of disk access
public class DiskBenchMarkApp {
	public static void main(String[] args) {

		// accessSize is the block size to be read and written from/to the disk
		int accessSize = 1024 * 1024;

		// fileSize is the file on which data will be read and written from/to
		// the disk
		int fileSize = 1024 * 1024 * 1024;

		// threadCount defines the number of threads to perform the operation.
		int threadCount = 1;

		// Operation type defines the various operations to perform.
		int operationTypes = 4;

		// This is the file where the operations are performed.
		String fileName = "1gba";

		/*
		 * With 1 thread, various operations such as sequential read, sequential
		 * write, random read, random write are performed. Parameters for this
		 * experiment is 1MB access on 1GB file.
		 */
		System.out.println("* Performing various operations on 1 GB file, accessed as 1MB with one thread *");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (operationTypes = 1; operationTypes <= 4; operationTypes++) {
			experiment(accessSize, fileSize, operationTypes, threadCount, fileName);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Performing the above operation with two threads
		threadCount = 2;

		/*
		 * With 2 threads, various operations such as sequential read,
		 * sequential write, random read, random write are performed. Parameters
		 * for this experiment is 1MB access on 1GB file.
		 */
		System.out.println("* Performing various operations on 1 GB file, accessed as 1MB with two threads *");
		for (operationTypes = 1; operationTypes <= 4; operationTypes++) {
			experiment(accessSize, fileSize, operationTypes, threadCount, fileName);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Operating parameters are changed for the upcoming experiments such as
		 * accessSize, fileSize, threadCount, fileName
		 */
		accessSize = 1024;
		fileSize = 1024 * 1024;
		threadCount = 1;
		fileName = "1mba";

		/*
		 * With 1 thread, various operations such as sequential read, sequential
		 * write, random read, random write are performed. Parameters for this
		 * experiment is 1KB access on 1MB file.
		 */
		System.out.println("* Performing various operations on 1 MB file, accessed as 1KB with one thread *");
		for (operationTypes = 1; operationTypes <= 4; operationTypes++) {
			experiment(accessSize, fileSize, operationTypes, threadCount, fileName);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Performing the above operation with two threads
		threadCount = 2;

		/*
		 * With 2 threads, various operations such as sequential read,
		 * sequential write, random read, random write are performed. Parameters
		 * for this experiment is 1KB access on 1MB file.
		 */
		System.out.println("* Performing various operations on 1 MB file, accessed as 1KB with two threads *");
		for (operationTypes = 1; operationTypes <= 4; operationTypes++) {
			experiment(accessSize, fileSize, operationTypes, threadCount, fileName);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Operating parameters are changed for the upcoming experiments such as
		 * accessSize, fileSize, threadCount, fileName
		 */
		accessSize = 1;
		fileSize = 1024;
		threadCount = 1;
		fileName = "1kba";

		/*
		 * With 1 thread, various operations such as sequential read, sequential
		 * write, random read, random write are performed. Parameters for this
		 * experiment is 1B access on 1KB file.
		 */
		System.out.println("* Performing various operations on 1 KB file, accessed as 1B with one thread *");
		for (operationTypes = 1; operationTypes <= 4; operationTypes++) {
			experiment(accessSize, fileSize, operationTypes, threadCount, fileName);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Performing the above operation with two threads
		threadCount = 2;

		/*
		 * With 2 threads, various operations such as sequential read,
		 * sequential write, random read, random write are performed. Parameters
		 * for this experiment is 1B access on 1KB file.
		 */
		System.out.println("* Performing various operations on 1 KB file, accessed as 1B with two threads *");
		for (operationTypes = 1; operationTypes <= 4; operationTypes++) {
			experiment(accessSize, fileSize, operationTypes, threadCount, fileName);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// experiment method encapsulates the thread handling using Java's
	// ExecutorService API and experiment running.
	private static void experiment(int accessSize, int fileSize, int operationType, int threadCount, String fileName) {

		// Java's ExecutorService API is used for handling threads.
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

		// Utility class object to access different methods in Utility class
		Utility utility = null;
		Utility utility2 = null;
		try {

			utility = new Utility(operationType, fileSize, accessSize, fileName, 0);

			// if threadCount is 2, then creating one more thread for the
			// experiment
			if (threadCount == 2) {
				utility2 = new Utility(operationType, fileSize, accessSize, fileName, 1);
			}

			// Spawning threads for disk access according to the parameters
			// provided
			executorService.submit(utility);
			if (threadCount == 2) {
				executorService.submit(utility2);
			}

			// waiting for minimum 10 seconds for the threads to complete
			executorService.awaitTermination(10, TimeUnit.SECONDS);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Shutting down the executorService
		executorService.shutdown();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Printing the results obtained through the experiments
		utility.printThroughput();

		if (threadCount == 2) {
			utility2.printThroughput();
		}

	}
}
