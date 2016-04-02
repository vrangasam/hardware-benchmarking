#include<stdio.h>
#include<string.h>
#include<sys/time.h>
#include<pthread.h>
#include<stdlib.h>
#define MAXTHREADS 2
#define MEMORY 52428800 //This number of bytes is used for read-write operations
long start_time[MAXTHREADS]; //to hold the starting time of a thread
long finish_time[MAXTHREADS]; //to hold the finishing time of a thread

//a structure that holds the experiment's parameters such as thread_count, thread_id, block_size.
typedef struct thread_type{
	int thread_id;
	int thread_count;
	long long int block_size;
} et;

//a generic sequential_access function to perfomr sequential read+write operation
void* sequential_access(void *arg);

//a generic random_access function to perfomr random read+write operation
void* random_access(void *arg);

// estimate_values is used to calculate the time taken to perform read+write operation and the throughput in each experiment.
// Time estimation is done for both single threaded experiment as well as double threaded experiment.
void estimate_values(et);

// main() receives a particular experiment number to perform among 12 available experiments. 
int main(int argc, char *argv[]){
	double time_taken; //time_taken is used for holding the latency
	int exp, returnvalue; //exp is the experiment type to run, returnvalue is the value returned while a thread is being created.
	sscanf (argv[1], "%i", &exp); 
	
	
	printf("\n* Performing six Sequential Access experiments *\n");
	sleep(1);
	long j = 1;
	/*SEQUENTIAL*/
	int i =1 ;
	for(j=1;j<=1024*1024;j=j*1024,i++) //sequential access of memory  in 1 bytes, 1K bytes, 1M bytes fashion using 1 thread
	{
		// declaring a pthread variable
		pthread_t thread;
		printf("\n%d)Sequential access of %ld byte using one thread",i,j);
		//single thread structure is created
		et one;
		one.thread_id = 1;
		one.thread_count = 1;
		one.block_size = j;
		//1 thread is created for performing sequential read-write operation in 1 byte, 1K bytes, 1M bytes fashion
		returnvalue = pthread_create(&thread, NULL, sequential_access, &one);
		//waiting for thread to complete
		pthread_join(thread,NULL);
		estimate_values(one);
	}

	for(j=1;j<=1024*1024;j=j*1024,i++) //sequential access of memory  in 1 bytes, 1K bytes, 1M bytes fashion using 2 threads
	{
		// declaring an array of two pthreads
		pthread_t thread[2];
		printf("\n%d)Sequential access of %ld byte using two threads",i,j);
		//two thread structures are created
		et one, two;

		one.thread_id = 1;
		one.thread_count = 2;
		one.block_size = j;

		two.thread_id = 2;
		two.thread_count = 2;
		two.block_size = j;
		//2 threads are created for performing sequential read-write operation in 1 byte, 1K bytes, 1M bytes fashion
		returnvalue = pthread_create(&thread[0], NULL, sequential_access, &one);
		returnvalue = pthread_create(&thread[1], NULL, sequential_access, &two);
		//waiting for threads to complete
		pthread_join(thread[0],NULL);
		pthread_join(thread[1],NULL);
		estimate_values(two);
	}
	/*RANDOM*/
	printf("\n* Performing six Random Access experiments *");
	i = 1;
	for(j=1;j<=1024*1024;j=j*1024,i++) //random access of memory  in 1 bytes, 1K bytes, 1M bytes fashion using 2 threads
	{
		// declaring a pthread variable
		pthread_t thread;
		printf("\n%d)Random access of %ld byte using one thread",i,j);
		//single thread structure is created
		et one;
		one.thread_id = 1;
		one.thread_count = 1;
		one.block_size = j;
		//1 thread is created for performing sequential read-write operation in 1 byte, 1K bytes, 1M bytes fashion
		returnvalue = pthread_create(&thread, NULL, random_access, &one);
		//waiting for thread to complete
		pthread_join(thread,NULL);
		estimate_values(one);
	}//

	for(j=1;j<=1024*1024;j=j*1024,i++) //for three values of bytes using 2 threads
	{
		// declaring an array of two pthreads
		pthread_t thread[2];
		printf("\n%d)Random access of %ld byte using two threads",i,j);
		//two thread structures are created
		et one, two;

		one.thread_id = 1;
		one.thread_count = 2;
		one.block_size = j;

		two.thread_id = 2;
		two.thread_count = 2;
		two.block_size = j;
		//2 threads are created for performing sequential read-write operation in 1 byte, 1K bytes, 1M bytes fashion
		returnvalue = pthread_create(&thread[0], NULL, random_access, &one);
		returnvalue = pthread_create(&thread[1], NULL, random_access, &two);
		//waiting for threads to complete
		pthread_join(thread[0],NULL);
		pthread_join(thread[1],NULL);
		estimate_values(two);
	}
}
	
