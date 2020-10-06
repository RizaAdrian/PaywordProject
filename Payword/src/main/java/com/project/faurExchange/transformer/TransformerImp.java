package com.project.faurExchange.transformer;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.project.faurExchange.dto.PaymentDTO;
import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.model.BytesSection;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.model.User;
import com.project.faurExchange.model.Vendor;
import com.project.faurExchange.service.PaymentModel;
import com.project.faurExchange.tables.PaymentEntity;
import com.project.faurExchange.tables.UserVendorKey;

@Component
public class TransformerImp implements Transformer {

	@Override
	public UserDTO userFromModeltoDTO(User model) {
		UserDTO dto = new UserDTO();
		dto.setUsername(model.getUsername());
		dto.setCardNo(model.getCardNo());
		dto.setCreditLimit(model.getCreditLimit());
		return dto;
	}

	@Override
	public VendorDTO vendorFromModeltoDTO(Vendor model) {
		VendorDTO dto = new VendorDTO();
		dto.setUsername(model.getUsername());
		dto.setCardNo(model.getCardNo());
		return dto;
	}

	@Override
	public UserVendorKey fromCommitRequestToKey(CommitRequest commitRequest) {
		UserVendorKey key = new UserVendorKey();
		key.setTransactionDate(new Date());
		key.setUserIdentity(commitRequest.getUserId());
		key.setVendorIdentity(commitRequest.getVendorId());
		key.setValue(commitRequest.getValue());
		return key;
	}

	@Override
	public Vendor vendorFromDTOtoModel(VendorDTO dto) {
		Vendor model = new Vendor();
		model.setUsername(dto.getUsername());
		model.setCardNo(dto.getCardNo());
		return model;
	}

	@Override
	public PaymentModel paymentfromEntitytoModel(PaymentEntity entity) {
		PaymentModel model = new PaymentModel();
		model.setIdPayment(entity.getIdPayment());
		model.setIdUser(entity.getIdUser());
		model.setIdVendor(entity.getIdVendor());
		model.setDate(entity.getDate());
		model.setSate(entity.getSate());
		model.setCommit(entity.getCommit());
		return model;
	}

	@Override
	public PaymentDTO paymentFromModelToDTO(PaymentModel model) {
		PaymentDTO dto = new PaymentDTO();
		dto.setDate(model.getDate());
		dto.setIdPayment(model.getIdPayment());
		dto.setIdVendor(model.getIdVendor());
		dto.setIdUser(model.getIdUser());
		dto.setSate(model.getSate());
		int valeu=model.getCommit().getSection(BytesSection.LENGTH_ONE)[3];
		valeu+=model.getCommit().getSection(BytesSection.LENGTH_FIVE)[3]*5;
		valeu+=model.getCommit().getSection(BytesSection.LENGTH_TEN)[3]*10;
		dto.setValue(valeu);
		return dto;
	}

}
