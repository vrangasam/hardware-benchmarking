How to run?

1. Make sure bash is available on the system.

2. Make sure java and gcc are installed on the system.

3. Make sure the availability of following files in the folder

	CPU benchmark
	./CPU/CPUBenchMarkApp.java

	Disk benchmark
	./Disk/DiskBenchMarkApp.java
	./Disk/Utility.java

	Memory benchmark
	./Memory/memory.c
	
4. Once all the above is made sure, run the following commands in root mode:

	$ chmod 755 bash.sh
	$ ./bash.sh
	
5. Once the bash script is executed, a option to select the particular benchmark will be shown.

	Sample:
	
	*** Running experiments for benchmarking Amazon EC2 t2.micro instance ***

		Please enter your choice:
		 1. CPU benchmarking 
		 2. Memory benchmarking 
		 3. Disk benchmarking
		 4. All the three experiments
		 
		 
6. Choose the appropriate experiments to run.

	Sample:
	
	*** Running experiments for benchmarking Amazon EC2 t2.micro instance ***

		Please enter your choice:
		 1. CPU benchmarking 
		 2. Memory benchmarking 
		 3. Disk benchmarking
		 4. All the three experiments 4
		 
		* Compiling CPU Benchmark Java code *

		* Performing CPU benchmark experiments *

		Thread 0 results in 4.512996 gflops and it took 0.476 secs to run
		Thread 1 results in 2.187076 gflops and it took 0.982 secs to run
		Thread 0 results in 2.176802 gflops and it took 0.987 secs to run
		.
		.
		.
		.
		
7. While running all the experiments, results will be shown readily.

8. To run the 600 samples experiment, run ./600samples.sh

8. Once all the experiments are completed, the bash script will exit automatically.






	