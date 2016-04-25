package com.ml.project.mlproject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVWriter;

public class Mappings {

  private static final String MAPPINGS = "output/mappings.txt";
  private static final String PREDICTIONS = "output/predictions/predictions_";
  private static Map<String, Integer> labelMap = new HashMap<String, Integer>();
  private static Map<String, Integer> signupMethodMap = new HashMap<String, Integer>();
  private static Map<String, Integer> signupMethodCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> languageMap = new HashMap<String, Integer>();
  private static Map<String, Integer> languageCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> affliateChannelMap = new HashMap<String, Integer>();
  private static Map<String, Integer> affliateChannelCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> affliateProviderMap = new HashMap<String, Integer>();
  private static Map<String, Integer> affliateProviderCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstAffliateMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstAffliateCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> signupAppMap = new HashMap<String, Integer>();
  private static Map<String, Integer> signupAppCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstDeviceMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstDeviceCountMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstBrowserMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstBrowserCountMap = new HashMap<String, Integer>();

  public static void initialize() {
    labelMap.put("US", 1);
    labelMap.put("FR", 2);
    labelMap.put("CA", 3);
    labelMap.put("GB", 4);
    labelMap.put("ES", 5);
    labelMap.put("IT", 6);
    labelMap.put("PT", 7);
    labelMap.put("NL", 8);
    labelMap.put("DE", 9);
    labelMap.put("AU", 10);
    labelMap.put("other", 11);
  }

  public static Integer getSeason(String date) {
    String[] firstBookedDate = date.split("-");
    int bookingMonth = Integer.parseInt(firstBookedDate[1]);

    if (bookingMonth >= 1 && bookingMonth <= 4) {
      return 1;
    } else if (bookingMonth >= 5 && bookingMonth <= 7) {
      return 2;
    } else if (bookingMonth >= 8 && bookingMonth <= 10) {
      return 3;
    } else {
      return 4;
    }
  }

  public static Integer getAgeVector(Integer age) {
    if (age > 5 || age < 100) {
      return age / 5;
    }

    return 0;
  }

  public static final int getMonthsDifference(Date startDate, Date endDate) {
    Calendar startCalendar = new GregorianCalendar();
    startCalendar.setTime(startDate);
    Calendar endCalendar = new GregorianCalendar();
    endCalendar.setTime(endDate);

    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

    if (diffMonth < 0) {
      diffMonth = 0;
    }

    return diffMonth;
  }

  public static Map<String, Integer> getLabelMap() {
    return labelMap;
  }

  public static Integer getsignupMethod(String signupMethod) {
    if (signupMethodMap.containsKey(signupMethod)) {
      int count = signupMethodCountMap.getOrDefault(signupMethod, 1) + 1;
      signupMethodCountMap.put(signupMethod, count);
      return signupMethodMap.get(signupMethod);
    } else {
      int size = signupMethodMap.size() + 1;
      signupMethodMap.put(signupMethod, size);
      return size;
    }
  }

  public static Integer getLanguage(String language) {

    languageCountMap.put(language, languageCountMap.getOrDefault(language, 1) + 1);

    if (language.equalsIgnoreCase("en")) {
      languageMap.putIfAbsent("en", 1);
      return 1;
    } else {
      languageMap.putIfAbsent("non-en", 2);
      return 2;
    }
  }

  public static Integer getAffliateChannel(String affliateChannel) {
    if (affliateChannelMap.containsKey(affliateChannel)) {
      int count = affliateChannelCountMap.getOrDefault(affliateChannel, 1) + 1;
      affliateChannelCountMap.put(affliateChannel, count);
      return affliateChannelMap.get(affliateChannel);
    } else {
      int size = affliateChannelMap.size() + 1;
      affliateChannelMap.put(affliateChannel, size);
      return size;
    }
  }

  public static Integer getAffliateProvider(String affliateProvider) {
    affliateProviderCountMap.put(affliateProvider, affliateProviderCountMap.getOrDefault(affliateProvider, 1) + 1);
    if (affliateProviderMap.containsKey(affliateProvider)) {
      return affliateProviderMap.get(affliateProvider);
    } else {
      int size = affliateProviderMap.size() + 1;
      affliateProviderMap.put(affliateProvider, size);
      return size;
    }
  }

  public static Integer getFirstAffliate(String firstAffliate) {

    firstAffliateCountMap.put(firstAffliate, firstAffliateCountMap.getOrDefault(firstAffliate, 1) + 1);

    if (firstAffliateMap.containsKey(firstAffliate)) {
      return firstAffliateMap.get(firstAffliate);
    } else {
      int size = firstAffliateMap.size() + 1;
      firstAffliateMap.put(firstAffliate, size);
      return size;
    }
  }

