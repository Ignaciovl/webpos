package com.onboarding.pos.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.model.Employee;

public class EmployeeCsvWriter extends AbstractCsvWriter<Employee> {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private Logger logger;

	public EmployeeCsvWriter(String csvFilePath) {
		super(csvFilePath);
	}

	public List<Employee> write(List<Employee> employees) throws CsvWritingException {

		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		try {
			fileWriter = new FileWriter(getCsvFilePath());
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			List<String[]> header = new ArrayList<String[]>();
			header.add(headersFromEnum(EmployeeCsvHeader.class));
			csvFilePrinter.printRecords(header);
			writeEmployees(csvFilePrinter, employees);
			fileWriter.flush();
			fileWriter.close();
			csvFilePrinter.close();
		} catch (IOException e) {
			logger.error("There was an error while writing the employee list", e);
		} finally {
			if (csvFilePrinter != null) {
				try {
					csvFilePrinter.close();
				} catch (IOException e) {
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
				}
			}
		}
		return employees;
	}

	private void writeEmployees(CSVPrinter csvFilePrinter, List<Employee> employees)
			throws IOException {
		for (Employee employee : employees) {
			List<String> employeeData = new ArrayList<String>();
			employeeData.add(String.valueOf(employee.getId()));
			employeeData.add(employee.getName());
			employeeData.add(employee.getIdNumber());
			employeeData.add(String.valueOf(employee.getContactNumber()));
			employeeData.add(employee.getEmail());
			employeeData.add(employee.getAddress());
			employeeData.add(employee.getPosition());
			employeeData.add(employee.getPassword());
			csvFilePrinter.printRecord(employeeData);
		}
	}
}
