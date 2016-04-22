#!/bin/bash
python csv2libsvm.py train_users_libsvm.csv ../output/train_users_libsvm.txt 13
echo "Written lbsvm output to output/train_users_libsvm.txt"