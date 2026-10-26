#!/bin/sh
# Compiles and runs the Internship Management System (Mac / Linux).
# Usage:  sh run.sh          (starts on port 8080)
#         sh run.sh 9090     (starts on a different port)
set -e
mkdir -p out
javac -d out src/model/*.java src/exception/*.java src/service/*.java src/util/*.java src/web/*.java src/main/*.java
java -cp out main.Main "$@"
