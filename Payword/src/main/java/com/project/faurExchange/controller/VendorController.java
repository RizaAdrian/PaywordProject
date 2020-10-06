package com.project.faurExchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.faurExchange.dto.JsonResponse;
import com.project.faurExchange.dto.PaymentDTO;
import com.project.faurExchange.dto.VendorDTO;
import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.service.BrokerService;

@Controller
public class VendorController {

	@Autowired
	BrokerService brokerService;

	@RequestMapping(value = "/vendor/paymentslist", method = RequestMethod.POST)
	public @ResponseBody List<PaymentDTO> paymentslist(@RequestBody VendorDTO vendorDTO) {

		return brokerService.retrievePaymentsListByVendor(vendorDTO.getUsername());
	}

	@RequestMapping(value = "/vendor/reedem", method = RequestMethod.POST)
	public @ResponseBody JsonResponse reedem(@RequestBody VendorDTO vendorDTO) throws ExceptionUser {

		return brokerService.reedemPaymentsList(vendorDTO);
	}

	@RequestMapping(value = "/reedem/{vendorId}/{idPayment}", method = RequestMethod.GET)
	public @ResponseBody String reedemOnePayment(@PathVariable String vendorId, @PathVariable int idPayment)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		int result= brokerService.reedemOnePayment(vendorId, idPayment);
		sb.append("Paymemt with id: " + idPayment );
		if(result==0){
			sb.append(" has been all ready reedem\n" );
		}
		else{
			sb.append(" has been reedem\n" );
		}
		sb.append("Now vendor get in his accoount : " + result + " extra money");
		return sb.toString();
	}
}
