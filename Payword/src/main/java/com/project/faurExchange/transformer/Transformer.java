package com.project.faurExchange.transformer;

import com.project.faurExchange.dto.PaymentDTO;
import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.model.User;
import com.project.faurExchange.model.Vendor;
import com.project.faurExchange.service.PaymentModel;
import com.project.faurExchange.tables.PaymentEntity;
import com.project.faurExchange.tables.UserVendorKey;

public interface Transformer {

	UserDTO userFromModeltoDTO(User user);

	VendorDTO vendorFromModeltoDTO(Vendor findVendor);

	UserVendorKey fromCommitRequestToKey(CommitRequest commitRequest);

	Vendor vendorFromDTOtoModel(VendorDTO vendorDTO);

	PaymentModel paymentfromEntitytoModel(PaymentEntity entity);

	PaymentDTO paymentFromModelToDTO(PaymentModel model);
}
