package com.project.faurExchange.repsository;

import com.project.faurExchange.security.Pay;
import java.util.List;

import com.project.faurExchange.tables.UserVendorKey;

public interface TransactionRepository {

	boolean isFirstPayment(UserVendorKey userVendorKey);

	List<Pay> findUserVendorPayowrds(UserVendorKey userVendorKey);

	List<Pay> generateHashChain(int valueLengtChain);
}
