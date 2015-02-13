package com.onboarding.pos.exception;

public class ProductNotFoundOnInvoice extends PosException {

	private static final long serialVersionUID = 5719496782197420405L;

	public ProductNotFoundOnInvoice() {
		super("The product is not on the sale");
	}

}
