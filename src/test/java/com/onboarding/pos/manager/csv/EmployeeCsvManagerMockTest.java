package com.onboarding.pos.manager.csv;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.csv.EmployeeCsvReader;
import com.onboarding.pos.csv.EmployeeCsvWriter;
import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.manager.csv.EmployeeCsvManager;
import com.onboarding.pos.model.Employee;

import static org.mockito.Mockito.*;

public class EmployeeCsvManagerMockTest {

	EmployeeCsvManager empMan = null;
	String csvFilePath = null;
	AbstractCsvWriter<Employee> csvWriterMock;
	AbstractCsvReader<Employee> csvReaderMock;
	List<Employee> employees;
	List<Employee> testEmployees = null;

	Employee emp = null;
	Employee empUp = null;
	Employee empG = null;

	@Before
	public void setUp() throws Exception {

		csvReaderMock = mock(EmployeeCsvReader.class);
		csvWriterMock = mock(EmployeeCsvWriter.class);
		empMan = new EmployeeCsvManager(csvReaderMock, csvWriterMock);

		emp = new Employee(10, "Joe", "17342832-4", "95180950",
				"castuardo@outlook.com", "Alcazar#2106", "Cashier", "123456");
		empUp = new Employee(10, "Joe", "17342832-4", "95180950",
				"castuardo@outlook.com", "Av.CristobalColon#4647B", "Manager", "123abc");
		empG = new Employee(20, "Joe", "19654789-4", "98573958",
				"joem@hotmail.com", "Av.CristobalColon#4647A", "Asistant", "micasa");
		
		empMan.setCsvReader(csvReaderMock);
		empMan.setCsvWriter(csvWriterMock);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCountAll() throws CsvReadingException {

		testEmployees = new ArrayList<Employee>();
		when(csvReaderMock.read()).thenReturn(testEmployees);
		employees = empMan.findAll();

		verify(csvReaderMock).read();

		assertTrue(employees.isEmpty());
		assertNotNull(empMan.countAll());
		
	}

	@Test
	public void testFindAll() throws CsvReadingException {

		testEmployees = new ArrayList<Employee>();
		testEmployees.add(emp);
		when(csvReaderMock.read()).thenReturn(testEmployees);

		assertFalse(empMan.findAll().isEmpty());
		verify(csvReaderMock).read();
		assertNotNull(empMan.findAll());
		
	}

	@Test
	public void testFindById() {

		int id = 10;

		assertNull(empMan.findById(id));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreate() throws CsvWritingException, IOException, EntityException {

		testEmployees = new ArrayList<Employee>();
		
		assertNotNull(empMan.countAll() == 0);
		
		testEmployees.add(emp);
		testEmployees.add(empUp);		
		
		when(csvWriterMock.write(anyList())).thenReturn(testEmployees);
		
		assertEquals(empMan.create(emp), emp);
		assertNotEquals(empMan.create(empUp), empUp);
		empMan.delete(emp);
		
		verify(csvWriterMock, times(2)).write(anyList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() throws CsvWritingException, EntityException {

		assertNotNull(empMan.countAll() == 0);

		when(csvWriterMock.write(anyList())).thenReturn(testEmployees);
		
		assertEquals(empMan.create(emp), emp);
		assertNotEquals(emp, empUp);
		assertNotEquals(empMan.update(empG), empG);
		assertEquals(empMan.update(empUp), empUp);
		empMan.delete(empUp);
		verify(csvWriterMock, times(3)).write(anyList());
	}

	@SuppressWarnings("unchecked")
	@Test (expected = EntityNotFoundException.class)
	public void testDelete() throws CsvWritingException, CsvReadingException, EntityException {

		assertNotNull(empMan.countAll() == 0);

		when(csvWriterMock.write(anyList())).thenReturn(testEmployees);
		when(csvReaderMock.read()).thenReturn(testEmployees);
		
		employees = empMan.findAll();		
		assertEquals(empMan.create(emp), emp);		
		assertFalse(employees.isEmpty());		
		empMan.delete(emp);
		assertTrue(employees.isEmpty());
		verify(csvWriterMock, times(2)).write(anyList());
		empMan.delete(empG);

	}

}
