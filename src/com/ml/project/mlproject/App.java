package com.ml.project.mlproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import com.ml.project.mlproject.classifiers.DecisionTreesClassifier;
import com.ml.project.mlproject.classifiers.GradientBoostingClassifier;
import com.ml.project.mlproject.classifiers.NaiveBayesClassifier;
import com.ml.project.mlproject.classifiers.RandomForestClassifier;

import au.com.bytecode.opencsv.CSVWriter;

public class App {


  static final String TRAIN_INPUT_FILE = "input/train_users.csv";
  static final String TEST_INPUT_FILE = "input/test_users.csv";

  static final String TRAIN_OUTPUT_FILE = "output/train_users.csv";
  static final String TEST_OUTPUT_FILE = "output/test_users.csv";

  static final String TRAIN_OUTPUT_FILE_SVM = "output/train_users_libsvm.csv";
  static final String TEST_OUTPUT_FILE_SVM = "output/test_users_libsvm.csv";

  static final String TRAIN_FINAL_SVM_FILE = "output/train_users_libsvm.txt";
  static final String TEST_FINAL_SVM_FILE = "output/test_users_libsvm.txt";

  static final String ACCURACY_OUTPUT_FILE = "output/accuracy.txt";

  public static void main(String[] args) throws IOException, ParseException {


    File evaluationFile = new File("output/Results.txt");
    if(evaluationFile.exists())
    {
      evaluationFile.delete();
      evaluationFile.createNewFile();
    }
    else
      evaluationFile.createNewFile();
    
    if (new File(TRAIN_FINAL_SVM_FILE).exists() && new File(TEST_FINAL_SVM_FILE).exists()) {

      SparkConf conf = new SparkConf().setAppName("ML Classification").setMaster("local")
          .set("spark.driver.allowMultipleContexts", "true");
      JavaSparkContext jsc = new JavaSparkContext(conf);

      StringBuffer buffer = new StringBuffer();
      double accuracy;

      System.out.println("Classifying using Naive Bayes:");
      NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier(jsc);
      accuracy = naiveBayesClassifier.classify();
      buffer.append("Naive Bayes Accuracy: ").append(accuracy).append("\n");

      System.out.println("Classifying using Random Forests:");
      RandomForestClassifier randomForestClassifier = new RandomForestClassifier(jsc);
      accuracy = randomForestClassifier.classify();
      buffer.append("Random Forests Accuracy: ").append(accuracy).append("\n");

      System.out.println("Classifying using Gradient Boosting:");
      GradientBoostingClassifier gradientBoostingClassifier = new GradientBoostingClassifier(jsc);
      accuracy = gradientBoostingClassifier.classify();
      buffer.append("Gradient Boosting Accuracy: ").append(accuracy).append("\n");

      /*DecisionTreesClassifier decisionTreesClassifier = new DecisionTreesClassifier(jsc);
      accuracy = decisionTreesClassifier.classify();
      buffer.append("Decision Trees Accuracy: ").append(accuracy).append("\n");*/

      /*FileWriter writer = new FileWriter(new File(ACCURACY_OUTPUT_FILE));
      writer.write(buffer.toString());
      writer.flush();
      writer.close();
*/
      FileWriter writer = new FileWriter(("output/Results.txt"), true);
      BufferedWriter bufferWritter = new BufferedWriter(writer);
          bufferWritter.write(buffer.toString());
          bufferWritter.flush();
          bufferWritter.close();
    } else {
      processTrainCsv();
      processTestCsv();
    }
  }

