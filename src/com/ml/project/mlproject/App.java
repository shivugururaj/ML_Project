package com.ml.project.mlproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ml.project.mlproject.classifiers.NaiveBayesClassifier;
import com.opencsv.CSVWriter;

public class App {

  static final String INPUT_FILE = "input/train_users_2.csv";
  static final String OUTPUT_FILE = "output/train_users.csv";
  static final String OUTPUT_FILE_SVM = "python/train_users_libsvm.csv";

  public static void main(String[] args) throws IOException, ParseException {

    if(!new File(OUTPUT_FILE_SVM).exists()) {
      processCsv();
    }
    
    NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier();
    naiveBayesClassifier.classify();
  }
  
  private static void processCsv() throws IOException, ParseException {
    CSVWriter writer = new CSVWriter(new FileWriter(OUTPUT_FILE), ',', CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writerSVM = new CSVWriter(new FileWriter(OUTPUT_FILE_SVM), ',', CSVWriter.NO_QUOTE_CHARACTER);

    String line;
    BufferedReader bf = new BufferedReader(new FileReader(INPUT_FILE));
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
      writer.writeNext(attr.arr());
    }

    for (Attribute attr : attributesListSVM) {
      writerSVM.writeNext(attr.arr());
    }

    bf.close();
    writer.close();
    writerSVM.close();
  }

}
