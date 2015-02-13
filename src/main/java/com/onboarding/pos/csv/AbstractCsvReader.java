package com.onboarding.pos.csv;

import java.util.List;

import com.onboarding.pos.exception.csv.CsvReadingException;

public abstract class AbstractCsvReader<T> extends AbstractCsvHandler {

	public AbstractCsvReader(String csvFilePath) {
		super(csvFilePath);
	}

	public abstract List<T> read() throws CsvReadingException;

}
