# Machine Learning Final Project:

### Prerequisities:
- Java 1.8
- Open CSV (Included in lib)
- Spark MLLib 1.6.1
- Python

###Steps to run:
- Import the project in eclipse
- Add the mllib assembly jar and opencsv to the Java build path
- Run App.java 
- Since the preprocessing of csv is not done yet the program preprocesses it first automatically
- It reads the input files [**train_users.csv** and **test_users.csv**] and generates [**train_users.csv**, **train_users_libsvm.csv**, **test_users.csv** and **test_users_libsvm.csv**] files.
- After the preprocessed csv files are genereated run the python script inside python/ to convert it to libsvm format. Or just run bash run.sh inside python.
- This generates [**train_users_libsvm.txt** and **test_users_libsvm.txt**] which are given as input to the program.
- Run App.java again now which will run the classifiers.
- The results are written to **output/Results.txt** and predictions to **output/predictions/[prediction_file].csv**.
