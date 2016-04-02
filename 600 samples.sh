#!/bin/bash
echo "*** Running 600 plot experiments for benchmarking Amazon EC2 t2.micro instance ***"
cd ./CPU/
echo "Integer Operations"
javac Sample.java
java -Djava.compiler=NONE Sample
sleep 1
echo "Float Operations"
javac Sample2.java
java -Djava.compiler=NONE Sample2
cd ..
echo "* Exiting *"