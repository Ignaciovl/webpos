package com.onboarding.pos.manager;

import com.onboarding.pos.model.Employee;

public interface EmployeeManager extends EntityManager<Employee> {
	public Employee findByIdNumber(String idNumber);

}
