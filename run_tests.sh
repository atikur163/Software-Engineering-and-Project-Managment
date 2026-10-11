#!/bin/bash
# Usage: ./run_tests.sh Firstname_Thread   (class name without .java)
CLASS=${1:-Firstname_Thread}
javac $CLASS.java || exit 1
mkdir -p outputs
TESTS=("1 1000" "2 10000" "5 10000" "10 50000" "20 50000" "50 50000" "100 50000")
for t in "${TESTS[@]}"; do
  set -- $t
  java $CLASS $1 $2 true | tee outputs/safe_${1}threads.txt
  for run in 1 2 3 4 5; do
    java $CLASS $1 $2 false | tee outputs/unsafe_${1}threads_run${run}.txt
  done
done