  public static Integer getSignupApp(String signupApp) {
    signupAppCountMap.put(signupApp, signupAppCountMap.getOrDefault(signupApp, 1) + 1);
    if (signupApp.equalsIgnoreCase("Web")) {
      signupAppMap.putIfAbsent("Web", 1);
      return 1;
    } else {
      signupAppMap.putIfAbsent("Mobile", 2);
      return 2;
    }
  }

  public static Integer getFirstDevice(String firstDevice) {

    firstDeviceCountMap.put(firstDevice, firstDeviceCountMap.getOrDefault(firstDevice, 1) + 1);

    if (firstDeviceMap.containsKey(firstDevice)) {
      return firstDeviceMap.get(firstDevice);
    } else {
      int size = firstDeviceMap.size() + 1;
      firstDeviceMap.put(firstDevice, size);
      return size;
    }
  }

  public static Integer getFirstBrowser(String firstBrowser) {

    firstBrowser = firstBrowser.toLowerCase();
    firstBrowserCountMap.put(firstBrowser, firstBrowserCountMap.getOrDefault(firstBrowser, 1) + 1);

    if (firstBrowser.contains("chrome")) {
      firstBrowserMap.putIfAbsent("Chrome", 1);
      return 1;
    } else if (firstBrowser.contains("safari")) {
      firstBrowserMap.putIfAbsent("Safari", 2);
      return 2;
    } else if (firstBrowser.contains("mozilla") || firstBrowser.contains("firefox")) {
      firstBrowserMap.putIfAbsent("Mozilla", 3);
      return 3;
    } else {
      firstBrowserMap.putIfAbsent("Other", 4);
      return 4;
    }
  }

  public static void writeMappings() throws IOException {
    FileWriter writer = new FileWriter(new File(MAPPINGS));
    StringBuffer buffer = new StringBuffer();

    buffer.append("Label Mappings:").append("\n");
    buffer.append(labelMap.toString()).append("\n\n");

    buffer.append("Signup Method Mappings:").append("\n");
    buffer.append(signupMethodMap.toString()).append("\n");
    buffer.append(signupMethodCountMap.toString()).append("\n\n");

    buffer.append("Language Mappings:").append("\n");
    buffer.append(languageMap.toString()).append("\n");
    buffer.append(languageCountMap.toString()).append("\n\n");

    buffer.append("Affliate Channe; Mappings:").append("\n");
    buffer.append(affliateChannelMap.toString()).append("\n");
    buffer.append(affliateChannelCountMap.toString()).append("\n\n");

    buffer.append("Affliate Provider Mappings:").append("\n");
    buffer.append(affliateProviderMap.toString()).append("\n\n");
    buffer.append(affliateProviderCountMap.toString()).append("\n");

    buffer.append("First Affliate Mappings:").append("\n");
    buffer.append(firstAffliateMap.toString()).append("\n\n");
    buffer.append(firstAffliateCountMap.toString()).append("\n");

    buffer.append("Signup App Mappings:").append("\n");
    buffer.append(signupAppMap.toString()).append("\n");
    buffer.append(signupAppCountMap.toString()).append("\n\n");

    buffer.append("First Device Mappings:").append("\n");
    buffer.append(firstDeviceMap.toString()).append("\n");
    buffer.append(firstDeviceCountMap.toString()).append("\n\n");

    buffer.append("First Browser Mappings:").append("\n");
    buffer.append(firstBrowserMap.toString()).append("\n");
    buffer.append(firstBrowserCountMap.toString()).append("\n");

    writer.write(buffer.toString());
    writer.flush();
    writer.close();
  }

  public static void writePredictionsToCSV(List<Double> classLabels, String classifier) throws IOException {
    
    String output =  PREDICTIONS + classifier + ".csv";
    Scanner scanner = new Scanner(new FileReader(new File(App.TEST_INPUT_FILE)));
    CSVWriter predictionsWriter = new CSVWriter(new FileWriter(new File(output)), ',', CSVWriter.NO_QUOTE_CHARACTER);
    List<Prediction> predictionsList = new ArrayList<Prediction>();
    
    scanner.nextLine();
    
    for(Double label:classLabels) {
      Prediction prediction = new Prediction();
      prediction.setId(scanner.nextLine().split(",")[0]);
      prediction.setClassLabel(label);
      predictionsList.add(prediction);
    }

    for(Prediction prediction:predictionsList) {
      predictionsWriter.writeNext(prediction.arr());
    }
    
    System.out.println("Final Predictions output written to "+ output);
    
    predictionsWriter.flush();
    predictionsWriter.close();
    scanner.close();
    
  }
}
