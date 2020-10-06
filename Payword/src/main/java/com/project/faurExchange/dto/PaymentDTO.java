package com.project.faurExchange.dto;

import java.io.Serializable;
import java.util.Date;

import com.project.faurExchange.transformer.State;

public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idPayment;

	private String idUser;

	private String idVendor;

	private Date date;

	private State sate;

	private int value;

	public int getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdVendor() {
		return idVendor;
	}

	public void setIdVendor(String idVendor) {
		this.idVendor = idVendor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public State getSate() {
		return sate;
	}

	public void setSate(State sate) {
		this.sate = sate;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
