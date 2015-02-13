package com.onboarding.pos.exception.csv;

import com.onboarding.pos.exception.PosException;

public class CsvException extends PosException {

	private static final long serialVersionUID = -5572420428072282677L;
	
	protected String filePath;
	
	public CsvException(String message, String filePath) {
		super(message);
		setFilePath(filePath);
	}
	
	public CsvException(String message, String filePath, Throwable cause) {
		super(message, cause);
		setFilePath(filePath);
	}

	public String getFullMessage() {
		return getMessage() + " (Filepath: " + getFilePath() + ")";
	}
	
	public String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
