package com.project.faurExchange.service;

import java.util.Date;

import com.project.faurExchange.security.Commit;
import com.project.faurExchange.transformer.State;

public class PaymentModel {
	private int idPayment;

	private String idUser;

	private String idVendor;

	private Date date;

	private State sate;

	private Commit commit;

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

	public Commit getCommit() {
		return commit;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("idPayment:"+this.idPayment);
		return sb.toString();
	}
}
