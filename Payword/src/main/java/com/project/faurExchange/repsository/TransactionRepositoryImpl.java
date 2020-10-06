package com.project.faurExchange.repsository;

import com.project.faurExchange.security.Pay;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.faurExchange.security.Crypto;
import com.project.faurExchange.tables.UserVendorKey;
import com.project.faurExchange.tables.VendorDB;


@Component
public class TransactionRepositoryImpl implements TransactionRepository {

	@Autowired
	private VendorDB vendorDb;

	@Override
	public List<Pay> findUserVendorPayowrds(UserVendorKey userVendorKey) {
		return vendorDb.getPaywords().get(userVendorKey);
	}


	@Override
	public  List<Pay> generateHashChain(int valueLengtChain) {
		List<Pay> currentHashChain = new ArrayList<>();

		byte[] cn = Crypto.getSecret(1024);

		Pay last = new Pay(cn);
		currentHashChain.add(last);
		int i = 1;
		for (i = valueLengtChain - 2; i >= 0; --i) {
			Pay current = new Pay(last);
			currentHashChain.add(current);

			last = current;
		}
		return currentHashChain;
	}

	@Override
	public boolean isFirstPayment(UserVendorKey userVendorKey) {
		return vendorDb.getPayments().containsKey(userVendorKey);
	}

}
