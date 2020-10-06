package com.project.faurExchange.service;

import java.util.List;

import com.project.faurExchange.dto.JsonResponse;
import com.project.faurExchange.dto.PaymentDTO;
import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.exception.ExceptionVendor;
import com.project.faurExchange.model.CommitRequest;

public interface BrokerService {

	JsonResponse userRegistration(UserDTO userDto, String ipAddr);
	
	JsonResponse userCommitRequest(CommitRequest commitRequest);

	List<PaymentDTO> retrievePaymentsListByVendor(String vendorId);

	JsonResponse reedemPaymentsList(VendorDTO vendorDTO) throws ExceptionUser;

	int reedemOnePayment(String vendorId, int idPayment) throws ExceptionVendor, ExceptionUser;
}

