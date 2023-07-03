package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
//@Table(name = "currency")
public class CurrencyConversion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	@Column(name = "currency_code")
	@OneToOne
	private Currency fromCurrencyCode;
	@OneToOne
	private Currency toCurrencyCode;

	private double totalExchangeFee;
	public CurrencyConversion(Long id, Currency fromCurrencyCode, Currency toCurrencyCode, double totalExchangeFee,
		double amount, double convertedAmount, double exchangeFee) {
	super();
	this.id = id;
	this.fromCurrencyCode = fromCurrencyCode;
	this.toCurrencyCode = toCurrencyCode;
	this.totalExchangeFee = totalExchangeFee;
	this.amount = amount;
	this.convertedAmount = convertedAmount;
	this.exchangeFee = exchangeFee;
}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getTotalExchangeFee() {
		return totalExchangeFee;
	}

	public void setTotalExchangeFee(double totalExchangeFee) {
		this.totalExchangeFee = totalExchangeFee;
	}

	public double getExchangeFee() {
		return exchangeFee;
	}

	public void setExchangeFee(double exchangeFee) {
		this.exchangeFee = exchangeFee;
	}

	private double amount;
	private double convertedAmount;
	private double exchangeFee;

	public Currency getFromCurrencyCode() {
		return fromCurrencyCode;
	}

	public void setFromCurrencyCode(Currency fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}

	public Currency getToCurrencyCode() {
		return toCurrencyCode;
	}

	public void setToCurrencyCode(Currency toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}

	public CurrencyConversion(Currency fromCurrencyCode, Currency toCurrencyCode, double amount, double convertedAmount,
			double exchangeFee) {
		super();
		this.fromCurrencyCode = fromCurrencyCode;
		this.toCurrencyCode = toCurrencyCode;
		this.amount = amount;
		this.convertedAmount = convertedAmount;
		this.exchangeFee = exchangeFee;
	}
	public CurrencyConversion() {
		
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getConvertedAmount() {
		return convertedAmount;
	}

	public void setConvertedAmount(double convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	public double getexchangeFee() {
		return exchangeFee;
	}

	public void setexchangeFee(double exchangeFee) {
		this.exchangeFee = exchangeFee;
	}
}
