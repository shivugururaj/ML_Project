package com.ml.project.mlproject;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
  private Integer userSince, dateFirstBooking, gender, age, signupMethod, signupFlow, language, affliateChannel,
      affliateProvider, firstAffliate, signupApp, firstDevice, firstBrowser, destinationCountry;

  Map<String, String> labelMap;

  public Attribute() {
    labelMap = new HashMap<String, String>();

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
  }

  public Integer getUserSince() {
    return userSince;
  }

  public void setUserSince(Integer userSince) {
    this.userSince = userSince;
  }

  public Integer getDateFirstBooking() {
    return dateFirstBooking;
  }

  public void setDateFirstBooking(Integer dateFirstBooking) {
    this.dateFirstBooking = dateFirstBooking;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getSignupMethod() {
    return signupMethod;
  }

  public void setSignupMethod(Integer signupMethod) {
    this.signupMethod = signupMethod;
  }

  public Integer getSignupFlow() {
    return signupFlow;
  }

  public void setSignupFlow(Integer signupFlow) {
    this.signupFlow = signupFlow;
  }

  public Integer getLanguage() {
    return language;
  }

  public void setLanguage(Integer language) {
    this.language = language;
  }

  public Integer getAffliateChannel() {
    return affliateChannel;
  }

  public void setAffliateChannel(Integer affliateChannel) {
    this.affliateChannel = affliateChannel;
  }

  public Integer getAffliateProvider() {
    return affliateProvider;
  }

  public void setAffliateProvider(Integer affliateProvider) {
    this.affliateProvider = affliateProvider;
  }

  public Integer getFirstAffliate() {
    return firstAffliate;
  }

  public void setFirstAffliate(Integer firstAffliate) {
    this.firstAffliate = firstAffliate;
  }

  public Integer getSignupApp() {
    return signupApp;
  }

  public void setSignupApp(Integer signupApp) {
    this.signupApp = signupApp;
  }

  public Integer getFirstDevice() {
    return firstDevice;
  }

  public void setFirstDevice(Integer firstDevice) {
    this.firstDevice = firstDevice;
  }

  public Integer getFirstBrowser() {
    return firstBrowser;
  }

  public void setFirstBrowser(Integer firstBrowser) {
    this.firstBrowser = firstBrowser;
  }

  public Integer getDestinationCountry() {
    return destinationCountry;
  }

  public void setDestinationCountry(Integer destinationCountry) {
    this.destinationCountry = destinationCountry;
  }
}
