package com.onboarding.pos.manager.csv;

import java.util.List;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.manager.EmployeeManager;
import com.onboarding.pos.model.Employee;

public class EmployeeCsvManager extends AbstractCsvManager<Employee> implements EmployeeManager {

	public EmployeeCsvManager(AbstractCsvReader<Employee> csvReader,
			AbstractCsvWriter<Employee> csvWriter) {
		super(csvReader, csvWriter);
	}

	@Override
	public long countAll() {
		return getEmployees().size();
	}

	@Override
	public List<Employee> findAll() {
		return getEmployees();
	}

	@Override
	public Employee findById(int id) {
		
		for (Employee employee : getEmployees()) {
			if (employee.getId() == id)
				return employee;
		}
		return null;
	}

	public Employee findByIdNumber(final String idNumber) {
		
		for (Employee employee : getEntities()) {
			if (employee.getIdNumber().equalsIgnoreCase(idNumber)) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public Employee create(Employee emp) throws EntityException {
		
		for (Employee employee : getEmployees()) {
			if (employee.getId() == (emp.getId())) {
				return null;
			}
		}
		getEmployees().add(emp);
		saveEntities();
		return emp;
	}

	@Override
	public Employee update(Employee empUp) {
		
		for (Employee employee : getEmployees()) {
			if (employee.getId() == (empUp.getId())) {
				getEmployees().remove(employee);
				getEmployees().add(empUp);
				return empUp;
			}
		}
		saveEntities();
		return null;
	}

	@Override
	public void delete(Employee empDel) throws EntityException {
		
		Employee storedEmp = findById(empDel.getId());
		
		if (storedEmp == null)
			throw new EntityNotFoundException(Employee.class);

		getEmployees().remove(storedEmp);
		saveEntities();
	}

	private List<Employee> getEmployees() {
		return getEntities();
	}

	@Override
	protected void setEntities(List<Employee> entities) {
		super.setEntities(entities);
	}

	@Override
	protected int nextId() {
		return super.nextId();
	}

	@Override
	protected void loadEntities() {
		super.loadEntities();
	}

	@Override
	protected void saveEntities() {
		try {
			super.saveEntities();
		} catch (CsvWritingException e) {

		}
	}
}
