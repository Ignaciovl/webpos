package com.onboarding.pos.module.hibernate;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.manager.hibernate.EmployeeHibernateManager;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.module.Module;
import com.onboarding.pos.util.HibernateUtil;
import com.onboarding.pos.util.SystemHelper;

public class EmployeeHibernateModule extends Module<Employee> {

	private EmployeeHibernateManager empHibManager = new EmployeeHibernateManager(
			HibernateUtil.getSessionFactory());

	public int empId;
	public String empName;
	public String empIdNumber;
	public String empContactNumber;
	public String empEmail;
	public String empAddress;
	public String empPosition;
	public String empPassword;

	public EmployeeHibernateModule(SystemHelper systemHelper, EntityManager<Employee> manager) {
		super(systemHelper, manager);
	}

	@Override
	public void init() {

		getSystemHelper().println("=============================================================");
		getSystemHelper().println("	Welcome to modelPOS Employee Hibernate Module	");
		getSystemHelper().println("=============================================================");
		printMenu();
	}

	public final void printMenu() {

		try {
			getSystemHelper().println("\nOptions:");
			getSystemHelper().println("	[ 1 ] List all employees");
			getSystemHelper().println("	[ 2 ] Add employee");
			getSystemHelper().println("	[ 3 ] Update employee");
			getSystemHelper().println("	[ 4 ] Delete employee");
			getSystemHelper().println("	[ 0 ] Exit");
			int option = getSystemHelper().askOption("\nSelect an Option",
					new int[] { 0, 1, 2, 3, 4 }, "Invalid option. Please select a valid option.");

			switch (option) {
			case 1:
				printAllEmployees();
				printMenu();
				break;
			case 2:
				addEmployee();
				printMenu();
				break;
			case 3:
				updateEmployee();
				printMenu();
				break;
			case 4:
				deleteEmployee();
				printMenu();
				break;
			default:
				exit();
				break;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	public final void printAllEmployees() {

		getSystemHelper().println(
				String.format("| %5s| %25s| %10s| %15s| %25s| %30s| %15s | %17s |", "id", "name",
						"idNumber", "contactNumber", "email", "address", "position", "password"));

		for (Employee eM : empHibManager.findAll()) {
			getSystemHelper().println(eM.toString());
		}
		printMenu();
	}

	public final void addEmployee() throws EntityException {

		empHibManager.create(askEmployeeInfo());

		getSystemHelper().println("===================================================");
		getSystemHelper().println("Employee added succesfully¡¡¡");
		getSystemHelper().println("===================================================");
	}

	public final void updateEmployee() throws EntityException {

		getSystemHelper().println("Insert the update data of the employee");
		getSystemHelper().println("===================================================");
		empHibManager.update(askEmployeeUpdatedInfo());

		getSystemHelper().println("===================================================");
		getSystemHelper().println("Employee updated succesfully¡¡¡");
		getSystemHelper().println("===================================================");
	}

	private Employee askEmployeeUpdatedInfo() {

		Employee employee;
		Employee employeeById = empHibManager.findById(empId);

		empId = askForInt("Insert the id of the employee");

		if (empHibManager.findById(empId) == null) {
			getSystemHelper().println("Id doesn't exist in the data base");
			askEmployeeUpdatedInfo();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual name? -" + employeeById.getName() + "- y/n")) {
			empName = employeeById.getName();
		} else {
			getSystemHelper().println("Insert the new name");
			empName = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual id number? -" + employeeById.getIdNumber() + "- y/n")) {
			empIdNumber = employeeById.getIdNumber();
		} else {
			getSystemHelper().println("Insert the new id number");
			empIdNumber = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual contact number? -" + employeeById.getContactNumber() + "- y/n")) {
			empContactNumber = employeeById.getContactNumber();
		} else {
			getSystemHelper().println("Insert the new contact number");
			empContactNumber = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual email? -" + employeeById.getEmail() + "- y/n")) {
			empEmail = employeeById.getEmail();
		} else {
			getSystemHelper().println("Insert the new email");
			empEmail = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual address? -" + employeeById.getAddress() + "- y/n")) {
			empAddress = employeeById.getAddress();
		} else {
			getSystemHelper().println("Insert the new address");
			empAddress = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual position? -" + employeeById.getPosition() + "- y/n")) {
			empPosition = employeeById.getPosition();
		} else {
			getSystemHelper().println("Insert the new position");
			empPosition = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual password? -" + employeeById.getPassword() + "- y/n")) {
			empPassword = employeeById.getPassword();
		} else {
			getSystemHelper().println("Insert the new password");
			empPassword = getSystemHelper().readLine();
		}

		employee = new Employee(empId, empName, empIdNumber, empContactNumber, empEmail,
				empAddress, empPosition, empPassword);
		return employee;
	}

	public final void deleteEmployee() throws EntityException {

		getSystemHelper().println("Insert the id of the employee you want to delete");
		getSystemHelper().println("===================================================");
		int id = getSystemHelper().readInt();
		Employee employee = empHibManager.findById(id);
		if (employee == null) {
			getSystemHelper().println("===================================================");
			getSystemHelper().println("Couldn't delete employee");
			getSystemHelper().println("===================================================");
		} else {
			empHibManager.delete(employee);
			getSystemHelper().println("===================================================");
			getSystemHelper().println("Employee deleted successfully");
			getSystemHelper().println("===================================================");
		}
	}

	private Employee askEmployeeInfo() {
		
		int id = askForInt("Insert the id of the employee");
		String name = askForString("Insert the name of the employee");
		String idNumber = askForString("Insert the id number of the employee");
		String contactNumber = askForString("Insert the contact number of the employee");
		String email = askForString("Insert the email of the employee");
		String address = askForString("Insert the address of the employee");
		String position = askForString("Insert the position of the employee");
		String password = askForString("Insert the password of the employee");
		Employee employee = new Employee(id, name, idNumber, contactNumber, email, address,
				position, password);
		return employee;
	}

	public String askForString(final String message) {
		
		getSystemHelper().print(message);
		return getSystemHelper().readLine();
	}

	public int askForInt(final String message) {
		
		getSystemHelper().print(message);
		String input = getSystemHelper().readLine();
		if (isInteger(input)) {
			return Integer.parseInt(input);
		} else {
			getSystemHelper().println("Invalid value");
			return askForInt(message);
		}
	}

	private static boolean isInteger(String cadena) {
		
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
