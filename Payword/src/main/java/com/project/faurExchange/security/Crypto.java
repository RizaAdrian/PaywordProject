package com.project.faurExchange.security;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Random;

import org.apache.log4j.Logger;

import com.project.faurExchange.model.Broker;
import com.project.faurExchange.model.Certificate;

public class Crypto {

	private static Logger log;

	private Crypto() {

	}

	public static boolean checkSign(Certificate certificate, Broker broker) {
		Signature signature = null;
		boolean result = false;
		try {
			signature = Signature.getInstance("SHA1WithRSA");
			signature.initVerify(broker.getPublicKey());
			signature.update(certificate.unsigned());
			result = signature.verify(certificate.signed());
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			log.fatal(e.getMessage());
		}
		return result;
	}

	public static void signCertificate(Certificate certificate, Broker broker) {
		byte[] signedHash = null;
		Signature signature = null;
		try {
			signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(broker.getPrivateKey());
			signature.update(certificate.unsigned());
			signedHash = signature.sign();
			certificate.setSigned(signedHash);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			log.fatal(e.getMessage());
		}

	}

	public static KeyPair getRSAKeyPair() {
		KeyPairGenerator keyPairGenerator = null;
		KeyPair keyPair = null;

		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024);

			keyPair = keyPairGenerator.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			log.fatal(e.getMessage());
		}

		return keyPair;
	}

	public static byte[] hashMessage(byte[] message) {
		byte[] hash = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			hash = messageDigest.digest(message);
		} catch (NoSuchAlgorithmException e) {
			log.fatal(e.getMessage());
		}

		return hash;
	}

	public static byte[] getSecret(int noOfBytes) {
		byte[] secret = new byte[noOfBytes];

		Random random = new Random();
		random.nextBytes(secret);

		return secret;
	}
}
