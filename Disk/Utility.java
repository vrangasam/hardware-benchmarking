
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

//Utility class has all the operations to perform
public class Utility implements Runnable {
	public double benchmark = 0.0;
	public double latency = 0.0;
	private String path = null;
	private long fileSize = 0;
	private int operationType = 0;
	private int accessSize = 0;
	private int threadId = 0;

	// Utility constructor to create utility object with various parameters
	// needed for the experiments
	public Utility(int operationType, long fileSize, int accessSize, String path, int threadId) {
		this.path = path;
		this.fileSize = fileSize;
		this.operationType = operationType;
		this.accessSize = accessSize;
		this.threadId = threadId;
	}

	// readSeq is the method used to read disk sectors sequentially
	void readSeq(int size) {

		// Java's RandomAccessFile is used for handling file.
		RandomAccessFile raf = null;

		// Java's FileChannel is used for performing file operations such as
		// file read, file write, file seeking, etc.,
		FileChannel fc = null;

		// A byte buffer to hold the bytes to be read from the file.
		ByteBuffer bb = null;

		// Once the bytes are read from the file using FileChannel object, it
		// returns the number of bytes read.
		// So holding that value here
		int noOfBytesRead = 0;

		// File position to seek
		long position = 0;

		// Size of the file on which operations are performed
		long fileSize = 0;

		/*
		 * Readcount is used to hold the value which equals the number of times
		 * a operation should be executed such that the operation is complete.
		 */
		long readCount = 0;

		// It holds the throughput value
		double throughput = 0.0;

		// It holds the latency value
		// double latency = 0.0;

		double elapsedTime = 0.0;

		try {

			// Getting the RandomAccessFile object with read and write
			// permission.
			raf = new RandomAccessFile(this.path, "rw");

			// Obtaining the file channel from the RandomAccessFile object
			// created.
			fc = raf.getChannel();

			// allocating size for the bytebuffer declared.
			bb = ByteBuffer.allocate(size);

			// Obtaining the size of the file
			fileSize = raf.length();

			// Calculating the readCount as mentioned above
			readCount = fileSize / size;

			// Capturing the start time of the operation
			double start = System.nanoTime();

			// Performing the operation until the operation is complete with
			// readCount value
			while (readCount > 0) {

				bb = ByteBuffer.allocate(size);

				// Clearing the buffer for any existing
				bb.clear();

				// Seeking to the position from which reading should be
				// performed.
				fc.position(position);

				// Reading bytes of memory from file to the bytebuffer
				noOfBytesRead = fc.read(bb);

				// Updating the readCount value
				readCount--;
			}

			// Thread.sleep(30000);

			// Capturing the end time of the operation
			double end = System.nanoTime();

			// Calculating the elapsedTime for the operation
			elapsedTime = end - start;
			elapsedTime = elapsedTime * 1e-9;

			// Calculating the throughput
			throughput = fileSize / elapsedTime;
			throughput = (throughput) / (1024 * 1024);

			// Closing the file channel
			fc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		latency = elapsedTime * 1e3;
		benchmark = throughput;
		System.gc();
	}

	// writeSeq is the method used to write disk sectors sequentially
	void writeSeq(int size) {

		// Java's RandomAccessFile is used for handling file.
		RandomAccessFile raf = null;

		// Java's FileChannel is used for performing file operations such as
		// file read, file write, file seeking, etc.,
		FileChannel fc = null;

		// A byte buffer to hold the bytes to be read from the file.
		ByteBuffer bb = null;

		// Once the bytes are written to the file using FileChannel object, it
		// returns the number of bytes written.
		// So holding that value here
		int noOfBytesWritten = 0;

		// File position to seek
		long position = 0;

		// It holds the throughput value
		double throughput = 0.0;

		/*
		 * writeCount is used to hold the value which equals the number of times
		 * a operation should be executed such that the operation is complete.
		 */
		long writeCount = 0;

		double elapsedTime = 0.0;

		try {
			// Getting the RandomAccessFile object with read and write
			// permission.
			raf = new RandomAccessFile(this.path, "rw");

			// Obtaining the file channel from the RandomAccessFile object
			// created.
			fc = raf.getChannel();

			// allocating size for the bytebuffer declared.
			bb = ByteBuffer.allocate(size);

			// Calculating the readCount as mentioned above
			writeCount = fileSize / size;

			// random bytes to write into the file
			byte[] bytes = new byte[size];
			Random random = new Random();

			// Copying the byte array into the bytebuffer
			random.nextBytes(bytes);
			for (int i = 0; i < bytes.length; i++) {
				bb.put((byte) bytes[i]);
			}

			// Capturing the start time of the operation
			double start = System.nanoTime();

			while (writeCount > 0) {

				// Flipping the byte buffer, such that the position is updated
				// to start.
				bb.flip();

				// Writing bytes of memory from bytebuffer to the file
				noOfBytesWritten = fc.write(bb);

				// Updating the writeCount value
				writeCount--;
			}

			// Capturing the end time of the operation
			double end = System.nanoTime();

			// Calculating the elapsedTime for the operation
			elapsedTime = end - start;
			elapsedTime = elapsedTime * 1e-9;

			// Calculating the throughput
			throughput = fileSize / elapsedTime;
			throughput = (throughput) / (1024 * 1024);

			// Closing the file channel
			fc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		latency = elapsedTime * 1e3;
		benchmark = throughput;
		System.gc();
	}

	// readRand is the method used to read disk sectors randomly
	void readRand(int size) {

		// Java's RandomAccessFile is used for handling file.
		RandomAccessFile raf = null;

		// Java's FileChannel is used for performing file operations such as
		// file read, file write, file seeking, etc.,
		FileChannel fc = null;

		// A byte buffer to hold the bytes to be read from the file.
		ByteBuffer bb = null;

		// Once the bytes are read from the file using FileChannel object, it
		// returns the number of bytes read.
		// So holding that value here
		int noOfBytesRead = 0;

		// File position to seek
		long position = 0;

		// Size of the file on which operations are performed
		long fileSize = 0;
		/*
		 * Readcount is used to hold the value which equals the number of times
		 * a operation should be executed such that the operation is complete.
		 */
		long readCount = 0;

		// It holds the throughput value
		double throughput = 0.0;

		double elapsedTime = 0.0;

		try {
			// Getting the RandomAccessFile object with read and write
			// permission.
			raf = new RandomAccessFile(this.path, "rw");

			// Obtaining the file channel from the RandomAccessFile object
			// created.
			fc = raf.getChannel();

			// allocating size for the bytebuffer declared.
			bb = ByteBuffer.allocate(size);

			fileSize = raf.length();
			// Calculating the readCount as mentioned above
			readCount = fileSize / size;

			// To perform random access, data is read front side first and then
			// back side and this is done backwards.
			// This boolean is used to handle that operation.
			boolean front = true;

			int count = 1;

			// Capturing the start time of the operation
			double start = System.nanoTime();

			while (readCount > 0) {

				bb = ByteBuffer.allocate(size);

				bb.clear();
				// Reading from the front side of the file

				if (front) {
					noOfBytesRead = fc.read(bb);
					position = position + (fileSize - (size * count));
					fc.position(position);
					front = false;
				}

				// Reading from the back side of the file
				else {
					noOfBytesRead = fc.read(bb);
					position = position - (fileSize - (size * count));
					fc.position(position);
					front = true;
				}

				// Updating the readCount value
				readCount--;

				count += 1;
			}

			// Capturing the end time of the operation
			double end = System.nanoTime();

			// Calculating the elapsedTime for the operation
			elapsedTime = end - start;
			elapsedTime = elapsedTime * 1e-9;

			// Calcuating the throughput
			throughput = fileSize / elapsedTime;
			throughput = (throughput) / (1024 * 1024);

			// Closing the file channel
			fc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		latency = elapsedTime * 1e3;
		benchmark = throughput;
		System.gc();
	}

	// writeRand is the method used to read disk sectors sequentially
	void writeRand(int size) {

		// Java's RandomAccessFile is used for handling file.
		RandomAccessFile raf = null;

		// Java's FileChannel is used for performing file operations such as
		// file read, file write, file seeking, etc.,
		FileChannel fc = null;

		// A byte buffer to hold the bytes to be read from the file.
		ByteBuffer bb = null;

		// Once the bytes are written from the file using FileChannel object, it
		// returns the number of bytes written.
		// So holding that value here
		int noOfBytesWritten = 0;

		// File position to seek
		long position = 0;

		// It holds the throughput value
		double throughput = 0;

		double elapsedTime = 0.0;
		/*
		 * writeCount is used to hold the value which equals the number of times
		 * a operation should be executed such that the operation is complete.
		 */
		long writeCount = 0;

		try {
			// Getting the RandomAccessFile object with read and write
			// permission.
			raf = new RandomAccessFile(this.path, "rw");

			// Obtaining the file channel from the RandomAccessFile object
			// created.
			fc = raf.getChannel();

			// allocating size for the bytebuffer declared.
			bb = ByteBuffer.allocate(size);

			// Calculating the readCount as mentioned above
			writeCount = this.fileSize / size;

			// To perform random access, data is write front side first and then
			// back side and this is done backwards.
			// This boolean is used to handle that operation.
			int count = 1;

			boolean front = true;

			// random bytes to write into the file
			byte[] bytes = new byte[size];
			Random random = new Random();
			random.nextBytes(bytes);

			// Copying the byte array into the bytebuffer
			for (int i = 0; i < bytes.length; i++) {
				bb.put((byte) bytes[i]);
			}

			// Capturing the start time of the operation
			double start = System.nanoTime();

			while (writeCount > 0) {
				// Flipping the byte buffer, such that the position is updated
				// to start.
				bb.flip();

				// Writing from the front side of the file
				if (front) {
					noOfBytesWritten = fc.write(bb);
					position = position + (fileSize - (size * count));
					fc.position(position);
					front = false;
				}

				// Writing from the back side of the file
				else {
					noOfBytesWritten = fc.write(bb);
					position = position - (fileSize - (size * count));
					fc.position(position);
					front = true;
				}

				// Updating the writeCount value
				writeCount--;

				count += 1;
			}
			noOfBytesWritten = fc.write(bb);
			position = position - (fileSize - (size * count));
			fc.position(position);
			front = true;

			// Capturing the end time of the operation
			double end = System.nanoTime();

			// Calculating the elapsedTime for the operation
			elapsedTime = end - start;

			elapsedTime = elapsedTime * 1e-9;

			// Calculating the throughput
			throughput = fileSize / elapsedTime;
			throughput = (throughput) / (1024 * 1024);

			// Closing the file channel
			fc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		latency = elapsedTime * 1e3;
		benchmark = throughput;
		System.gc();
	}

	@Override
	public void run() {
		// Decides which operation to perform according to the operationType
		// passed when creating the object.

		if (operationType == 1) { // writeSeq
			System.out.println("\n" + accessSize + " bytes sequential write by thread" + threadId);
			writeSeq(accessSize);
		} else if (operationType == 2) { // writeRand
			System.out.println("\n" + accessSize + " bytes random write by thread" + threadId);
			writeRand(accessSize);
		} else if (operationType == 3) { // readSeq
			System.out.println("\n" + accessSize + " bytes sequential read by thread" + threadId);
			readSeq(accessSize);
		} else if (operationType == 4) { // readRand
			System.out.println("\n" + accessSize + " bytes random read by thread" + threadId);
			readRand(accessSize);
		}
	}

	synchronized void printThroughput() {
		// Printing results obtained.
		System.out.println("Throughput for the operation is " + this.benchmark + " MB/s");
		System.out.printf("Latency for the operation is %.6f milliseconds \n", this.latency);
	}

}
