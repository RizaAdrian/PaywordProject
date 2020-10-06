package com.project.faurExchange.dto;

import com.project.faurExchange.model.Certificate;

public class JsonResponse {

	private String message;

	private Certificate certificate;

	private String securityStatus;

	public JsonResponse() {
		
	}

	public JsonResponse(String message, Certificate certificate) {
		this.message = message;
		this.certificate = certificate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public String getSecurityStatus() {
		return securityStatus;
	}

	public void setSecurityStatus(String securityStatus) {
		this.securityStatus = securityStatus;
	}

}
