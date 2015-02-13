package com.onboarding.pos.module;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.InvoiceManager;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.model.Invoice;
import com.onboarding.pos.model.InvoiceProduct;
import com.onboarding.pos.model.InvoiceProductId;
import com.onboarding.pos.model.InvoiceStatus;
import com.onboarding.pos.model.PaymentMethod;
import com.onboarding.pos.util.SystemHelper;

public class InvoiceModule extends Module<Invoice> {

	public InvoiceModule(SystemHelper systemHelper, InvoiceManager manager) {
		super(systemHelper, manager);
	}
	
	@Override
	public InvoiceManager getManager() {
		return (InvoiceManager) super.getManager();
	}

	@Override
	public void init() {
		systemHelper.println("===================================================");
		systemHelper.println("	Welcome to modelPOS Invoice Module	");
		systemHelper.println("===================================================");
		printMenu();
	}
	
	private void printMenu() {
		while (true) {
			systemHelper.println("\nOptions:");
			systemHelper.println("	[ 1 ] View invoice");
			systemHelper.println("	[ 2 ] List all invoices");
			systemHelper.println("	[ 3 ] List invoices by Employee");
			systemHelper.println("	[ 4 ] List invoices by Client");
			systemHelper.println("	[ 5 ] List invoices by Status");
			systemHelper.println("	[ 6 ] List invoices by Payment Method");
			systemHelper.println("	[ 7 ] Create invoice");
			systemHelper.println("	[ 8 ] Update invoice");
			systemHelper.println("	[ 9 ] Delete invoice");
			systemHelper.println("	[ 0 ] Exit");
			int option = systemHelper.askOption("\nSelect an option: ",
					new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 }, "Invalid option.");

			switch (option) {
			case 1:
				viewInvoice();
				break;
			case 2:
				listAllInvoices();
				break;
			case 3:
				listInvoicesByEmployee();
				break;
			case 4:
				listInvoicesByClient();
				break;
			case 5:
				listInvoicesByStatus();
				break;
			case 6:
				listInvoicesByPaymentMethod();
				break;
			case 7:
				createInvoice();
				break;
			case 8:
				updateInvoice();
				break;
			case 9:
				deleteInvoice();
				break;
			default:
				exit();
				continue;
			}

			systemHelper.askPressEnterToContinue();
		}
	}
	
	private void viewInvoice() {
		systemHelper.println("\nView invoice");

		int id = systemHelper.askInt("Enter the ID of the invoice: ");
		Invoice invoice = getManager().findById(id);
		if (invoice == null) {
			systemHelper.println("There is no existing invoice with the specifed ID");
			return;
		}
		
		systemHelper.println("Invoice: ");
		printInvoice(invoice);
	}
	
	private void listAllInvoices() {
		systemHelper.println("\nList all invoices");
		
		List<Invoice> invoices = getManager().findAll();
		printInvoices(invoices, true);
	}
	
	private void listInvoicesByEmployee() {
		systemHelper.println("\nList invoices by Employee");
		
		List<Invoice> invoices = getManager().findByEmployeeIdNumber("123456789");
		printInvoices(invoices, true);
	}
	
	private void listInvoicesByClient() {
		systemHelper.println("\nList invoices by Client");
		
		List<Invoice> invoices = getManager().findByClientIdNumber("11.234.567-k");
		printInvoices(invoices, true);
	}
	
	private void listInvoicesByStatus() {
		systemHelper.println("\nList invoices by Status");
		
		List<Invoice> invoices = getManager().findByStatus(InvoiceStatus.IN_PROGRESS);
		printInvoices(invoices, true);
	}
	
	private void listInvoicesByPaymentMethod() {
		systemHelper.println("\nList invoices by Client");
		
		List<Invoice> invoices = getManager().findByPaymentMethod(PaymentMethod.CASH);
		printInvoices(invoices, true);
	}
	
	private void createInvoice() {
		systemHelper.println("\nCreate new invoice");

		Invoice invoice = null;
		Employee employee = null;
		Client client = null;
		try {
			employee = new Employee();
			employee.setId(1);
			
			client = new Client();
			client.setId(2);
			
			invoice = new Invoice(0, employee, client, new Date(), InvoiceStatus.IN_PROGRESS);
			
			InvoiceProduct invoiceProduct1 = new InvoiceProduct(new InvoiceProductId(0, "00000001"), invoice, new Department(1), "product1", 1, new BigDecimal(10000));
			InvoiceProduct invoiceProduct2 = new InvoiceProduct(new InvoiceProductId(0, "00000002"), invoice, new Department(2), "product2", 2, new BigDecimal(20000));
			InvoiceProduct invoiceProduct3 = new InvoiceProduct(new InvoiceProductId(0, "00000003"), invoice, new Department(3), "product3", 3, new BigDecimal(30000));
			
			invoice.getInvoiceProducts().add(invoiceProduct1);
			invoice.getInvoiceProducts().add(invoiceProduct2);
			invoice.getInvoiceProducts().add(invoiceProduct3);
		} catch (IllegalArgumentException e) {
			systemHelper.println(e.getMessage());
			systemHelper.println("Invoice was not created");
			return;
		}
		
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to create this invoice? (Y/N): ");
		if (confirm) {
			try {
				invoice = getManager().create(invoice);
				systemHelper.println("Invoice successfully created: ");
				printInvoice(invoice);
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Invoice was not created");
				return;
			}
		} else {
			systemHelper.println("Create new invoice cancelled by user");
		}
	}

	private void updateInvoice() {
		systemHelper.println("\nUpdate invoice");

		int id = systemHelper.askInt("Enter the ID of the invoice to update: ");
		Invoice invoice = getManager().findById(id);
		if (invoice == null) {
			systemHelper.println("There is no existing invoice with the specifed ID");
			return;
		}
		
		systemHelper.println("Invoice to update: ");
		printInvoice(invoice);
		
		systemHelper.println("Updating invoice:");
		try {
			invoice.setPaymentMethod(PaymentMethod.CASH);
			invoice.setTotal(new BigDecimal(1000));
		} catch (IllegalArgumentException e) {
			systemHelper.println(e.getMessage());
			systemHelper.println("Invoice was not updated");
			return;
		}
		
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to update this invoice? (Y/N): ");
		if (confirm) {
			try {
				invoice = getManager().update(invoice);
				systemHelper.println("Invoice successfully updated: ");
				printInvoice(invoice);
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Invoice was not updated");
				return;
			}
		} else {
			systemHelper.println("Delete invoice cancelled by user");
		}
	}

	private void deleteInvoice() {
		systemHelper.println("\nDelete invoice");

		int id = systemHelper.askInt("Enter the ID of the invoice to delete: ");
		Invoice invoice = getManager().findById(id);
		if (invoice == null) {
			systemHelper.println("There is no existing invoice with the specifed code");
			return;
		}
		
		systemHelper.println("Invoice to delete: ");
		printInvoice(invoice);
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to delete this invoice? (Y/N): ");
		if (confirm) {
			try {
				getManager().delete(invoice);
				systemHelper.println("Invoice successfully deleted");
			} catch (EntityException e) {
				systemHelper.println(e.getMessage());
				systemHelper.println("Invoice was not deleted");
				return;
			}
		} else {
			systemHelper.println("Delete invoice cancelled by user");
		}
	}
	
	private void printInvoices(List<Invoice> invoices, boolean printCount) {
		if (invoices.isEmpty()) {
			systemHelper.println("No invoices were found");
			return;
		}
		for (Invoice invoice : invoices) {
			systemHelper.println(invoice);
		}
	}
	
	private void printInvoice(Invoice invoice) {
		List<Invoice> invoices = new ArrayList<Invoice>(1);
		invoices.add(invoice);
		printInvoices(invoices, false);
	}

}
