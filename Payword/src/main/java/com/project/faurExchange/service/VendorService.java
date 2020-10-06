package com.project.faurExchange.service;

import java.util.List;

import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.exception.ExceptionVendor;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.model.User;
import com.project.faurExchange.security.Commit;

public interface VendorService {

	VendorDTO retrieveVendorDTOByUsername(String vendorId) throws ExceptionVendor;

	List<PaymentModel> retrievePayments(String username);

	void addPaymentTOVendor(Commit commit, CommitRequest commitRequest);

	int reedemPaymentsList(VendorDTO vendorDTO) throws ExceptionUser;

	boolean checkPaymentSingnatures(User user, Commit commit);

	int reedemPayment(String vendorId, int idPayment) throws ExceptionVendor,ExceptionUser;

}
