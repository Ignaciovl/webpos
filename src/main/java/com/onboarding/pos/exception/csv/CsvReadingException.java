package com.onboarding.pos.exception.csv;

public class CsvReadingException extends CsvException {

	private static final long serialVersionUID = -1243142011052079202L;
	private static final String MESSAGE = "There was a problem getting information from a CSV file.";
	
	public CsvReadingException(String message, String filePath, Throwable cause) {
		super(MESSAGE, filePath, cause);
	}
	
	public CsvReadingException(String message, String filePath) {
		super(MESSAGE, filePath);
	}
	
	public CsvReadingException(String filePath, Throwable cause) {
		super(MESSAGE , filePath, cause);
	}

	public CsvReadingException(String filePath) {
		super(MESSAGE, filePath);
	}

}
