Machine Learning Final Project:
===============================

Team:
Deeptha Parsi Diwakar - dpd140030
Shivu Gururaj - sxg144730
Sudharsanan Muralidharan - sxm149130


Prerequisities:
---------------
1. Java 1.8
2. Open CSV (Included in lib)
3. Spark MLLib 1.6.1
4. Python

Steps to run:
-------------
1. Import the project in eclipse
2. Add the mllib assembly jar and opencsv to the Java build path
3. Run App.java
4. Since the preprocessing of csv is not done yet the program preprocesses it first automatically
5. It reads the input files [train_users.csv and test_users.csv] and generates [train_users.csv, train_users_libsvm.csv, test_users.csv and test_users_libsvm.csv] files.
6. After the preprocessed csv files are genereated run the python script inside python/ to convert it to libsvm format. Or just run bash run.sh inside python.
7. This generates [train_users_libsvm.txt and test_users_libsvm.txt] which are given as input to the program.
8. Run App.java again now which will run the classifiers.
9. The results are written to output/Results.txt and predictions to output/predictions/[prediction_file].csv.
