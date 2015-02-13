package com.onboarding.pos.module;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.model.Product;
import com.onboarding.pos.util.SystemHelper;

public class ProductModule extends Module<Product> {

	private EntityManager<Department> departmentManager;
	private String[] departmentCodes;

	public ProductModule(SystemHelper systemHelper, EntityManager<Product> manager,
			EntityManager<Department> departmentManager) {
		super(systemHelper, manager);
		this.departmentManager = departmentManager;
	}

	@Override
	public void init() {
		systemHelper.println("===================================================");
		systemHelper.println("	Welcome to modelPOS Product Module	");
		systemHelper.println("===================================================");
		printMenu();
	}

	private void printMenu() {
		while (true) {
			systemHelper.println("\nOptions:");
			systemHelper.println("	[ 1 ] List all products");
			systemHelper.println("	[ 2 ] Create product");
			systemHelper.println("	[ 3 ] Update product");
			systemHelper.println("	[ 4 ] Delete product");
			systemHelper.println("	[ 0 ] Exit");
			int option = systemHelper.askOption("\nSelect an option: ",
					new int[] { 1, 2, 3, 4, 0 }, "Invalid option.");

			switch (option) {
			case 1:
				printAllProducts();
				break;
			case 2:
				createProduct();
				break;
			case 3:
				updateProduct();
				break;
			case 4:
				deleteProduct();
				break;
			default:
				exit();
				continue;
			}

			systemHelper.askPressEnterToContinue();
		}
	}

	private void printAllProducts() {
		systemHelper.println("\nListing all products...");

		List<Product> products = entityManager.findAll();
		printProducts(products, true);
	}

	private void createProduct() {
		systemHelper.println("\nCreate new product");

		systemHelper.println("Enter the information for the new product:");
		String code = systemHelper.askString("Code: ");
		String name = systemHelper.askString("Name: ");
		String departmentCode = askDepartmentCode();
		BigDecimal price = systemHelper.askBigDecimal("Price: ");
		Product product = null;
		try {
			product = new Product(code, name, departmentCode, price);
		} catch (IllegalArgumentException e) {
			systemHelper.println(e.getMessage());
			systemHelper.println("Product was not created");
			return;
		}

		boolean confirm = systemHelper
				.askYesOrNo("\nAre you sure you want to create this product? (Y/N): ");
		if (confirm) {
			try {
				product = entityManager.create(product);
				systemHelper.println("Product successfully created: ");
				printProduct(product);
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Product was not created");
				return;
			}
		} else {
			systemHelper.println("Create new product cancelled by user");
		}
	}

	private void updateProduct() {
		systemHelper.println("\nUpdate product");

		int id = systemHelper.askInt("Enter the ID of the product to update: ");
		Product product = entityManager.findById(id);
		if (product == null) {
			systemHelper.println("There is no existing product with the specifed ID");
			return;
		}

		systemHelper.println("Product to update: ");
		printProduct(product);

		systemHelper.println("Now enter the new information for the product:");
		String name = systemHelper.askString("Name: ");
		String departmentCode = askDepartmentCode();
		BigDecimal price = systemHelper.askBigDecimal("Price: ");
		try {
			product.setName(name);
			product.setDepartmentCode(departmentCode);
			product.setPrice(price);
		} catch (IllegalArgumentException e) {
			systemHelper.println(e.getMessage());
			systemHelper.println("Product was not updated");
			return;
		}

		boolean confirm = systemHelper
				.askYesOrNo("\nAre you sure you want to update this product? (Y/N): ");
		if (confirm) {
			try {
				product = entityManager.update(product);
				systemHelper.println("Product successfully updated: ");
				printProduct(product);
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Product was not updated");
				return;
			}
		} else {
			systemHelper.println("Delete product cancelled by user");
		}
	}

	private void deleteProduct() {
		systemHelper.println("\nDelete product");

		int id = systemHelper.askInt("Enter the ID of the product to delete: ");
		Product product = entityManager.findById(id);
		if (product == null) {
			systemHelper.println("There is no existing product with the specifed code");
			return;
		}

		systemHelper.println("Product to delete: ");
		printProduct(product);
		boolean confirm = systemHelper
				.askYesOrNo("\nAre you sure you want to delete this product? (Y/N): ");
		if (confirm) {
			try {
				entityManager.delete(product);
				systemHelper.println("Product successfully deleted");
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Product was not deleted");
				return;
			}
		} else {
			systemHelper.println("Delete product cancelled by user");
		}
	}

	private void printProducts(List<Product> products, boolean printCount) {
		String line = "+----------------------------------------------------------------------------------+%n";
		String format = "| %-5s | %-10s | %-30s | %-10s | %-13s |%n";

		systemHelper.printf(line);
		systemHelper.printf(format, "ID", "CODE", "DESCRIPTION", "DEPARTMENT", "PRICE (CLP)");
		systemHelper.printf(line);

		format = "| %-5s | %-10s | %-30s | %-10s | %,13.2f |%n";
		for (Product product : products) {
			systemHelper.printf(format, product.getId(), product.getCode(), product.getName(),
					product.getDepartmentCode(), product.getPrice());
		}
		systemHelper.printf(line);

		if (printCount) {
			systemHelper.printf("| %-80s |%n", "# of Products: " + products.size());
			systemHelper.printf(line);
		}
	}

	private void printProduct(Product product) {
		List<Product> products = new ArrayList<Product>(1);
		products.add(product);
		printProducts(products, false);
	}

	private EntityManager<Department> getDepartmentManager() {
		return departmentManager;
	}

	private String[] getValidDepartmentCodes() {
		if (departmentCodes == null) {
			List<Department> departments = getDepartmentManager().findAll();
			departmentCodes = new String[departments.size()];
			for (int index = 0; index < departments.size(); index++) {
				departmentCodes[index] = departments.get(index).getCode();
			}
		}
		return departmentCodes;
	}

	private String askDepartmentCode() {
		if (getValidDepartmentCodes() != null && getValidDepartmentCodes().length > 0)
			return systemHelper.askOption("Department code: ", getValidDepartmentCodes(),
					"Invalid department code.");
		else
			return systemHelper.askString("Department code: ");
	}

}
