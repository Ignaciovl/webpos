package com.onboarding.pos.csv;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.csv.EmployeeCsvWriter;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.model.Employee;

public class EmployeeCsvWriterTest {

	EmployeeCsvWriter eMWriter = new EmployeeCsvWriter("src/main/resources/csv/Employees.csv");
	Employee employee = null;

	@Before
	public void setUp() throws Exception {

		employee = new Employee(1, "Camilo", "17342832-4", "95180950", "castuardo@outlook.com",
				"CristobalColon4647", "Cashier", "123456");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteSuccess() throws IOException, CsvWritingException {
		List<Employee> data = new ArrayList<Employee>();
		data.add(employee);
		List<Employee> employees = eMWriter.write(data);

		assertNotNull(employees);
		assertFalse(employees.isEmpty());
		assertTrue(employees.equals(data));
	}

	@SuppressWarnings("unused")
	@Test (expected = NullPointerException.class)
	public void testWriteNullOriginList() throws IOException, CsvWritingException {
		List<Employee> employees = eMWriter.write(null);
	}
	
	@Test
	public void testWriteEmptyOriginList() throws IOException, CsvWritingException {
		List<Employee> data = new ArrayList<Employee>();
		List<Employee> employees = eMWriter.write(data);

		assertTrue(employees.isEmpty());
	}
}