package com.project.faurExchange.service;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.project.faurExchange.dto.JsonResponse;
import com.project.faurExchange.dto.PaymentDTO;
import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.exception.ExceptionVendor;
import com.project.faurExchange.model.Broker;
import com.project.faurExchange.model.BytesSection;
import com.project.faurExchange.model.Certificate;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.model.Message;
import com.project.faurExchange.model.User;
import com.project.faurExchange.security.Crypto;
import com.project.faurExchange.tables.UserDB;
import com.project.faurExchange.tables.VendorDB;
import com.project.faurExchange.transformer.Transformer;

@Component
@Scope("singleton")
public class BrokerServiceImpl implements BrokerService {

	@Autowired
	private UserDB usersDB;

	@Autowired
	private VendorDB vendorDB;
	@Autowired
	private UserService userService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private Broker broker;

	@Autowired
	private Transformer transformer;
	
	private final  Logger looger=Logger.getLogger(BrokerServiceImpl.class);

	@Override
	public JsonResponse userRegistration(UserDTO userDto, String ipAddr) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		if (usersDB.checkIfExists(user)) {
			return new JsonResponse(Message.USER_ALREADY_EXISTS.name(), null);
		}
		user.setCardNo(userDto.getCardNo());
		user.setCreditLimit(userDto.getCreditLimit());
		KeyPair rsaKeyPair = Crypto.getRSAKeyPair();
		user.setPublicKey(rsaKeyPair.getPublic());
		user.setPrivateKey(rsaKeyPair.getPrivate());
		user.setCertificate(generateUserCertificate(user, broker));
		looger.info(userDto.getUsername() + " certificate length: " + user.getCertificate().unsigned().length
				+ "\n" + user.getCertificate());
		usersDB.addUser(user);
		return new JsonResponse(Message.SUCCESSFUL_REGISTRATION.name(), user.getCertificate());
	}

	private Certificate generateUserCertificate(User user, Broker broker) {
		Certificate certificate = new Certificate();
		certificate.addSection(BytesSection.USER_IDENTITY, user.getUsername().getBytes());
		certificate.addSection(BytesSection.BROKER_IDENTITY, broker.getUsername().getBytes());
		certificate.addSection(BytesSection.USER_PUBLIC_KEY, user.getPublicKey().getEncoded());
		certificate.addSection(BytesSection.BROKER_PUBLIC_KEY, broker.getPublicKey().getEncoded());
		certificate.addSection(BytesSection.ACCOUNT_NUMBER, user.getCardNo().getBytes());
		byte[] creditLimit = ByteBuffer.allocate(4).putInt(user.getCreditLimit()).array();
		certificate.addSection(BytesSection.CREDIT_LIMIT, creditLimit);
		certificate.addSection(BytesSection.EXPIRATION_DATE, expireDate());
		Crypto.signCertificate(certificate, broker);
		return certificate;
	}

	private byte[] expireDate() {
		LocalDateTime currentDateLocalDateTime = LocalDateTime.now();
		LocalDateTime expireDateLocalDate = currentDateLocalDateTime.plusDays(1);
		long expireDateLong = expireDateLocalDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return ByteBuffer.allocate(8).putLong(expireDateLong).array();
	}

	@Override
	public JsonResponse userCommitRequest(CommitRequest commitRequest) {
		JsonResponse response = new JsonResponse();

		try {
			response.setMessage("OK");
			// 1.Check if user exist
			UserDTO userDto = userService.retrieveUserDTOByUsername(commitRequest.getUserId());

			//2.Check if are sufficient money
			if (commitRequest.getValue() > userDto.getCreditLimit()) {
				throw new ExceptionUser("Are not sufficient money for this tranzaction");
			}
			userService.commitUserToBroker(commitRequest);

			takeMoneyFromUserAccount(commitRequest.getValue(), commitRequest.getUserId());
			response.setSecurityStatus("true");
			
		} catch (Exception e) {
			response.setMessage("KO");
			looger.info(e.getMessage());
		}

		return response;
	}

	@Override
	public List<PaymentDTO> retrievePaymentsListByVendor(String vendorId) {

		List<PaymentModel> listModel = vendorService.retrievePayments(vendorId);
		List<PaymentDTO> listDTO = new ArrayList<>();
		for (PaymentModel model : listModel) {
			listDTO.add(transformer.paymentFromModelToDTO(model));
		}
		if(listDTO.isEmpty()){
			looger.info("Are not any payments to reedem.");
		}
		return listDTO;
	}

	@Override
	public JsonResponse reedemPaymentsList(VendorDTO vendorDTO) throws ExceptionUser {
		JsonResponse response = new JsonResponse();
		int moneyForVendor = vendorService.reedemPaymentsList(vendorDTO);
		addMoneyToVendorAccount(moneyForVendor, vendorDTO.getUsername());
		response.setSecurityStatus("true");
		response.setMessage("Ok");
		return response;
	}

	private void addMoneyToVendorAccount(int money, String vendorID) {
		vendorDB.addMoneyToVendorAccount(money, vendorID);
	}


	private void takeMoneyFromUserAccount(int money,String userID){
		usersDB.takeMoneyFromUserAccount(money, userID);
	}
	@Override
	public int reedemOnePayment(String vendorId, int idPayment) throws ExceptionVendor,ExceptionUser {
		int moneyValue = vendorService.reedemPayment(vendorId, idPayment);

		addMoneyToVendorAccount(moneyValue, vendorId);

		return moneyValue;
	}
}