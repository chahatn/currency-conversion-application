package com.conversionApplication.model;

public class CurrencyExchangeRateResponse {
	private float exchangeRate;
	private String fromCurrencyCode;
	private String fromCurrencyName;
	private String toCurrencyCode;
	private String toCurrencyName;
	
	
	public CurrencyExchangeRateResponse() {
	
	}
	public CurrencyExchangeRateResponse(float exchangeRate, String fromCurrencyCode, String fromCurrencyName,
			String toCurrencyCode, String toCurrencyName) {
		super();
		this.exchangeRate = exchangeRate;
		this.fromCurrencyCode = fromCurrencyCode;
		this.fromCurrencyName = fromCurrencyName;
		this.toCurrencyCode = toCurrencyCode;
		this.toCurrencyName = toCurrencyName;
	}
	public float getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getFromCurrencyCode() {
		return fromCurrencyCode;
	}
	public void setFromCurrencyCode(String fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}
	public String getFromCurrencyName() {
		return fromCurrencyName;
	}
	public void setFromCurrencyName(String fromCurrencyName) {
		this.fromCurrencyName = fromCurrencyName;
	}
	public String getToCurrencyCode() {
		return toCurrencyCode;
	}
	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}
	public String getToCurrencyName() {
		return toCurrencyName;
	}
	public void setToCurrencyName(String toCurrencyName) {
		this.toCurrencyName = toCurrencyName;
	}
	
	

}
