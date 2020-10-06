package com.project.faurExchange.model;

public enum PayVaue {

	ONE(1), 
	FIVE(5), 
	TEN(10);

	int value;

	private PayVaue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

