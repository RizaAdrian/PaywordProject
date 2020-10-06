package com.project.faurExchange.tables;

public class PayEntiy {

	private int idPaymen;

	private int hashChainStep;

	private byte[] cValue;

	public int getIdPaymen() {
		return idPaymen;
	}

	public void setIdPaymen(int idPaymen) {
		this.idPaymen = idPaymen;
	}

	public int getHashChainStep() {
		return hashChainStep;
	}

	public void setHashChainStep(int hashChainStep) {
		this.hashChainStep = hashChainStep;
	}

	public byte[] getcValue() {
		return cValue;
	}

	public void setcValue(byte[] cValue) {
		this.cValue = cValue;
	}

}
