package com.project.faurExchange.service;

import com.project.faurExchange.security.Pay;
import java.nio.ByteBuffer;
import java.security.Signature;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.model.BytesSection;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.model.User;
import com.project.faurExchange.repsository.TransactionRepository;
import com.project.faurExchange.security.Commit;
import com.project.faurExchange.tables.PaymentEntity;
import com.project.faurExchange.tables.PaymentsDB;
import com.project.faurExchange.tables.PayEntiy;
import com.project.faurExchange.tables.UserDB;
import com.project.faurExchange.tables.UserVendorKey;
import com.project.faurExchange.transformer.State;
import com.project.faurExchange.transformer.Transformer;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDB userDb;

	@Autowired
	private PaymentsDB paymentsDB;

	@Autowired
	private TransactionRepository transcationRepository;

	@Autowired
	private Transformer transformer;

	private final Logger looger = Logger.getLogger(UserServiceImpl.class);

	@Override
	public void commitUserToBroker(CommitRequest commitRequest) throws ExceptionUser {

		UserVendorKey userVendorKey = transformer.fromCommitRequestToKey(commitRequest);
		/** Generate all pays */


		Map<BytesSection, List<Pay>> pay = applyGreedyAlghorithm(userVendorKey.getValue());

		/** 1.Create commit with payment */
		Commit commit = addPayToCommit(pay);
		
		completeCommit(commit,userVendorKey);
		addPaymentTOVendor(commit, commitRequest, pay);
	}

	private Commit addPayToCommit(Map<BytesSection, List<Pay>> pay) {
		Commit commit=new Commit();
		int pozition = 1;

		for (BytesSection key : pay.keySet()) {

			int paywordSize = pay.get(key).size();
			Pay cn = pay.get(key).get(paywordSize - 1);
			byte[] payword = cn.getValue();
			commit.addSection(key, payword);

			byte[] lengthOfChainBytes = ByteBuffer.allocate(4).putInt(paywordSize).array();
			commit.addSection(BytesSection.CHAIN_LENGTH.getChainLength(pozition), lengthOfChainBytes);
			pozition++;
		}

		return commit;
	}

	private Map<BytesSection, List<Pay>> applyGreedyAlghorithm(int value) {
		Map<BytesSection, List<Pay>> result = new EnumMap<>(BytesSection.class);

		int coin[] = { 10, 5, 1 };

		for (int i = 0; i < coin.length; i++) {

			int num = 0;

			List<Pay> listSpecificList;
			if (coin[i] <= value) {

				num = value / coin[i];
				System.out.println(num + ": " + coin[i] + "$");

				listSpecificList = transcationRepository.generateHashChain(num);
				result.put(BytesSection.PAY.getPayType(coin[i]), listSpecificList);
				value = num * coin[i];
			} else {
				listSpecificList = new ArrayList<>();
				result.put(BytesSection.PAY.getPayType(num), listSpecificList);
			}

		}
		return result;
	}

	private void completeCommit(Commit commit,UserVendorKey userVendorKey) throws ExceptionUser {

		User user = userDb.findUser(userVendorKey.getUserIdentity());
		commit.addSection(BytesSection.VENDOR_IDENTITY, userVendorKey.getVendorIdentity().getBytes());
		commit.addSection(BytesSection.CERTIFICATE, user.getCertificate().unsigned());

		LocalDateTime currentDateLocalDateTime = LocalDateTime.now();
		long currentDateLong = currentDateLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		byte[] currentDateBytes = ByteBuffer.allocate(8).putLong(currentDateLong).array();
		commit.addSection(BytesSection.CURRENT_DATE, currentDateBytes);

		byte[] signedHash = null;

		Signature sig = null;
		try {
			sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(user.getPrivateKey());
			sig.update(commit.unsigned());
			signedHash = sig.sign();
		} catch (Exception e) {
			looger.fatal("User commit abort!!!");
		}

		commit.setSigned(signedHash);
	}

	@Override
	public UserDTO retrieveUserDTOByUsername(String userId) throws ExceptionUser {

		return transformer.userFromModeltoDTO(userDb.findUser(userId));
	}

	@Override
	public void addPaymentTOVendor(Commit commit, CommitRequest commitRequest,
			Map<BytesSection, List<Pay>> allPayment) {

		PaymentEntity entity = new PaymentEntity();
		entity.setIdPayment(paymentsDB.getSizePaymentsDB());
		entity.setIdUser(commitRequest.getUserId());
		entity.setIdVendor(commitRequest.getVendorId());
		entity.setCommit(commit);
		entity.setSate(State.WAITING);
		entity.setDate(new Date());

		for (BytesSection key : allPayment.keySet()) {
			List<Pay> microChain = allPayment.get(key);

			for (Pay pay : microChain) {
				PayEntiy payEntiy = new PayEntiy();
				payEntiy.setIdPaymen(entity.getIdPayment());
				payEntiy.setHashChainStep(microChain.lastIndexOf(pay));
				payEntiy.setcValue(pay.getValue());
				entity.getPaywordEntityList().add(payEntiy);
			}
			looger.info("Succesful save pays from: " + commitRequest.getUserId() + " to "
					+ commitRequest.getVendorId());
			looger.info("Save :" + microChain.size() + " pays of value: " + key);
		}
		paymentsDB.addPayment(entity);

	}

}
