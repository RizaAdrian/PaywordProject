package com.project.faurExchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.faurExchange.dto.JsonResponse;
import com.project.faurExchange.model.CommitRequest;
import com.project.faurExchange.service.BrokerService;

@Controller
public class UserController {

	@Autowired
	BrokerService brokerService;

	@RequestMapping(value = "/user/pay", method = RequestMethod.POST)
	public @ResponseBody JsonResponse buy(@RequestBody CommitRequest commitRequest) {

		return brokerService.userCommitRequest(commitRequest);
	}
}
