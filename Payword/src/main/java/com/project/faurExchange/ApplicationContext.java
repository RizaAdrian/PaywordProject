package com.project.faurExchange;

import java.security.KeyPair;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.faurExchange.model.Broker;
import com.project.faurExchange.security.Crypto;

@Configuration
public class ApplicationContext {

	private static final String BROKER = "SC_2019";

	@Bean
	public Broker buildBroker() {
		Broker broker = new Broker();
		KeyPair rsaKeyPair = Crypto.getRSAKeyPair();
		broker.setUsername(BROKER);
		broker.setPublicKey(rsaKeyPair.getPublic());
		broker.setPrivateKey(rsaKeyPair.getPrivate());
		return broker;
	}

}
