package com.project.faurExchange.model;

public enum Message {

	USER_ALREADY_EXISTS("User already exists."), 
	SUCCESSFUL_REGISTRATION("Successful registration!");

	private final String messageCode;

	private Message(String message) {
		this.messageCode = message;
	}

	public String getMessage() {
		return messageCode;
	}

}

