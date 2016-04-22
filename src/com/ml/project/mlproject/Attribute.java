package com.ml.project.mlproject;

public class Attribute {
	private Integer userSince, dateFirstBooking, gender, age, signupMethod, signupFlow, language, affliateChannel,
			affliateProvider, firstAffliate, signupApp, firstDevice, firstBrowser, destinationCountry;
	
	public Attribute() {
	  
	}

	public Integer getUserSince() {
		return userSince;
	}

	public void setUserSince(String userSince) {
		this.userSince = Integer.parseInt(userSince);
	}

	public Integer getDateFirstBooking() {
		return dateFirstBooking;
	}

	public void setDateFirstBooking(String dateFirstBooking) {
		this.dateFirstBooking = Integer.parseInt(dateFirstBooking);
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = Integer.parseInt(gender);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = Integer.parseInt(age);
	}

	public Integer getSignupMethod() {
		return signupMethod;
	}

	public void setSignupMethod(String signupMethod) {
		this.signupMethod = Integer.parseInt(signupMethod);
	}

	public Integer getSignupFlow() {
		return signupFlow;
	}

	public void setSignupFlow(String signupFlow) {
		this.signupFlow = Integer.parseInt(signupFlow);
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = Integer.parseInt(language);
	}

	public Integer getAffliateChannel() {
		return affliateChannel;
	}

	public void setAffliateChannel(String affliateChannel) {
		this.affliateChannel = Integer.parseInt(affliateChannel);
	}

	public Integer getAffliateProvider() {
		return affliateProvider;
	}

	public void setAffliateProvider(String affliateProvider) {
		this.affliateProvider = Integer.parseInt(affliateProvider);
	}

	public Integer getFirstAffliate() {
		return firstAffliate;
	}

	public void setFirstAffliate(String firstAffliate) {
		this.firstAffliate = Integer.parseInt(firstAffliate);
	}

	public Integer getSignupApp() {
		return signupApp;
	}

	public void setSignupApp(String signupApp) {
		this.signupApp = Integer.parseInt(signupApp);
	}

	public Integer getFirstDevice() {
		return firstDevice;
	}

	public void setFirstDevice(String firstDevice) {
		this.firstDevice = Integer.parseInt(firstDevice);
	}

	public Integer getFirstBrowser() {
		return firstBrowser;
	}

	public void setFirstBrowser(String firstBrowser) {
		this.firstBrowser = Integer.parseInt(firstBrowser);
	}

	public Integer getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = Integer.parseInt(destinationCountry);
	}
}
