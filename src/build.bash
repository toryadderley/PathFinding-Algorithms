#!/bin/bash
set -e -x
echo "Building..."
javac *.java
echo "Runing"
java Agent
