package com.project.faurExchange.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.exception.ExceptionVendor;
import com.project.faurExchange.model.Broker;
import com.project.faurExchange.model.BytesSection;
import com.project.faurExchange.model.Certificate;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.model.PayVaue;
import com.project.faurExchange.model.User;
import com.project.faurExchange.security.Commit;
import com.project.faurExchange.security.Crypto;
import com.project.faurExchange.tables.PaymentEntity;
import com.project.faurExchange.tables.PaymentsDB;
import com.project.faurExchange.tables.PayEntiy;
import com.project.faurExchange.tables.UserDB;
import com.project.faurExchange.tables.VendorDB;
import com.project.faurExchange.transformer.State;
import com.project.faurExchange.transformer.Transformer;

@Component
public class VendorServiceImpl implements VendorService {

	private static final String ID_PAYMENT = "IdPayment: ";

	@Autowired
	private UserDB userDb;

	@Autowired
	private VendorDB vendorDb;

	@Autowired
	private PaymentsDB paymentsDB;
	@Autowired
	private Broker broker;

	@Autowired
	private Transformer transformer;

	private final  Logger looger=Logger.getLogger(VendorServiceImpl.class);

	@Override
	public boolean checkPaymentSingnatures(User user, Commit commit) {
		byte[] unsignedCommit = commit.unsigned();
		byte[] signedCommit = commit.signed();

		/** 1.Check signature from Client */
		Signature signature = null;
		boolean result = false;
		try {
			signature = Signature.getInstance("SHA1WithRSA");
			signature.initVerify(user.getPublicKey());
			signature.update(unsignedCommit);
			result = signature.verify(signedCommit);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			looger.fatal("Singnature error: " + e.getMessage());
		}

		if (result) {
			/** 2.Check signature from Broker */
			Certificate userCertificate = user.getCertificate();
			byte[] unsignedCertificate = userCertificate.unsigned();
			byte[] signedCertificate = userCertificate.signed();
			try {
				signature = Signature.getInstance("SHA1WithRSA");
				signature.initVerify(broker.getPublicKey());
				signature.update(unsignedCertificate);
				result = signature.verify(signedCertificate);
			} catch (Exception e) {
				looger.fatal(e.getMessage());
			}

		}

		return result;
	}

	@Override
	public VendorDTO retrieveVendorDTOByUsername(String vendorId) throws ExceptionVendor {

		return transformer.vendorFromModeltoDTO(vendorDb.findVendor(vendorId));
	}

	@Override
	public List<PaymentModel> retrievePayments(String vendorID) {

		List<PaymentEntity> listPaymentEntity = paymentsDB.retriveAllPosibleReedem(vendorID);

		List<PaymentModel> listPaymentModel = new ArrayList<>();
		for (PaymentEntity entity : listPaymentEntity) {
			listPaymentModel.add(transformer.paymentfromEntitytoModel(entity));
		}
		return listPaymentModel;
	}

	@Override
	public void addPaymentTOVendor(Commit commit, CommitRequest commitRequest) {

		PaymentEntity entity = new PaymentEntity();
		entity.setIdUser(commitRequest.getUserId());
		entity.setIdVendor(commitRequest.getVendorId());
		entity.setCommit(commit);
		entity.setSate(State.WAITING);
		entity.setDate(new Date());

		paymentsDB.addPayment(entity);

	}

	@Override
	public int reedemPaymentsList(VendorDTO vendorDTO) throws ExceptionUser {
		int moneyforVendor = 0;
		String vendorID = vendorDTO.getUsername();
		/** All payments registerd for a specific Vendor */
		List<PaymentEntity> listReedem = paymentsDB.retriveAllPosibleReedem(vendorID);

		looger.info(" Are not payments to reeedm.");
		for (PaymentEntity entity : listReedem) {

			moneyforVendor += reedemOnePayment(entity);
		}

		vendorDTO.addBalance(moneyforVendor);
		return moneyforVendor;
	}

	private int reedemOnePayment(PaymentEntity entity) throws ExceptionUser {
		if (!entity.getSate().equals(State.WAITING)) {
			looger.info(ID_PAYMENT + entity.getIdPayment() + " has been allready reedem");
			return 0;
		}
		String userID = entity.getIdUser();

		User user = userDb.findUser(userID);
		/**1.Step signature*/
		boolean status = checkPaymentSingnatures(user, entity.getCommit());

		int sizePaywordOne=entity.getCommit().getSection(BytesSection.LENGTH_ONE)[3];
		List<PayEntiy> paywordsOne=entity.getPaywordEntityList().subList(0, sizePaywordOne);
		boolean paywordOne=checkHashCain(sizePaywordOne, paywordsOne);

		int sizePaywordFive=entity.getCommit().getSection(BytesSection.LENGTH_FIVE)[3];
		List<PayEntiy> paywordsFive=entity.getPaywordEntityList().subList(0, sizePaywordFive);
		boolean paywordFive=checkHashCain(sizePaywordFive, paywordsFive);

		int sizePaywordTen=entity.getCommit().getSection(BytesSection.LENGTH_TEN)[3];
		List<PayEntiy> paywordsTen=entity.getPaywordEntityList().subList(0, sizePaywordTen);
		boolean paywordTen=checkHashCain(sizePaywordTen, paywordsTen);
		
		if ((status) && paywordOne && paywordFive && paywordTen) {
			paymentsDB.updatePament(entity, State.FINISH);
			looger.info(ID_PAYMENT + entity.getIdPayment() + " has be accepted");
			looger.info("IdPayment have: " + entity.getPaywordEntityList().size() + " pays!");
			
			return sizePaywordOne* PayVaue.ONE.getValue()+sizePaywordFive* PayVaue.FIVE.getValue()+sizePaywordTen* PayVaue.TEN.getValue();
		} else {
			paymentsDB.updatePament(entity, State.CANCELED);
			looger.info(ID_PAYMENT + entity.getIdPayment() + " has be cancelade");
			return 0;
		}

	}

	/** Recursive method */
	boolean checkHashCain(int size, List<PayEntiy> listPayword) {
		if (size == 1) {
			looger.info("HashChain is ok!");
			return true;
		} else {
			byte[] hcn = listPayword.get(size - 1).getcValue();// h(cn)
			byte[] next = Crypto.hashMessage(listPayword.get(size - 2).getcValue());// cn-1
			if (Arrays.equals(hcn, next)) {
				checkHashCain(size - 1, listPayword);
			} else {
				looger.info("HashChain is corrupted ok!");
				return false;
			}
			return true;
		}
	}

	@Override
	public int reedemPayment(String vendorId, int idPayment) throws ExceptionVendor,ExceptionUser {
		PaymentEntity paymentEntity = paymentsDB.retriveOneReedem(idPayment);
		return reedemOnePayment(paymentEntity);

	}

}