  private static void processTestCsv() throws IOException, ParseException {
    // TODO Auto-generated method stub

    CSVWriter testWriter = new CSVWriter(new FileWriter(TEST_OUTPUT_FILE), ',', CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter testWriterSVM = new CSVWriter(new FileWriter(TEST_OUTPUT_FILE_SVM), ',',
        CSVWriter.NO_QUOTE_CHARACTER);

    String line;
    BufferedReader bf = new BufferedReader(new FileReader(TEST_INPUT_FILE));
    bf.readLine();

    List<Attribute> attributesList = new ArrayList<Attribute>();
    List<Attribute> attributesListSVM = new ArrayList<Attribute>();
    Attribute attribute;

    while ((line = bf.readLine()) != null) {
      attribute = new Attribute();

      String[] lineEntry = line.split(",");

      attribute.setDateFirstBooking(lineEntry[3]);
      attribute.setUserSince(lineEntry[1]);
      attribute.setGender(lineEntry[4]);
      attribute.setAge(lineEntry[5]);
      attribute.setSignupMethod(lineEntry[6]);
      attribute.setSignupFlow(lineEntry[7]);
      attribute.setLanguage(lineEntry[8]);
      attribute.setAffliateChannel(lineEntry[9]);
      attribute.setAffliateProvider(lineEntry[10]);
      attribute.setFirstAffliate(lineEntry[11]);
      attribute.setSignupApp(lineEntry[12]);
      attribute.setFirstDevice(lineEntry[13]);
      attribute.setFirstBrowser(lineEntry[14]);
      attribute.setDestinationCountry(null);

      attributesList.add(attribute);
      attributesListSVM.add(attribute.process(attribute));
    }

    for (Attribute attr : attributesList) {
      testWriter.writeNext(attr.arr());
    }

    for (Attribute attr : attributesListSVM) {
      testWriterSVM.writeNext(attr.arr());
    }

    bf.close();
    testWriter.close();
    testWriterSVM.close();

    Mappings.writeMappings();

  }

  private static void processTrainCsv() throws IOException, ParseException {
    CSVWriter trainWriter = new CSVWriter(new FileWriter(TRAIN_OUTPUT_FILE), ',', CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter trainWriterSVM = new CSVWriter(new FileWriter(TRAIN_OUTPUT_FILE_SVM), ',',
        CSVWriter.NO_QUOTE_CHARACTER);

    String line;
    BufferedReader bf = new BufferedReader(new FileReader(TRAIN_INPUT_FILE));
    bf.readLine();

    List<Attribute> attributesList = new ArrayList<Attribute>();
    List<Attribute> attributesListSVM = new ArrayList<Attribute>();
    Attribute attribute;
    Mappings.initialize();

    while ((line = bf.readLine()) != null) {
      attribute = new Attribute();

      String[] lineEntry = line.split(",");

      String classLabel = lineEntry[15].trim();
      String dateFirstBooking = lineEntry[3].trim();

      if ((classLabel != null && !classLabel.equalsIgnoreCase("NDF") && !classLabel.isEmpty())
          && (dateFirstBooking != null && !dateFirstBooking.equalsIgnoreCase("-unkown-")
              && !dateFirstBooking.isEmpty())) {
        attribute.setDateFirstBooking(lineEntry[3]);
        attribute.setUserSince(lineEntry[1]);
        attribute.setGender(lineEntry[4]);
        attribute.setAge(lineEntry[5]);
        attribute.setSignupMethod(lineEntry[6]);
        attribute.setSignupFlow(lineEntry[7]);
        attribute.setLanguage(lineEntry[8]);
        attribute.setAffliateChannel(lineEntry[9]);
        attribute.setAffliateProvider(lineEntry[10]);
        attribute.setFirstAffliate(lineEntry[11]);
        attribute.setSignupApp(lineEntry[12]);
        attribute.setFirstDevice(lineEntry[13]);
        attribute.setFirstBrowser(lineEntry[14]);
        attribute.setDestinationCountry(lineEntry[15]);

        attributesList.add(attribute);
        attributesListSVM.add(attribute.process(attribute));
      }
    }

    for (Attribute attr : attributesList) {
      trainWriter.writeNext(attr.arr());
    }

    for (Attribute attr : attributesListSVM) {
      trainWriterSVM.writeNext(attr.arr());
    }

    bf.close();
    trainWriter.close();
    trainWriterSVM.close();

    Mappings.writeMappings();
  }

}