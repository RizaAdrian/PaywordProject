package com.project.faurExchange.model;

public enum BytesSection {

	USER_IDENTITY,
	BROKER_IDENTITY, 
	VENDOR_IDENTITY,
	USER_PUBLIC_KEY, 
	BROKER_PUBLIC_KEY, 
	ACCOUNT_NUMBER, 
	CREDIT_LIMIT, 
	EXPIRATION_DATE, 
	CURRENT_DATE, 
	SIGNED_MESSAGE, 
	CERTIFICATE,
	PAY,
	PAY_ONE,
	PAY_FIVE,
	PAY_TEN,
	CHAIN_LENGTH,
	LENGTH_ONE,
	LENGTH_FIVE,
	LENGTH_TEN;

	public BytesSection getPayType(int n) {

		if (n == 1)
			return PAY_ONE;
		if (n == 5)
			return PAY_FIVE;
		return PAY_TEN;
	}
	
	public BytesSection getChainLength(int n) {
		if (n == 1)
			return LENGTH_ONE;
		if (n == 2)
			return LENGTH_FIVE;
		return LENGTH_TEN;

	}
}
