package com.onboarding.pos.manager;

import java.util.List;

import com.onboarding.pos.model.Invoice;
import com.onboarding.pos.model.InvoiceStatus;
import com.onboarding.pos.model.PaymentMethod;

public interface InvoiceManager extends EntityManager<Invoice> {

	public List<Invoice> findByClientIdNumber(String idNumber);

	public List<Invoice> findByEmployeeIdNumber(String idNumber);

	public List<Invoice> findByPaymentMethod(PaymentMethod paymentMethod);

	public List<Invoice> findByStatus(InvoiceStatus status);

}
