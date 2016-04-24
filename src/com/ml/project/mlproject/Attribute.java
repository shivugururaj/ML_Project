package com.ml.project.mlproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attribute {
  private Object userSince, firstBookingSeason = "", gender, age, signupMethod, signupFlow, language, affliateChannel,
      affliateProvider, firstAffliate, signupApp, firstDevice, firstBrowser, destinationCountry;
  private String firstBooking;
  private SimpleDateFormat dateFormat;

  public Attribute() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  }

  public Object getUserSince() {
    return userSince;
  }

  public void setUserSince(String userSince) throws ParseException {
    if (userSince.isEmpty() || userSince == null || firstBooking == null) {
      this.userSince = 1;
    } else {
      Date dateAccountCreated = dateFormat.parse(userSince);
      Date dateFirstBooking = dateFormat.parse(firstBooking);
      this.userSince = Mappings.getMonthsDifference(dateAccountCreated, dateFirstBooking);

    }

  }

  public Object getFirstBookingSeason() {
    return firstBookingSeason;
  }

  public void setDateFirstBooking(String firstBooking) {
    if (!firstBooking.isEmpty() && firstBooking != null) {
      this.firstBooking = firstBooking;
      this.firstBookingSeason = Mappings.getSeason(firstBooking);
    }
  }

  public Object getGender() {
    return gender;
  }

  public void setGender(String gender) {

    gender = gender.trim();

    if (gender == null || gender.isEmpty() || gender.equalsIgnoreCase("-unknown-")) {
      this.gender = 0;
      return;
    }

    this.gender = (gender.equalsIgnoreCase("MALE") ? 1 : 2);
  }

  public Object getAge() {
    return age;
  }

  public void setAge(String age) {
    if (age == null || age.isEmpty()) {
      this.age = 0;
    } else {
      this.age = Mappings.getAgeVector((int) Double.parseDouble(age.trim()));
    }
  }

  public Object getSignupMethod() {
    return signupMethod;
  }

  public void setSignupMethod(String signupMethod) {
    this.signupMethod = signupMethod;
  }

  public Object getSignupFlow() {
    return signupFlow;
  }

  public void setSignupFlow(String signupFlow) {
    if (signupFlow.equals("0")) {
      this.signupFlow = 1;
    }
    this.signupFlow = signupFlow;
  }

  public Object getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Object getAffliateChannel() {
    return affliateChannel;
  }

  public void setAffliateChannel(String affliateChannel) {
    this.affliateChannel = affliateChannel;
  }

  public Object getAffliateProvider() {
    return affliateProvider;
  }

  public void setAffliateProvider(String affliateProvider) {
    this.affliateProvider = affliateProvider;
  }

  public Object getFirstAffliate() {
    return firstAffliate;
  }

  public void setFirstAffliate(String firstAffliate) {
    this.firstAffliate = firstAffliate;
  }

  public Object getSignupApp() {
    return signupApp;
  }

  public void setSignupApp(String signupApp) {
    this.signupApp = signupApp;
  }

  public Object getFirstDevice() {
    return firstDevice;
  }

  public void setFirstDevice(String firstDevice) {
    this.firstDevice = firstDevice;
  }

  public Object getFirstBrowser() {
    return firstBrowser;
  }

  public void setFirstBrowser(String firstBrowser) {
    this.firstBrowser = firstBrowser;
  }

  public Object getDestinationCountry() {
    return destinationCountry;
  }

  public void setDestinationCountry(String destinationCountry) {
    if (destinationCountry != null) {
      this.destinationCountry = (destinationCountry.equalsIgnoreCase("US") ? 1 : 0);
    }
  }

  public Attribute process(Attribute attribute) {

    Attribute copyAttr = attribute.copy();

    copyAttr.signupMethod = Mappings.getsignupMethod(copyAttr.signupMethod.toString());
    copyAttr.language = Mappings.getLanguage(copyAttr.language.toString());
    copyAttr.affliateChannel = Mappings.getAffliateChannel(copyAttr.affliateChannel.toString());
    copyAttr.affliateProvider = Mappings.getAffliateProvider(copyAttr.affliateProvider.toString());
    copyAttr.firstAffliate = Mappings.getFirstAffliate(copyAttr.firstAffliate.toString());
    copyAttr.signupApp = Mappings.getSignupApp(copyAttr.signupApp.toString());
    copyAttr.firstDevice = Mappings.getFirstDevice(copyAttr.firstDevice.toString());
    copyAttr.firstBrowser = Mappings.getFirstBrowser(copyAttr.firstBrowser.toString());

    return copyAttr;
  }

  public String[] arr() {
    String[] result;
    if (this.destinationCountry == null) {
      result = new String[13];
    } else {
      result = new String[14];
    }

    result[0] = this.userSince.toString();

    if (this.firstBookingSeason.equals("")) {
      result[1] = "1";
    } else {
      result[1] = this.firstBookingSeason.toString();
    }

    result[2] = this.gender.toString();
    result[3] = this.age.toString();
    result[4] = this.signupMethod.toString();
    result[5] = this.signupFlow.toString();
    result[6] = this.language.toString();
    result[7] = this.affliateChannel.toString();
    result[8] = this.affliateProvider.toString();
    result[9] = this.firstAffliate.toString();
    result[10] = this.signupApp.toString();
    result[11] = this.firstDevice.toString();
    result[12] = this.firstBrowser.toString();
    if (this.destinationCountry != null) {
      result[13] = this.destinationCountry.toString();
    }

    return result;
  }

  private Attribute copy() {
    Attribute newThis = new Attribute();

    newThis.userSince = this.userSince;
    newThis.firstBookingSeason = this.firstBookingSeason;
    newThis.gender = this.gender;
    newThis.age = this.age;
    newThis.signupMethod = this.signupMethod;
    newThis.signupFlow = this.signupFlow;
    newThis.language = this.language;
    newThis.affliateChannel = this.affliateChannel;
    newThis.affliateProvider = this.affliateProvider;
    newThis.firstAffliate = this.firstAffliate;
    newThis.signupApp = this.signupApp;
    newThis.firstDevice = this.firstDevice;
    newThis.firstBrowser = this.firstBrowser;
    newThis.destinationCountry = this.destinationCountry;

    return newThis;
  }
}