#!/bin/bash
echo "*** Running experiments for benchmarking Amazon EC2 t2.micro instance ***"
echo
read -p "Please enter your choice:`echo $'\n 1. CPU benchmarking \n 2. Memory benchmarking \n 3. Disk benchmarking\n 4. All the three experiments \n'`" doit
echo
case $doit in  
1) 
cd ./CPU/
sleep 1
echo "* Compiling CPU Benchmark Java code *"
echo
javac CPUBenchMarkApp.java
sleep 1
echo "* Performing CPU benchmark experiments *"
echo
java -Djava.compiler=NONE CPUBenchMarkApp
echo
cd ..
;;

2)
sleep 1
cd ./Memory/
sleep 1
echo "* Compiling Memory Benchmark C code *"
echo
gcc memory.c -lpthread
sleep 1
echo "* Performing Memory benchmark experiments *"
echo
./a.out 0
sleep 1
echo "* All the benchmark experiments are completed *"
cd ..
sleep 1
;;
3)
cd ./Disk/
sleep 1
echo "* Compiling Disk Benchmark Java code *"
echo
javac DiskBenchMarkApp.java
sleep 1
echo "* Performing Disk benchmark experiments *"
echo
java -Djava.compiler=NONE DiskBenchMarkApp
echo
cd ..
sleep 1
;;
4)
cd ./CPU/
sleep 1
echo "* Compiling CPU Benchmark Java code *"
echo
javac CPUBenchMarkApp.java
sleep 1
echo "* Performing CPU benchmark experiments *"
echo
java -Djava.compiler=NONE CPUBenchMarkApp
echo
cd ..
sleep 1
cd ./Memory/
sleep 1
echo "* Compiling Memory Benchmark C code *"
echo
gcc memory.c -lpthread
sleep 1
echo "* Performing Memory benchmark experiments *"
echo
./a.out 0
sleep 1
echo "* All the benchmark experiments are completed *"
cd ..
sleep 1
cd ./Disk/
sleep 1
echo "* Compiling Disk Benchmark Java code *"
echo
javac DiskBenchMarkApp.java
sleep 1
echo "* Performing Disk benchmark experiments *"
echo
java -Djava.compiler=NONE DiskBenchMarkApp
echo
cd ..
sleep 1
;;
*)echo "Option entered is wrong" 
	sleep 1
	echo "Exiting the script"
	sleep 1
	exit 1
	;;
esac
echo "* All the experiments are completed *"
sleep 1
echo "* Exiting *"
