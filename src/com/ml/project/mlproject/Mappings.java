package com.ml.project.mlproject;

import java.util.HashMap;
import java.util.Map;

public class Mappings {

  private static Map<String, Integer> labelMap = new HashMap<String, Integer>();

  public static void initialize() {
    labelMap.put("US", 0);
    labelMap.put("FR", 1);
    labelMap.put("CA", 2);
    labelMap.put("GB", 3);
    labelMap.put("ES", 4);
    labelMap.put("IT", 5);
    labelMap.put("PT", 6);
    labelMap.put("NL", 7);
    labelMap.put("DE", 8);
    labelMap.put("AU", 9);
    labelMap.put("other", 10);
  }

  public static Integer getSeason(String date) {
    String[] firstBookedDate = date.split("-");
    int bookingMonth = Integer.parseInt(firstBookedDate[1]);

    if (bookingMonth >= 1 && bookingMonth <= 4) {
      return 0;
    } else if (bookingMonth >= 5 && bookingMonth <= 7) {
      return 1;
    } else if (bookingMonth >= 8 && bookingMonth <= 10) {
      return 2;
    } else {
      return 3;
    }
  }

}
