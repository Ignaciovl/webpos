package com.onboarding.pos.manager.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.csv.EmployeeCsvReader;
import com.onboarding.pos.csv.EmployeeCsvWriter;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.model.Employee;

public class EmployeeCsvManagerTest {

	private static final String CSV_FILE_PATH = "src/test/resources/csv/Employees.csv";
	private static final int NUMBER_OF_EMPLOYEES = 1;
	private static final int EMPTY_LIST = 0;
	private static final int NON_EXISTENT_ID = 13;

	private static final Employee EMPLOYEE = new Employee(10, "Joe", "17342832-4", "95180950",
			"castuardo@outlook.com", "Alcazar#2106", "Cashier", "123456");
	private static final Employee EMPLOYEE_TO_UPDATE = new Employee(10, "Joe", "17342832-4",
			"95180950", "castuardo@outlook.com", "Av.CristobalColon#4647B", "Manager", "123abc");

	private EmployeeCsvManager employeeManager = null;
	private AbstractCsvWriter<Employee> csvWriter;
	private AbstractCsvReader<Employee> csvReader;

	@Before
	public void setUp() throws Exception {
		csvReader = new EmployeeCsvReader(CSV_FILE_PATH);
		csvWriter = new EmployeeCsvWriter(CSV_FILE_PATH);
		employeeManager = new EmployeeCsvManager(csvReader, csvWriter);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCountAll() throws EntityException {
		assertTrue(employeeManager.getEntities().isEmpty());
		employeeManager.create(EMPLOYEE);
		assertTrue(employeeManager.countAll() == NUMBER_OF_EMPLOYEES);
		employeeManager.delete(EMPLOYEE);
	}

	@Test
	public void testCountAllEmptyList() {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
	}

	@Test
	public void testFindAllSuccess() {
		assertNotNull(employeeManager.findAll());
	}

	@Test
	public void testFindById() throws EntityException {
		employeeManager.create(EMPLOYEE);
		assertNotNull(employeeManager.findById(EMPLOYEE.getId()));
		employeeManager.delete(EMPLOYEE);
	}

	@Test
	public void testFindByIdFail() {
		assertNull(employeeManager.findById(NON_EXISTENT_ID));
	}

	@Test
	public void testCreateSuccess() throws EntityException {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
		assertEquals(employeeManager.create(EMPLOYEE), EMPLOYEE);
		employeeManager.delete(EMPLOYEE);
	}

	@Test
	public void testCreateDuplicatedEmployee() throws EntityException {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
		assertEquals(employeeManager.create(EMPLOYEE), EMPLOYEE);
		assertNull(employeeManager.create(EMPLOYEE));
		employeeManager.delete(EMPLOYEE);
	}

	@Test
	public void testUpdateSuccess() throws EntityException {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
		assertEquals(employeeManager.create(EMPLOYEE), EMPLOYEE);
		assertNotEquals(EMPLOYEE, EMPLOYEE_TO_UPDATE);
		assertEquals(employeeManager.update(EMPLOYEE_TO_UPDATE), EMPLOYEE_TO_UPDATE);
		employeeManager.delete(EMPLOYEE_TO_UPDATE);
	}

	@Test
	public void testUpdateNotFoundEmployee() {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
		assertNull(employeeManager.update(EMPLOYEE));
	}

	@Test
	public void testDeleteSuccess() throws EntityException {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
		assertEquals(employeeManager.create(EMPLOYEE), EMPLOYEE);
		assertTrue(employeeManager.getEntities().size() != EMPTY_LIST);
		employeeManager.delete(EMPLOYEE);
		assertTrue(employeeManager.getEntities().isEmpty());
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteNotFoundEmployee() throws EntityException {
		assertTrue(employeeManager.countAll() == EMPTY_LIST);
		employeeManager.delete(EMPLOYEE);
	}

}
