package com.onboarding.pos.exception;

public class InvalidProductQuantityException extends PosException {

	private static final long serialVersionUID = -1719163407216702017L;

	public InvalidProductQuantityException() {
		super("The product quantity is invalid.");
	}

}
