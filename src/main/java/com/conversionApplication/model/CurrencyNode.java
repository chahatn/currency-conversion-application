package com.conversionApplication.model;

public class CurrencyNode {
	private String country;
	private double amount;
	private String path;
	
	public CurrencyNode(String country, double amount, String path) {
		super();
		this.country = country;
		this.amount = amount;
		this.path = path;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