//sequential_access does the operation of accessing memory sequentially in 1 Byte, 1KB, 1 MB fashion
void *sequential_access(void *arg){
	
	//fetching the thread type and experiment details from the argument arg.
	et type = *((et*) (arg)); 
	int tid = type.thread_id;
	int tc = type.thread_count;
	long long int bs = type.block_size;
	
	//temporary variable for loop
	long a;
	
	//timeval structures to get hold of starting time and finishing time of an experiment
	struct timeval start;
	struct timeval finish;
	
	//variables to perform memory operations
	char *original, *copy;
	
	//allocating memory for those variables
	original = (char *)malloc((MEMORY));
	copy = (char *)malloc((MEMORY));
	
	//setting all the bytes of 'original' variable as 'l'
	memset(original, 'l', MEMORY);
	
	//recording the start time of the experiment
	
	
	//starting the experiment by sequentially copying the source bytes from 'original' to the destination 'copy'. In this operation, both reading and writing operation done
	//if the accessing block size is 1MB(big), allocating a bigger memory to operate on, as the process is quicker.
	if(bs==1024*1024){
		long long int mem = 1024*1024*60;
		original = (char *)malloc((mem));
		copy = (char *)malloc((mem));
		gettimeofday(&start,NULL);
		for(a=0; a < (mem/(bs)); a++){
				memcpy(copy+(a*bs), original+(a*bs), bs);
			}
	}
	//if the accessing block size in smaller, then performing operating on same memory size as the process is longer.
	else{
		gettimeofday(&start,NULL);
		for(a=0; a < (MEMORY/(bs)); a++){
			memcpy(copy+(a*bs), original+(a*bs), bs);
		}
	}

	//recording the finish time of the experiment
	gettimeofday(&finish,NULL);
	
	
	//saving the start time and finish time in start_time and finish_time array.
	start_time[type.thread_id-1] = (start.tv_sec*1000000)+start.tv_usec;
	finish_time[type.thread_id-1] = (finish.tv_sec*1000000)+finish.tv_usec;
	
	//freeing the memory allocated
	free(original);
	free(copy);
}

void *random_access(void *arg){
	
	//fetching the thread type and experiment details from the argument arg.
	et type = *((et*) (arg));
	int tid = type.thread_id;
	int tc = type.thread_count;
	long long int bs = type.block_size;
	
	//temporary variable for loop
	long a;
	
	//timeval structures to get hold of starting time and finishing time of an experiment
	struct timeval start;
	struct timeval finish;
	
	//variables to perform memory operations
	char *original, *copy;
	
	//allocating memory for those variables
	original = (char *)malloc((MEMORY));
	copy = (char *)malloc((MEMORY));
	
	//setting all the bytes of 'original' variable as 'x'
	memset(original, 'x', MEMORY);
	
	//recording the start time of the experiment
	
	
	//variable to hold the random address to access
	int v;
	
	//starting the experiment by randomly copying the source bytes from 'original' to the destination 'copy'. In this operation, both reading and writing operation done
	//if the accessing block size is 1MB(big), allocating a bigger memory to operate on, as the process is quicker.
	if(bs==1024*1024){
		long long int mem = 1024*1024*600;
		gettimeofday(&start,NULL);
		for(a=0; a < (mem/(bs)); a++){
			srand((unsigned)time(NULL));
			v =  rand() % bs;
			memcpy(copy+(v), original+(v), bs);
			}
	}
	//if the accessing block size in smaller, then performing operating on same memory size as the process is longer.
	else{
	gettimeofday(&start,NULL);
	for(a=0; a < (MEMORY/(bs)); a++){
		srand((unsigned)time(NULL));
		v =  rand() % bs;
		memcpy(copy+(v), original+(v), bs);
		}
	}
	//recording the finish time of the experiment
	gettimeofday(&finish,NULL);
	
	//saving the start time and finish time in start_time and finish_time array.
	start_time[type.thread_id-1] = (start.tv_sec*1000000)+start.tv_usec;
	finish_time[type.thread_id-1] = (finish.tv_sec*1000000)+finish.tv_usec;
	
	//freeing the memory allocated
	free(original);
	free(copy);
}

void estimate_values(et type){
	
	//start, finish counters
	long start, finish;
	
	double elapsed_time;
	start = start_time[0];
	finish = finish_time[0];
	
	//if there are two threads, start time and end time are calculated as a whole for two threads.
	if(type.thread_count==2){
		if(start > start_time[1])
			start = start_time[1];
		if(finish < finish_time[1])
			finish = finish_time[1];
	}
	elapsed_time = (double) (finish - start)/1000000.0;
	
	//Latency
	printf("\n* Latency is %lf milliseconds *", elapsed_time*1000);
	
	//Throughput calculation
	printf("\n* Throughput is %lf MB/s *\n",(double)(type.thread_count*MEMORY)/(elapsed_time*1024*1024));

}
