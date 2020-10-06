package com.project.faurExchange.security;

import com.project.faurExchange.model.PayVaue;

public class Pay {

	private byte[] value;

	private PayVaue payVaue;

	public Pay() {
		this.value = new byte[20];
	}

	public Pay(byte[] bytes) {
		this.value = Crypto.hashMessage(bytes);
	}

	public Pay(Pay pay) {
		this.value = Crypto.hashMessage(pay.getValue());
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public PayVaue getPayVaue() {
		return payVaue;
	}

	public void setPayVaue(PayVaue payVaue) {
		this.payVaue = payVaue;
	}

}
