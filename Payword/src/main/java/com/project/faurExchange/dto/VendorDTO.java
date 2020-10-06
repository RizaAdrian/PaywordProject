package com.project.faurExchange.dto;

import java.io.Serializable;

public class VendorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String cardNo;

	private int balance;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void addBalance(int moneyforVendor) {
		this.balance += moneyforVendor;

	}

}
