package com.conversionApplication.model;

public class ExchangeNode {
	private String currencyCode;
	private float exchangeRate;
	private String currencyName;
	private double amount;
	public ExchangeNode(String currencyCode, float exchangeRate, String currencyName, double amount) {
		super();
		this.currencyCode = currencyCode;
		this.exchangeRate = exchangeRate;
		this.currencyName = currencyName;
		this.amount = amount;
	}
	public ExchangeNode() {
	}
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public float getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	
	
	
}
