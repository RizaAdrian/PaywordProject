package com.project.faurExchange.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.faurExchange.dto.JsonResponse;
import com.project.faurExchange.dto.UserDTO;
import com.project.faurExchange.model.Broker;
import com.project.faurExchange.security.Crypto;
import com.project.faurExchange.service.BrokerService;

@RestController
public class BankController {

	@Autowired
	BrokerService brokerService;

	@Autowired
	Broker broker;

	private final  Logger looger=Logger.getLogger(BankController.class);
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public JsonResponse userRegistration(@RequestBody UserDTO userDto, HttpServletRequest request) {
		String ipAddr = request.getRemoteAddr();

		JsonResponse response = brokerService.userRegistration(userDto, ipAddr);
		if (response.getCertificate() != null) {
			response.setSecurityStatus("Security status: " + Crypto.checkSign(response.getCertificate(), broker));
		}
		looger.info("Userul: " + userDto.getUsername() + "s-a inregistrat! ");
		return response;
	}
}
