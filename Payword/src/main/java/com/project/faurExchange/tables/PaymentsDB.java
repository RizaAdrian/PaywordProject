package com.project.faurExchange.tables;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.project.faurExchange.exception.ExceptionVendor;
import com.project.faurExchange.transformer.State;

@Component
public class PaymentsDB {

	List<PaymentEntity> listPayments;

	@PostConstruct
	private void init() {
		this.listPayments = new ArrayList<>();
	}

	public List<PaymentEntity> retriveAllPosibleReedem(String vendorID) {
		List<PaymentEntity> result = new ArrayList<>();
		for (PaymentEntity entity : this.listPayments) {

			if (entity.getIdVendor().equals(vendorID) && entity.getSate().equals(State.WAITING)) {
				result.add(entity);
			}
		}
		return result;
	}

	public void addPayment(PaymentEntity entity) {
		this.listPayments.add(entity);
	}

	public void updatePament(PaymentEntity entity, State state) {
		int index=this.listPayments.indexOf(entity);
		this.listPayments.get(index).setSate(state);
	}
	
	public int getSizePaymentsDB(){
		return this.listPayments.size();
	}

	public PaymentEntity retriveOneReedem(int idPayment) throws ExceptionVendor {
		for (PaymentEntity entity : this.listPayments) {
			if (entity.getIdPayment() == idPayment) {
				return entity;
			}
		}
		throw new ExceptionVendor("This tranzaction not exist");
	}
}
