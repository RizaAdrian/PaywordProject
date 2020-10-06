package com.project.faurExchange.service;

import com.project.faurExchange.security.Pay;
import java.util.List;
import java.util.Map;

import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.model.BytesSection;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.security.Commit;

public interface UserService {

	void commitUserToBroker(CommitRequest commitRequest) throws ExceptionUser;

	UserDTO retrieveUserDTOByUsername(String userId) throws ExceptionUser;

	void addPaymentTOVendor(Commit commit, CommitRequest commitRequest, Map<BytesSection,List<Pay>> allPayment);

}
