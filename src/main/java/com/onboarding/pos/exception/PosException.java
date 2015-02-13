package com.onboarding.pos.exception;

public class PosException extends Exception {

	private static final long serialVersionUID = -6987427795814986155L;

	public PosException(String message) {
		super(message);
	}

	public PosException(String message, Throwable cause) {
		super(message, cause);
	}

}
