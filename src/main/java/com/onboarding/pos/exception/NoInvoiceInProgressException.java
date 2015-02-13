package com.onboarding.pos.exception;

public class NoInvoiceInProgressException extends PosException {

	private static final long serialVersionUID = -5179783540777440088L;

	public NoInvoiceInProgressException() {
		super("There is no sale on progress. Can not perfom the action requested.");
	}

}
