#!/bin/bash
python csv2libsvm.py ../output/train_users_libsvm.csv ../output/train_users_libsvm.txt 13
echo "Written train libsvm output to output/train_users_libsvm.txt"

#!/bin/bash
python csv2libsvm.py ../output/test_users_libsvm.csv ../output/test_users_libsvm.txt -1
echo "Written test libsvm output to output/test_users_libsvm.txt"