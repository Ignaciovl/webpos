package com.onboarding.pos.module.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.manager.EmployeeManager;
import com.onboarding.pos.manager.hibernate.EmployeeHibernateManager;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.util.SystemHelper;

public class PosLoginHandlerTest {

	private static final String EMPLOYEE_ID_NUMBER = "11.111.111-1";
	private static final String INVALID_ID_NUMBER = " ";
	private static final String CORRECT_EMPLOYEE_PASSWORD = "123456";
	private static final String WRONG_EMPLOYEE_PASSWORD = "abcdef";
	private static final String INVALID_PASSWORD = " ";
	
	private PosLoginHandler posLoginHandler;
	private SystemHelper systemHelperMock;
	private EmployeeManager employeeManagerMock;
	
	@Before
	public void setUp() {
		systemHelperMock = mock(SystemHelper.class);
		employeeManagerMock = mock(EmployeeHibernateManager.class);
		posLoginHandler = new PosLoginHandler(systemHelperMock, employeeManagerMock);
	}
	
	@After
	public void tearDown() {
		systemHelperMock = null;
		employeeManagerMock = null;
		posLoginHandler = null;
	}

	
	@Test
	public void testLoginSuccess() {
		when(systemHelperMock.askString(anyString())).thenReturn(EMPLOYEE_ID_NUMBER);
		when(systemHelperMock.readPassword(anyString())).thenReturn(CORRECT_EMPLOYEE_PASSWORD);
		
		Employee expectedEmployee = new Employee(1, "Employee", EMPLOYEE_ID_NUMBER, "8765432", "employee@email.com", "address", "position", CORRECT_EMPLOYEE_PASSWORD);
		
		when(employeeManagerMock.findByIdNumber(anyString())).thenReturn(expectedEmployee);
		
		Employee resultEmployee = posLoginHandler.logIn();
		
		verify(systemHelperMock).askString(anyString());
		verify(systemHelperMock).readPassword(anyString());
		verify(employeeManagerMock).findByIdNumber(anyString());
		assertEquals(expectedEmployee, resultEmployee);
	}

	@Test
	public void testLoginFailDueToWrongIdNumber() {
		when(systemHelperMock.askString(anyString())).thenReturn(EMPLOYEE_ID_NUMBER);
		when(systemHelperMock.readPassword(anyString())).thenReturn(CORRECT_EMPLOYEE_PASSWORD);
		when(employeeManagerMock.findByIdNumber(anyString())).thenReturn(null);
		
		Employee resultEmployee = posLoginHandler.logIn();
		
		verify(systemHelperMock).askString(anyString());
		verify(systemHelperMock).readPassword(anyString());
		verify(employeeManagerMock).findByIdNumber(anyString());
		assertNull(resultEmployee);
	}

	@Test
	public void testLoginFailDueToWrongPassword() {
		when(systemHelperMock.askString(anyString())).thenReturn(EMPLOYEE_ID_NUMBER);
		when(systemHelperMock.readPassword(anyString())).thenReturn(WRONG_EMPLOYEE_PASSWORD);
		
		Employee expectedEmployee = new Employee(1, "Employee", EMPLOYEE_ID_NUMBER, "8765432", "employee@email.com", "address", "position", CORRECT_EMPLOYEE_PASSWORD);
		
		when(employeeManagerMock.findByIdNumber(anyString())).thenReturn(expectedEmployee);
		
		Employee resultEmployee = posLoginHandler.logIn();
		
		verify(systemHelperMock).askString(anyString());
		verify(systemHelperMock).readPassword(anyString());
		verify(employeeManagerMock).findByIdNumber(anyString());
		assertNull(resultEmployee);
	}

	@Test
	public void testLoginFailDueToInvalidGivenIdNumber() {
		when(systemHelperMock.askString(anyString())).thenReturn(INVALID_ID_NUMBER);
		
		Employee loggedEmployee = posLoginHandler.logIn();
		
		verify(systemHelperMock).askString(anyString());
		assertNull(loggedEmployee);
	}

	@Test
	public void testLoginFailDueToInvalidGivenPassword() {
		when(systemHelperMock.askString(anyString())).thenReturn(EMPLOYEE_ID_NUMBER);
		when(systemHelperMock.readPassword(anyString())).thenReturn(INVALID_PASSWORD);
		
		Employee loggedEmployee = posLoginHandler.logIn();
		
		verify(systemHelperMock).askString(anyString());
		verify(systemHelperMock).readPassword(anyString());
		assertNull(loggedEmployee);
	}
	
}
