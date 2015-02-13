package com.onboarding.pos.exception;

public class InvoiceInProgressException extends PosException {

	private static final long serialVersionUID = -9051161721750818516L;

	public InvoiceInProgressException() {
		super("There is a sale on progress. Can not perfom the action requested.");
	}

}
