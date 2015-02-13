package com.onboarding.pos.exception.csv;

public class CsvWritingException extends CsvException {

	private static final long serialVersionUID = 188173024790312876L;
	private static final String MESSAGE = "There was a problem updating information from a CSV file.";
	
	public CsvWritingException(String filePath, Throwable cause) {
		super(MESSAGE , filePath, cause);
	}

	public CsvWritingException(String filePath) {
		super(MESSAGE, filePath);
	}

}
