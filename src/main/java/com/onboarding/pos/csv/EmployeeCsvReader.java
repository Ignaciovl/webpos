package com.onboarding.pos.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.model.Employee;

public class EmployeeCsvReader extends AbstractCsvReader<Employee> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	public EmployeeCsvReader(String csvFilePath) {		
		super(csvFilePath);
	}

	public List<Employee> read() throws CsvReadingException {

		FileReader fileReader = null;
		CSVParser csvFileParser = null;

		try {
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();
			fileReader = new FileReader((getCsvFilePath()));
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			return readEmployees(csvFileParser);

		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			return new ArrayList<Employee>();

		} catch (IOException e) {
			throw new CsvReadingException(getCsvFilePath(), e);
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();
				if (csvFileParser != null)
					csvFileParser.close();
			} catch (IOException e) {
				logger.log(Level.ERROR, e.getMessage(), e.getCause());
			}
		}
	}

	private List<Employee> readEmployees(CSVParser csvFileParser) throws IOException {

		List<Employee> employees = new ArrayList<Employee>();
		List<CSVRecord> csvRecords = csvFileParser.getRecords();
		for (CSVRecord record : csvRecords) {
			Employee employee = new Employee(Integer.parseInt(record.get(EmployeeCsvHeader.ID)),
					record.get(EmployeeCsvHeader.NAME), record.get(EmployeeCsvHeader.ID_NUMBER),
					record.get(EmployeeCsvHeader.CONTACT_NUMBER),
					record.get(EmployeeCsvHeader.EMAIL), record.get(EmployeeCsvHeader.ADDRESS),
					record.get(EmployeeCsvHeader.POSITION), record.get(EmployeeCsvHeader.PASSWORD));
			employees.add(employee);
		}
		return employees;
	}
}
