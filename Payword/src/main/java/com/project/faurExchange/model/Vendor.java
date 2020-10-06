package com.project.faurExchange.model;

public class Vendor {

	private String username;

	private String cardNo;

	private int balance;

	public Vendor() {
	}

	public Vendor(String username) {
		this.username = username;
	}

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

	public void addBalance(int momeny) {
		this.balance += momeny;
	}
}
