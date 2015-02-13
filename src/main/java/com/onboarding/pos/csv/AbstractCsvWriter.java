package com.onboarding.pos.csv;

import java.util.List;

import com.onboarding.pos.exception.csv.CsvWritingException;

public abstract class AbstractCsvWriter<T> extends AbstractCsvHandler {

	public AbstractCsvWriter(String csvFilePath) {
		super(csvFilePath);
	}

	public abstract List<T> write(List<T> entities) throws CsvWritingException;
	
}
