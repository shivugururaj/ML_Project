package com.ml.project.mlproject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Mappings {

  private static Map<String, Integer> labelMap = new HashMap<String, Integer>();
  private static Map<String, Integer> signupMethodMap = new HashMap<String, Integer>();
  private static Map<String, Integer> languageMap = new HashMap<String, Integer>();
  private static Map<String, Integer> affliateChannelMap = new HashMap<String, Integer>();
  private static Map<String, Integer> affliateProviderMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstAffliateMap = new HashMap<String, Integer>();
  private static Map<String, Integer> signupAppMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstDeviceMap = new HashMap<String, Integer>();
  private static Map<String, Integer> firstBrowserMap = new HashMap<String, Integer>();

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

    return diffMonth + 1;
  }

  public static Map<String, Integer> getLabelMap() {
    return labelMap;
  }

  public static Integer getsignupMethod(String signupMethod) {
    if (signupMethodMap.containsKey(signupMethod)) {
      return signupMethodMap.get(signupMethod);
    } else {
      int size = signupMethodMap.size() + 1;
      signupMethodMap.put(signupMethod, size);
      return size;
    }
  }

  public static Integer getLanguage(String language) {
    if (languageMap.containsKey(language)) {
      return languageMap.get(language);
    } else {
      int size = languageMap.size() + 1;
      languageMap.put(language, size);
      return size;
    }
  }

  public static Integer getAffliateChannel(String affliateChannel) {
    if (affliateChannelMap.containsKey(affliateChannel)) {
      return affliateChannelMap.get(affliateChannel);
    } else {
      int size = affliateChannelMap.size() + 1;
      affliateChannelMap.put(affliateChannel, size);
      return size;
    }
  }

  public static Integer getAffliateProvider(String affliateProvider) {
    if (affliateProviderMap.containsKey(affliateProvider)) {
      return affliateProviderMap.get(affliateProvider);
    } else {
      int size = affliateProviderMap.size() + 1;
      affliateProviderMap.put(affliateProvider, size);
      return size;
    }
  }

  public static Integer getFirstAffliate(String firstAffliate) {
    if (firstAffliateMap.containsKey(firstAffliate)) {
      return firstAffliateMap.get(firstAffliate);
    } else {
      int size = firstAffliateMap.size() + 1;
      firstAffliateMap.put(firstAffliate, size);
      return size;
    }
  }
  
  public static Integer getSignupApp(String signupApp) {
    if(signupAppMap.containsKey(signupApp)) {
      return signupAppMap.get(signupApp);
    } else {
      int size = signupAppMap.size() + 1;
      signupAppMap.put(signupApp, size);
      return size;
    }
  }
  
  public static Integer getFirstDevice(String firstDevice) {
    if(firstDeviceMap.containsKey(firstDevice)) {
      return firstDeviceMap.get(firstDevice);
    } else {
      int size = firstDeviceMap.size() + 1;
      firstDeviceMap.put(firstDevice, size);
      return size;
    }
  }
  
  public static Integer getFirstBrowser(String firstBrowser) {
    if(firstBrowserMap.containsKey(firstBrowser)) {
      return firstBrowserMap.get(firstBrowser);
    } else {
      int size = firstBrowserMap.size() + 1;
      firstBrowserMap.put(firstBrowser, size);
      return size;
    }
  }
}
