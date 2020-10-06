package com.project.faurExchange.tables;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class UserVendorKey {

	private String userIdentity;

	private String vendorIdentity;

	private Date transactionDate;

	private int value;

	public UserVendorKey(String userIdentity, String vendorIdentity, Date transactionDate) {
		this.userIdentity = userIdentity;
		this.vendorIdentity = vendorIdentity;
		this.transactionDate = transactionDate;
	}

	public UserVendorKey() {

	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getVendorIdentity() {
		return vendorIdentity;
	}

	public void setVendorIdentity(String vendorIdentity) {
		this.vendorIdentity = vendorIdentity;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		Date truncatedDate = DateUtils.truncate(transactionDate, java.util.Calendar.DAY_OF_MONTH);
		result = prime * result + ((truncatedDate == null) ? 0 : truncatedDate.hashCode());
		result = prime * result + ((userIdentity == null) ? 0 : userIdentity.hashCode());
		result = prime * result + ((vendorIdentity == null) ? 0 : vendorIdentity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVendorKey other = (UserVendorKey) obj;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!DateUtils.isSameDay(transactionDate, other.transactionDate))
			return false;
		if (userIdentity == null) {
			if (other.userIdentity != null)
				return false;
		} else if (!userIdentity.equals(other.userIdentity))
			return false;
		if (vendorIdentity == null) {
			if (other.vendorIdentity != null)
				return false;
		} else if (!vendorIdentity.equals(other.vendorIdentity))
			return false;
		return true;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
