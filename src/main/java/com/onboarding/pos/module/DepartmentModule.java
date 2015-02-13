package com.onboarding.pos.module;

import java.util.ArrayList;
import java.util.List;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.util.SystemHelper;

public class DepartmentModule extends Module<Department> {

	public DepartmentModule(SystemHelper systemHelper, EntityManager<Department> manager) {
		super(systemHelper, manager);
	}

	@Override
	public void init() {
		systemHelper.println("===================================================");
		systemHelper.println("	Welcome to modelPOS Department Module	");
		systemHelper.println("===================================================");
		printMenu();
	}
	
	private void printMenu() {
		while (true) {
			systemHelper.println("\nOptions:");
			systemHelper.println("	[ 1 ] List all departments");
			systemHelper.println("	[ 2 ] Create department");
			systemHelper.println("	[ 3 ] Update department");
			systemHelper.println("	[ 4 ] Delete department");
			systemHelper.println("	[ 0 ] Exit");
			int option = systemHelper.askOption("\nSelect an option: ",
					new int[] { 1, 2, 3, 4, 0 }, "Invalid option.");

			switch (option) {
			case 1:
				printAllDepartments();
				break;
			case 2:
				createDepartment();
				break;
			case 3:
				updateDepartment();
				break;
			case 4:
				deleteDepartment();
				break;
			default:
				exit();
				continue;
			}

			systemHelper.askPressEnterToContinue();
		}
	}
	
	private void printAllDepartments() {
		systemHelper.println("\nListing all departments...");
		
		List<Department> departments = entityManager.findAll();
		printDepartments(departments, true);
	}

	private void createDepartment() {
		systemHelper.println("\nCreate new department");

		systemHelper.println("Enter the information for the new department:");
		String code = systemHelper.askString("Code: ");
		String name = systemHelper.askString("Name: ");
		Department department = null;
		try {
			department = new Department(code, name);
		} catch (IllegalArgumentException e) {
			systemHelper.println(e.getMessage());
			systemHelper.println("Department was not created");
			return;
		}
		
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to create this department? (Y/N): ");
		if (confirm) {
			try {
				department = entityManager.create(department);
				systemHelper.println("Department successfully created: ");
				printDepartment(department);
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Department was not created");
				return;
			}
		} else {
			systemHelper.println("Create new department cancelled by user");
		}
	}

	private void updateDepartment() {
		systemHelper.println("\nUpdate department");

		int id = systemHelper.askInt("Enter the ID of the department to update: ");
		Department department = entityManager.findById(id);
		if (department == null) {
			systemHelper.println("There is no existing department with the specifed ID");
			return;
		}
		
		systemHelper.println("Department to update: ");
		printDepartment(department);
		
		systemHelper.println("Now enter the new information for the department:");
		String name = systemHelper.askString("Name: ");
		try {
			department.setName(name);
		} catch (IllegalArgumentException e) {
			systemHelper.println(e.getMessage());
			systemHelper.println("Department was not updated");
			return;
		}
		
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to update this department? (Y/N): ");
		if (confirm) {
			try {
				department = entityManager.update(department);
				systemHelper.println("Department successfully updated: ");
				printDepartment(department);
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Department was not updated");
				return;
			}
		} else {
			systemHelper.println("Delete department cancelled by user");
		}
	}

	private void deleteDepartment() {
		systemHelper.println("\nDelete department");

		int id = systemHelper.askInt("Enter the ID of the department to delete: ");
		Department department = entityManager.findById(id);
		if (department == null) {
			systemHelper.println("There is no existing department with the specifed code");
			return;
		}
		
		systemHelper.println("Department to delete: ");
		printDepartment(department);
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to delete this department? (Y/N): ");
		if (confirm) {
			try {
				entityManager.delete(department);
				systemHelper.println("Department successfully deleted");
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Department was not deleted");
				return;
			}
		} else {
			systemHelper.println("Delete department cancelled by user");
		}
	}

	private void printDepartments(List<Department> departments, boolean printCount) {
		String line = "+------------------------------------------------+%n";
		String format = "| %-5s | %-5s | %-30s |%n";
		
		systemHelper.printf(line);
		systemHelper.printf(format, "ID", "CODE", "NAME");
		systemHelper.printf(line);
		
		for (Department department : departments) {
			systemHelper.printf(format, department.getId(), department.getCode(),
					department.getName());
		}
		systemHelper.printf(line);
		
		if (printCount) {
			systemHelper.printf("| %-46s |%n", "# of Departments: " + departments.size());
			systemHelper.printf(line);
		}
	}
	
	private void printDepartment(Department department) {
		List<Department> departments = new ArrayList<Department>(1);
		departments.add(department);
		printDepartments(departments, false);
	}

}
