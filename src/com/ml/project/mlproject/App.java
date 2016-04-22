package com.ml.project.mlproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVWriter;

public class App {

  static String[] removeColumn(String[] arr, int remIndex) {
    int numElts = arr.length - (remIndex + 1);
    System.arraycopy(arr, remIndex + 1, arr, remIndex, numElts);
    return arr;
  }

  public static void main(String[] args) throws IOException, ParseException {

    String csvFile = "dataset/train_users_2.csv";
    CSVWriter writer = new CSVWriter(new FileWriter("dataset/train_users.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
    String line = "";
    BufferedReader bf = new BufferedReader(new FileReader(csvFile));
    bf.readLine();

    Map<String, String> labelMap = new HashMap<String, String>();
    labelMap.put("US", "0");
    labelMap.put("FR", "1");
    labelMap.put("CA", "2");
    labelMap.put("GB", "3");
    labelMap.put("ES", "4");
    labelMap.put("IT", "5");
    labelMap.put("PT", "6");
    labelMap.put("NL", "7");
    labelMap.put("DE", "8");
    labelMap.put("AU", "9");
    labelMap.put("other", "10");

    while ((line = bf.readLine()) != null) {

      char[] ageVector = new char[20];

      String[] lineEntry = line.split(",");
      String dateAccountCreated = lineEntry[1];
      String agevalue = lineEntry[5];
      String classLable = lineEntry[15];
      String dateFirstBooked = lineEntry[3];
      String gender = lineEntry[4];
      classLable = classLable.trim();
      dateFirstBooked = dateFirstBooked.trim();
      if (classLable.isEmpty() || dateFirstBooked.isEmpty() || classLable.equalsIgnoreCase("NDF")
          || dateFirstBooked.equalsIgnoreCase("-unknown-") || classLable == null || dateFirstBooked == null) {
        // skip this entry
      }

      else {
        // handling gender data
        if (gender.trim().isEmpty() || gender == null) {
          gender = "-unknown-";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        Date parsedFirstBooked = dateFormat.parse(dateFirstBooked);
        Date parsedAccountCreated = dateFormat.parse(dateAccountCreated);

        Integer diffMonths = getMonthsDifference(parsedAccountCreated, parsedFirstBooked);
        lineEntry[1] = diffMonths.toString();

        // update dates to season
        String[] firstBookedDate = dateFirstBooked.split("-");
        int bookingMonth = Integer.parseInt(firstBookedDate[1]);
        if (bookingMonth >= 1 && bookingMonth <= 4) {
          dateFirstBooked = "Spring";
        } else if (bookingMonth >= 5 && bookingMonth <= 7) {
          dateFirstBooked = "Summer";
        } else if (bookingMonth >= 8 && bookingMonth <= 10) {
          dateFirstBooked = "Fall";
        } else {
          dateFirstBooked = "Winter";
        }

        if (agevalue.isEmpty() || agevalue == null) {
          // default the age value , change the class label to number
          // and update the training file
          lineEntry[5] = "00000000000000000000";
          lineEntry[15] = labelMap.get(classLable);
          lineEntry[3] = dateFirstBooked;
          lineEntry[4] = gender;
          lineEntry = removeColumn(lineEntry, 2);
          lineEntry = Arrays.copyOfRange(lineEntry, 1, lineEntry.length);

          writer.writeNext(lineEntry);

        } else {
          // get the age value and check if its >5 or <100
          double ageVal = Double.parseDouble((agevalue.trim()));
          int age = (int) ageVal;

          if (age > 5 || age < 100) {
            // insert a vector value of this format
            // 00000000000000000010 , change the classlabel to
            // number and update the training file
            int index = age / 5;
            for (int i = 0; i < ageVector.length; i++) {
              if (i == index) {
                ageVector[index] = '1';

              } else
                ageVector[i] = '0';
            }
            lineEntry[5] = new String(ageVector);
            lineEntry[15] = labelMap.get(classLable);
            lineEntry[3] = dateFirstBooked;
            lineEntry[4] = gender;
            lineEntry = removeColumn(lineEntry, 2);
            lineEntry = Arrays.copyOfRange(lineEntry, 1, lineEntry.length);

            writer.writeNext(lineEntry);

          }
        }

      }

    }
    writer.close();
  }

  public static final int getMonthsDifference(Date startDate, Date endDate) {
    Calendar startCalendar = new GregorianCalendar();
    startCalendar.setTime(startDate);
    Calendar endCalendar = new GregorianCalendar();
    endCalendar.setTime(endDate);

    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    
    if(diffMonth < 0) {
      diffMonth = 0;
    }
    
    return diffMonth;
  }

}