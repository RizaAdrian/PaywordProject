package com.project.faurExchange.tables;

import com.project.faurExchange.security.Pay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.project.faurExchange.exception.ExceptionVendor;
import com.project.faurExchange.model.Vendor;
import com.project.faurExchange.security.Commit;
import com.project.faurExchange.security.Payment;


@Component
public class VendorDB {

	private List<Vendor> vendors;

	/** This map contain vendor id*/
	private Map<String, List<Commit>> commitements;

	private Map<UserVendorKey, List<Pay>> paywords;

	private Map<UserVendorKey, List<Payment>> payments;

	public VendorDB() {
		this.commitements = new HashMap<>();
		this.paywords = new HashMap<>();
		this.payments = new HashMap<>();
		this.vendors = new ArrayList<>();
	}

	@PostConstruct
	private void init() {
		this.vendors = new ArrayList<>();
		this.paywords = new HashMap<>();
		this.payments = new HashMap<>();
		Vendor vendor1 = new Vendor("Vendor1");
		vendors.add(vendor1);
		Vendor vendor2 = new Vendor("Vendor2");
		vendors.add(vendor2);
		Vendor vendor3 = new Vendor("Vendor3");
		vendors.add(vendor3);
	}

	public Vendor findVendor(String vendorId) throws ExceptionVendor {
		for (Vendor vendor : vendors) {
			if ((vendorId).equals(vendor.getUsername())) {
				return vendor;
			}
		}
		throw new ExceptionVendor("This vendor is not exist");
	}

	public Map<String, List<Commit>> getCommitements() {
		return commitements;
	}

	public void setCommitements(Map<String, List<Commit>> commitements) {
		this.commitements = commitements;
	}

	public Map<UserVendorKey, List<Pay>> getPaywords() {
		return paywords;
	}

	public void setPaywords(Map<UserVendorKey, List<Pay>> paywords) {
		this.paywords = paywords;
	}

	public Map<UserVendorKey, List<Payment>> getPayments() {
		return payments;
	}

	public void setPayments(Map<UserVendorKey, List<Payment>> payments) {
		this.payments = payments;
	}

	public void addMoneyToVendorAccount(int money, String vendorID) {
		for (Vendor vendor : vendors) {
			if ((vendorID).equals(vendor.getUsername()))
				vendor.addBalance(money);

		}

	}

}
