#!/bin/bash
python csv2libsvm.py /Users/shivugururaj/Documents/problemsolving/mlproject/output/train_users_libsvm.csv /Users/shivugururaj/Documents/problemsolving/mlproject/output/train_users_libsvm.txt 13
echo "Written train libsvm output to output/train_users_libsvm.txt"

python csv2libsvm.py /Users/shivugururaj/Documents/problemsolving/mlproject/output/test_users_libsvm.csv /Users/shivugururaj/Documents/problemsolving/mlproject/output/test_users_libsvm.txt -1
echo "Written test libsvm output to output/test_users_libsvm.txt"