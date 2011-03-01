#!/bin/bash

for i in {0..10} 
do 
echo "=========== Running Test $i ======" ;
#mvn clean;
mvn test;
done

