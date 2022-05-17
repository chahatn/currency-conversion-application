package com.conversionApplication.model;


public class BestExchangeRatePathData extends CurrencyNode {

	
	private String currencyCode; 
	
	public BestExchangeRatePathData(String country, double amount, String path, String currencyCode) {
		super(country, amount, path);
		this.currencyCode = currencyCode;
	}	
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	

	
	
	

}
