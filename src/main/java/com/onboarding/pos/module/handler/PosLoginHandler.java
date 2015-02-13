package com.onboarding.pos.module.handler;

import com.onboarding.pos.manager.EmployeeManager;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.util.SystemHelper;

public class PosLoginHandler {

	private SystemHelper systemHelper;
	private EmployeeManager employeeManager;

	public PosLoginHandler(SystemHelper systemHelper, EmployeeManager employeeManager) {
		this.systemHelper = systemHelper;
		this.employeeManager = employeeManager;
	}

	public SystemHelper getSystemHelper() {
		return systemHelper;
	}

	public void setSystemHelper(SystemHelper systemHelper) {
		this.systemHelper = systemHelper;
	}

	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public Employee logIn() {
		String idNumber = getSystemHelper().askString("\nUser Id Number: ");
		String password = getSystemHelper().askString("Password: ");
//		String password = getSystemHelper().readPassword("Password: ");
		
		return validateLogInInformation(idNumber, password);
	}

	private Employee validateLogInInformation(String idNumber, String password) {
		Employee employee = getEmployeeManager().findByIdNumber(idNumber);
		
		if (employee != null && password.equalsIgnoreCase(employee.getPassword())) {
			getSystemHelper().println("\nLogged in sucessfully, welcome " + employee.getName());
		} else {
			getSystemHelper().println("\nEither the Id Number or the Password that you have provided don't match, logging in was aborted");
			employee = null;
		}
		
		getSystemHelper().askPressEnterToContinue();
		
		return employee;
	}

}
