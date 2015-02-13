package com.onboarding.pos.csv;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public abstract class AbstractCsvHandler {

	private String csvFilePath;

	public AbstractCsvHandler(String csvFilePath) {
		setCsvFilePath(csvFilePath);
	}

	public String getCsvFilePath() {
		return csvFilePath;
	}

	public void setCsvFilePath(String csvFilePath) {
		Validate.isTrue(csvFilePath != null, "The file path cannot be NULL");
		Validate.isTrue(!csvFilePath.isEmpty(), "The file path cannot be empty");
		Validate.isTrue(!csvFilePath.trim().isEmpty(), "The file path cannot be blank spaces");
		Validate.isTrue(StringUtils.endsWithIgnoreCase(csvFilePath, ".csv"),
				"The file path does not point to a CSV file");
		this.csvFilePath = csvFilePath;
	}
	
	protected <E extends Enum<E>> String[] headersFromEnum(Class<E> enumType) {
		E[] enumValues = enumType.getEnumConstants();
		String[] headers = new String[enumValues.length];

		int index = 0;
		for (E enumValue : enumValues) {
			headers[index++] = enumValue.toString();
		}
		
		return headers;
	}
	
}
