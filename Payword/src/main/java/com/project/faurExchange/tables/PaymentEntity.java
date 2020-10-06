package com.project.faurExchange.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.faurExchange.security.Commit;
import com.project.faurExchange.transformer.State;

public class PaymentEntity {

	private List<PayEntiy> paywordEntityList;

	private int idPayment;

	private String idUser;

	private String idVendor;

	private Date date;

	private State sate;

	private Commit commit;

	public PaymentEntity() {
		this.paywordEntityList = new ArrayList<>();
	}

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

	public Commit getCommit() {
		return commit;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	public State getSate() {
		return sate;
	}

	public void setSate(State sate) {
		this.sate = sate;
	}

	public List<PayEntiy> getPaywordEntityList() {
		return paywordEntityList;
	}

	public void setPaywordEntityList(List<PayEntiy> paywordEntityList) {
		this.paywordEntityList = paywordEntityList;
	}

}
