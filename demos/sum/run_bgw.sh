#!/bin/bash

# Kill any existing java processes
killall java 2>/dev/null || true

# Create server directories if they don't exist
mkdir -p server1
mkdir -p server2
mkdir -p server3

# Copy the jar file to each server directory
cp target/fresco-demo-sum.jar server1/
cp target/fresco-demo-sum.jar server2/
cp target/fresco-demo-sum.jar server3/

# BGW integration approach
echo "Starting computation with dummyarithmetic protocol (BGW JAR included)..."
(cd server1 && java -cp fresco-demo-sum.jar:${HOME}/.m2/repository/dk/alexandra/fresco/bgw/1.3.8/bgw-1.3.8.jar dk.alexandra.fresco.demo.InputSumExample -e SEQUENTIAL_BATCHED -i 1 -l INFO -p 1:localhost:8081 -p 2:localhost:8082 -p 3:localhost:8083 -s dummyarithmetic > log.txt 2>&1 &)
(cd server2 && java -cp fresco-demo-sum.jar:${HOME}/.m2/repository/dk/alexandra/fresco/bgw/1.3.8/bgw-1.3.8.jar dk.alexandra.fresco.demo.InputSumExample -e SEQUENTIAL_BATCHED -i 2 -l INFO -p 1:localhost:8081 -p 2:localhost:8082 -p 3:localhost:8083 -s dummyarithmetic > log.txt 2>&1 &)
(cd server3 && java -cp fresco-demo-sum.jar:${HOME}/.m2/repository/dk/alexandra/fresco/bgw/1.3.8/bgw-1.3.8.jar dk.alexandra.fresco.demo.InputSumExample -e SEQUENTIAL_BATCHED -i 3 -l INFO -p 1:localhost:8081 -p 2:localhost:8082 -p 3:localhost:8083 -s dummyarithmetic 2>&1 | tee log.txt)

echo ""
echo "============================================================"
echo "BGW JAR is included in the classpath, but we're using dummyarithmetic protocol."
echo "To fully use the BGW protocol, we would need to:"
echo "1. Fix the linter errors in CmdLineProtocolSuite.java"
echo "2. Add BGW to the supported protocol suites in CmdLineUtil.java"
echo "3. Ensure the BGW classes are available in the classpath"
echo "============================================================" 