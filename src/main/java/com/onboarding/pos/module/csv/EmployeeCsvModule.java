package com.onboarding.pos.module.csv;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.module.Module;
import com.onboarding.pos.util.SystemHelper;

public class EmployeeCsvModule extends Module<Employee> {

	public String id;
	public int employeeId;
	public String empName;
	public String empIdNumber;
	public String empContactNumber;
	public String empEmail;
	public String empAddress;
	public String empPosition;
	public String empPassword;

	public EmployeeCsvModule(SystemHelper systemHelper, EntityManager<Employee> manager) {
		super(systemHelper, manager);
	}

	public void init() {

		getSystemHelper().println("===================================================");
		getSystemHelper().println("	Welcome to modelPOS Employee CSV Module	");
		getSystemHelper().println("===================================================");
		printMenu();
	}

	public void printMenu() {

		getSystemHelper().println("\nOptions:");
		getSystemHelper().println("	[ 1 ] List all employees");
		getSystemHelper().println("	[ 2 ] Add employee");
		getSystemHelper().println("	[ 3 ] Update employee");
		getSystemHelper().println("	[ 4 ] Delete employee");
		getSystemHelper().println("	[ 0 ] Exit");
		int option = getSystemHelper().askOption("\nSelect an option:",
				new int[] { 0, 1, 2, 3, 4 }, "Invalid option. Please select a valid option.");
		switch (option) {
		case 1:
			printAllEmployees();
			printMenu();
			break;
		case 2:
			addEmployee();
			printMenu();
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
	}

	public void printAllEmployees() {

		getSystemHelper().println(
				String.format("| %5s| %25s| %10s| %15s| %25s| %30s| %15s | %17s |", "id", "name",
						"idNumber", "contactNumber", "email", "address", "position", "password"));

		for (Employee employeesList : getManager().findAll()) {
			getSystemHelper().println(employeesList.toString());
		}
	}

	public Employee addEmployee() {

		try {
			Employee employee = getManager().create(askEmployeeInfo());
			return employee;
		} catch (EntityException e) {
			getSystemHelper().println("Cant´t add the employee...");
			return null;
		}
	}

	public Employee updateEmployee() {

		Employee employee;

		try {
			employee = getManager().update(askEmployeeUpdatedInfo());
			return employee;
		} catch (EntityException e) {
			getSystemHelper().println("Cant't update the employee...");
			return null;
		}
	}

	public Employee deleteEmployee() {

		Employee employee = new Employee();

		getSystemHelper().println("Insert the id of the employee you want to delete");
		getSystemHelper().println("===================================================");
		int id = getSystemHelper().readInt();
		employee.setId(id);
		if (getManager().findById(id) == null) {
			getSystemHelper().println("===================================================");
			getSystemHelper().println("Could not delete employee");
			getSystemHelper().println("===================================================");
			return null;
		} else {
			try {
				getManager().delete(employee);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			getSystemHelper().println("===================================================");
			getSystemHelper().println("Employee deleted successfully");
			getSystemHelper().println("===================================================");
		}
		return employee;
	}

	public void exit() {

		boolean confirm = getSystemHelper().askYesOrNo("\nAre you sure you want to exit? (Y/N): ");
		if (confirm) {
			getSystemHelper().println("\nHave a nice day!");
			getSystemHelper().println("===================================================");
			getSystemHelper().exit(0);
		}
	}

	public Employee askEmployeeInfo() {

		Employee employee;

		getSystemHelper().println("\nInsert the data of the new employee");
		getSystemHelper().println("===================================================");

		id = getSystemHelper().askString("\nInsert the id of the employee: ");
		while (isInteger(id)) {
			id = getSystemHelper().askString("\nInvalid Id, insert again: ");
		}
		empName = getSystemHelper().askString("Insert the name of the employee: ");
		while (empName.trim().isEmpty()) {
			empName = getSystemHelper().askString("\nInvalid Name, insert again: ");
		}
		empIdNumber = getSystemHelper().askString("Insert the id number of the employee: ");
		while (empIdNumber.trim().isEmpty()) {
			empIdNumber = getSystemHelper().askString("\nInvalid Id Number, insert again: ");			
		}
		empContactNumber = getSystemHelper().askString("Insert the contact number of the employee: ");
		while (empContactNumber.trim().isEmpty()) {
			empContactNumber = getSystemHelper().askString("\nInvalid Contact Number, insert again: ");			
		}
		empEmail = getSystemHelper().askString("Insert the email of the employee: ");
		while (empEmail.trim().isEmpty()) {
			empEmail = getSystemHelper().askString("\nInvalid Email, insert again: ");			
		}
		empAddress = getSystemHelper().askString("Insert the address of the employee: ");
		while (empAddress.trim().isEmpty()) {
			empAddress = getSystemHelper().askString("\nInvalid Address, insert again: ");			
		}
		empPosition = getSystemHelper().askString("Insert the position of the employee: ");
		while (empPosition.trim().isEmpty()) {
			empPosition = getSystemHelper().askString("\nInvalid Position, insert again: ");			
		}
		empPassword= getSystemHelper().askString("Insert the password of the employee: ");
		while (empPassword.trim().isEmpty()) {
			empPassword = getSystemHelper().askString("\nInvalid Password, insert again: ");			
		}

		employee = new Employee(employeeId = Integer.parseInt(id), empName, empIdNumber,
				empContactNumber, empEmail, empAddress, empPosition, empPassword);

		return employee;
	}

	private Employee askEmployeeUpdatedInfo() {

		Employee employee;

		getSystemHelper().println("Insert the update data of the employee");
		getSystemHelper().println("===================================================");
		getSystemHelper().println("Insert the id of the employee");
		employeeId = getSystemHelper().readInt();
		Employee employeeToUpdate = getManager().findById(employeeId);
		
		if(employeeToUpdate == null){
			return null;
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual name? -" + employeeToUpdate.getName() + "- y/n")) {
			empName = employeeToUpdate.getName();
		} else {
			getSystemHelper().println("Insert the new name");
			empName = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual id number? -" + employeeToUpdate.getIdNumber() + "- y/n")) {
			empIdNumber = employeeToUpdate.getIdNumber();
		} else {
			getSystemHelper().println("Insert the new id number");
			empIdNumber = getSystemHelper().readLine();
		}
		if (getSystemHelper()
				.askYesOrNo(
						"Keep the actual contact number? -" + employeeToUpdate.getContactNumber()
								+ "- y/n")) {
			empContactNumber = employeeToUpdate.getContactNumber();
		} else {
			getSystemHelper().println("Insert the new contact number");
			empContactNumber = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual email? -" + employeeToUpdate.getEmail() + "- y/n")) {
			empEmail = employeeToUpdate.getEmail();
		} else {
			getSystemHelper().println("Insert the new email");
			empEmail = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual address? -" + employeeToUpdate.getAddress() + "- y/n")) {
			empAddress = employeeToUpdate.getAddress();
		} else {
			getSystemHelper().println("Insert the new address");
			empAddress = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual position? -" + employeeToUpdate.getPosition() + "- y/n")) {
			empPosition = employeeToUpdate.getPosition();
		} else {
			getSystemHelper().println("Insert the new position");
			empPosition = getSystemHelper().readLine();
		}
		if (getSystemHelper().askYesOrNo(
				"Keep the actual password? -" + employeeToUpdate.getPassword() + "- y/n")) {
			empPassword = employeeToUpdate.getPassword();
		} else {
			getSystemHelper().println("Insert the new password");
			empPassword = getSystemHelper().readLine();
		}
		employee = new Employee(employeeId, empName, empIdNumber, empContactNumber, empEmail,
				empAddress, empPosition, empPassword);
		return employee;
	}

	private boolean isInteger(String cadena) {

		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
