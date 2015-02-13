package com.onboarding.pos.module.csv;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.csv.EmployeeCsvManager;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.util.SystemHelper;

import static org.mockito.Mockito.*;

public class EmployeeCsvModuleTest {

	private static final int ID = 1;
	private static final int ID_TEST = 2;
	private static final int LINES_READED = 7;
	private static final String ID_NUMBER = "17.342.832-4";
	private static final String ANY_STRING = "hola";
	private static final String NAME = "Camilo Stuardo";
	private static final String CONTACT_NUMBER = "95180950";
	private static final String EMAIL = "castuard@outlook.com";
	private static final String ADDRESS = "Av. Cristobal Colon #4647";
	private static final String ADDRESS_TO_UPDATE = "Apoquindo #4700";
	private static final String POSITION = "Developer";
	private static final String PASSWORD = "12345";
	private static final Employee EMPLOYEE = new Employee(ID, NAME, ID_NUMBER, CONTACT_NUMBER,
			EMAIL, ADDRESS, POSITION, PASSWORD);
	private static final Employee EMPLOYEE_TO_UPDATE = new Employee(ID, NAME, ID_NUMBER,
			CONTACT_NUMBER, EMAIL, ADDRESS_TO_UPDATE, POSITION, PASSWORD);

	private EmployeeCsvManager mockedEmployeeCsvManager;
	private SystemHelper mockedSystemHelper;
	private EmployeeCsvModule employeeModule;

	@Before
	public void setUp() throws Exception {
		mockedSystemHelper = mock(SystemHelper.class);
		mockedEmployeeCsvManager = mock(EmployeeCsvManager.class);
		employeeModule = new EmployeeCsvModule(mockedSystemHelper, mockedEmployeeCsvManager);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddEmployeeSuccess() throws EntityException {
		when(mockedSystemHelper.readInt()).thenReturn(ID);
		when(mockedSystemHelper.readLine()).thenReturn("..");
		when(mockedEmployeeCsvManager.create(any(Employee.class))).thenReturn(EMPLOYEE);
		Employee addedEmployee = employeeModule.addEmployee();
		verify(mockedSystemHelper).readInt();
		verify(mockedSystemHelper, times(LINES_READED)).readLine();
		verify(mockedEmployeeCsvManager).create(any(Employee.class));
		assertNotNull(addedEmployee);
	}

	@Test
	public void testAddEmployeeNullEmployee() throws EntityException {
		when(mockedSystemHelper.readInt()).thenReturn(ID);
		when(mockedSystemHelper.readLine()).thenReturn("..");
		when(mockedEmployeeCsvManager.create(any(Employee.class))).thenReturn(null);
		Employee addedEmployee = employeeModule.addEmployee();
		verify(mockedSystemHelper).readInt();
		verify(mockedSystemHelper, times(LINES_READED)).readLine();
		verify(mockedEmployeeCsvManager).create(any(Employee.class));
		assertNull(addedEmployee);
	}

	@Test
	public void testUpdateEmployeeSuccess() throws EntityException {
		when(mockedSystemHelper.readInt()).thenReturn(ID_TEST);
		when(mockedSystemHelper.readLine()).thenReturn(ANY_STRING);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		when(mockedEmployeeCsvManager.findById(anyInt())).thenReturn(EMPLOYEE_TO_UPDATE);
		when(mockedEmployeeCsvManager.update(any(Employee.class))).thenReturn(EMPLOYEE_TO_UPDATE);
		Employee updatedEmployee = employeeModule.updateEmployee();
		verify(mockedSystemHelper).readInt();
		verify(mockedSystemHelper, times(LINES_READED)).readLine();
		verify(mockedSystemHelper, times(LINES_READED)).askYesOrNo(anyString());
		verify(mockedEmployeeCsvManager).update(any(Employee.class));
		assertNotNull(updatedEmployee);
		assertTrue(updatedEmployee == EMPLOYEE_TO_UPDATE);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateEmployeeNotFound() {
		when(mockedSystemHelper.readInt()).thenReturn(ID_TEST);
		when(mockedSystemHelper.readLine()).thenReturn(ANY_STRING);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		when(mockedEmployeeCsvManager.findById(anyInt())).thenReturn(null);
		when(mockedEmployeeCsvManager.update(any(Employee.class))).thenReturn(null);
		Employee updatedEmployee = employeeModule.updateEmployee();
		assertNull(updatedEmployee);
	}

	@Test
	public void testDeleteEmployeeSuccess() {
		when(mockedSystemHelper.readInt()).thenReturn(ID);
		when(mockedEmployeeCsvManager.findById(anyInt())).thenReturn(EMPLOYEE);
		Employee deletedEmployee = employeeModule.deleteEmployee();
		assertNotNull(deletedEmployee);
	}
	
	@Test
	public void testDeleteEmployeeNotFound() {
		when(mockedSystemHelper.readInt()).thenReturn(ID);
		when(mockedEmployeeCsvManager.findById(anyInt())).thenReturn(null);
		Employee deletedEmployee = employeeModule.deleteEmployee();
		assertNull(deletedEmployee);
	}
}
