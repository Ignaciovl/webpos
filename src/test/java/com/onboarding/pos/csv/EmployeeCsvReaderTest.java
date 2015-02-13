package com.onboarding.pos.csv;

import static org.junit.Assert.*;

import java.util.List;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.csv.EmployeeCsvReader;
import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.model.Employee;

public class EmployeeCsvReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRead() throws IOException, CsvReadingException {

		EmployeeCsvReader eR = new EmployeeCsvReader(
				"src/test/resources/csv/Employees.csv");
		List<Employee> employees = eR.read();

		assertNotNull(employees);
		assertTrue(employees.isEmpty());
	}

	@Test
	public void testReadExpectingFileNotFoundException() throws IOException,
			CsvReadingException {
		EmployeeCsvReader eR = new EmployeeCsvReader(
				"src/test/resources/csv/Employees1.csv");
		List<Employee> employees = eR.read();
		assertTrue(employees.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testReadExpectingIllegalArgumentException1() throws IOException,
			CsvReadingException {
		EmployeeCsvReader eR = new EmployeeCsvReader(null);
		eR.read();
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testReadExpectingIllegalArgumentException2() throws IOException, CsvReadingException {
		EmployeeCsvReader eR = new EmployeeCsvReader("");
	}

}
