package com.onboarding.pos.module;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.ClientManager;
import com.onboarding.pos.manager.DepartmentManager;
import com.onboarding.pos.manager.EmployeeManager;
import com.onboarding.pos.manager.InvoiceManager;
import com.onboarding.pos.manager.ProductManager;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.model.Invoice;
import com.onboarding.pos.model.InvoiceProduct;
import com.onboarding.pos.model.InvoiceProductId;
import com.onboarding.pos.model.InvoiceStatus;
import com.onboarding.pos.model.PaymentMethod;
import com.onboarding.pos.model.Product;
import com.onboarding.pos.module.handler.PosLoginHandler;
import com.onboarding.pos.util.SystemHelper;

public class PosModule extends Module<Invoice> {

	private static final int DEFAULT_ID = 0;

	private static final int LIST_ALL_INVOICES_OPTION = 1;
	private static final int CREATE_INVOICE_OPTION = 2;
	private static final int LOAD_INVOICE_OPTION = 3;
	private static final int VOID_INVOICE_OPTION = 4;
	private static final int LOG_OUT_OPTION = 0;

	private static final int ANNONYMOUS_CLIENT_ID = 1;

	private PosLoginHandler posLoginHandler;

	private ProductManager productManager;
	private ClientManager clientManager;
	private EmployeeManager employeeManager;
	private DepartmentManager departmentManager;

	private Employee currentEmployee;
	private Client annonymousClient;
	private Invoice inProgressInvoice;

	private Logger logger;

	public PosModule(final SystemHelper givenSystemHelper,
			final InvoiceManager givenInvoiceManager, final ProductManager givenProductManager,
			final ClientManager givenClientManager, final EmployeeManager givenEmployeeManager,
			final DepartmentManager givenDepartmentManager) {
		
		super(givenSystemHelper, givenInvoiceManager);
		setProductManager(givenProductManager);
		setClientManager(givenClientManager);
		setEmployeeManager(givenEmployeeManager);
		setDepartmentManager(givenDepartmentManager);
		setLogger(LogManager.getLogger(ClientManager.class.getName()));
		setAnnonymousClient(getClientManager().findById(ANNONYMOUS_CLIENT_ID));
		setPosLoginHandler(new PosLoginHandler(givenSystemHelper, givenEmployeeManager));
	}

	@Override
	public void init() {
		
		getSystemHelper().println("===================================================");
		getSystemHelper().println("	Welcome to modelPOS Main Module	");
		getSystemHelper().println("===================================================");
		printLogInMenu();
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(final ProductManager givenProductManager) {
		this.productManager = givenProductManager;
	}

	public ClientManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(final ClientManager givenClientManager) {
		this.clientManager = givenClientManager;
	}

	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(final EmployeeManager givenEmployeeManager) {
		this.employeeManager = givenEmployeeManager;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(final DepartmentManager givenDepartmentManager) {
		this.departmentManager = givenDepartmentManager;
	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public void setCurrentEmployee(final Employee givenCurrentEmployee) {
		this.currentEmployee = givenCurrentEmployee;
	}

	public Client getAnnonymousClient() {
		return annonymousClient;
	}

	public void setAnnonymousClient(final Client newAnnonymousClient) {
		this.annonymousClient = newAnnonymousClient;
	}

	public Invoice getInProgressInvoice() {
		return inProgressInvoice;
	}

	public void setInProgressInvoice(final Invoice newInProgressInvoice) {
		this.inProgressInvoice = newInProgressInvoice;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(final Logger newLogger) {
		logger = newLogger;
	}

	public PosLoginHandler getPosLoginHandler() {
		return posLoginHandler;
	}

	public void setPosLoginHandler(PosLoginHandler posLoginHandler) {
		this.posLoginHandler = posLoginHandler;
	}

	private void printLogInMenu() {
		getSystemHelper().println("\nOptions:");
		getSystemHelper().println("	[ 1 ] Log in");
		getSystemHelper().println("	[ 0 ] Exit");
		int option = getSystemHelper().askOption("\nSelect an option: ", new int[] { 1, 0 },
				"Invalid option. Please select a valid option.");
		if (option == 1) {
			setCurrentEmployee(getPosLoginHandler().logIn());
			if (getCurrentEmployee() != null) {
				printMenu();
			} else {
				printLogInMenu();
			}
		} else {
			exit();
			getSystemHelper().println("\nExit aborted");
			getSystemHelper().askPressEnterToContinue();
			printLogInMenu();
		}
	}

	protected void printAllInvoices() {
		List<Invoice> invoices = getManager().findAll();
		int[] options = new int[invoices.size() + 1];
		Collections.sort(invoices);
		getSystemHelper().print("\n");
		getSystemHelper().println(
				String.format("%5s| %25s| %25s| %25s| %15s| %20s| %s", "Id", "Creation Date",
						"Employee Id Number", "Client Id Number", "Status", "Payment Method",
						"Total"));
		for (int position = 0; position < invoices.size(); position++) {
			options[position] = invoices.get(position).getId();
			getSystemHelper().println(invoices.get(position).toString());
		}
		options[options.length - 1] = 0;
		int option = getSystemHelper().askOption(
				"\nSelect the Id of the invoice that you want to expand, type 0 to exit", options,
				"Invalid option. Please select a valid option");
		if (option != 0) {
			expandChosenInvoice(invoices, option);
			if (getSystemHelper().askYesOrNo("\nCheck another invoice? (Y/N)")) {
				printAllInvoices();
			}
		}
	}

	protected Set<InvoiceProduct> addProducts(final Invoice invoice,
			Set<InvoiceProduct> invoiceProductsSet) {
		getSystemHelper().println("\n===================Add Products====================");
		boolean addAnotherProduct = true;
		do {
			String productCode = getSystemHelper().askString("\nInsert product code: ");
			Product productToAdd = getProductManager().findByCode(productCode);
			if (productToAdd == null) {
				getSystemHelper().println("Invalid Product Code");
			} else {
				int quantity = getSystemHelper().askInt("Insert product quantity: ");
				if (quantity < 1) {
					getSystemHelper().println("Quantity can't be zero or negative");
				} else {
					InvoiceProduct invoiceProductToAdd = productToInvoiceProduct(productToAdd,
							quantity, invoice);
					invoiceProductsSet = addInvoiceProductToSet(invoiceProductsSet,
							invoiceProductToAdd);
					getSystemHelper().println("The product was added successfully");
				}
			}
			if (invoiceProductsSet.isEmpty()) {
				addAnotherProduct = true;
			} else {
				addAnotherProduct = getSystemHelper().askYesOrNo("\nAdd another product? (Y/N)");
			}
		} while (addAnotherProduct);
		return invoiceProductsSet;
	}

	protected Client selectClient() {
		getSystemHelper().println("\n===================Select Client===================");
		getSystemHelper().println("\nInsert the Id Number of the client");
		String clientIdNumber = getSystemHelper().readLine();
		Client client = getClientManager().findByIdNumber(clientIdNumber);
		if (client != null) {
			getSystemHelper().println("\nClient selected successfully");
			return client;
		} else {
			return selectUnregisteredClient(clientIdNumber);
		}
	}

	protected BigDecimal commitPayment(final Set<InvoiceProduct> invoiceProductsList) {
		getSystemHelper().println("\n======================Payment======================");
		BigDecimal totalPrice = calculateTotalPrice(invoiceProductsList);
		BigDecimal totalPayment = getSystemHelper().askBigDecimal("\nInsert payment ammount: ");
		if (!validatePayment(totalPayment, totalPrice)) {
			return null;
		}
		BigDecimal change = totalPayment.subtract(totalPrice);
		getSystemHelper().println("\nPayment done successfully, your change is " + change);
		return totalPrice;
	}

	protected Invoice saveInvoice(final Invoice invoice) {
		if (invoice == null) {
			return null;
		}
		if (getClientManager().findByIdNumber(invoice.getClient().getIdNumber()) != null) {
			invoice.setStatus(InvoiceStatus.COMPLETED);
			try {
				getManager().create(invoice);
			} catch (EntityException e) {
				logger.error("There was an error while creating the invoice", e);
				return null;
			}
			getSystemHelper().println("\nInvoice completed successfully");
			getSystemHelper().askPressEnterToContinue();
			return invoice;
		} else {
			return null;
		}
	}

	protected Invoice voidInvoice() {
		int invoiceId = getSystemHelper().askInt("\nInsert invoice id: ");
		if (invoiceId < 1) {
			getSystemHelper().println("\nInvalid id, must be a positive number");
			getSystemHelper().askPressEnterToContinue();
			return null;
		} else {
			Invoice invoiceToVoid = getManager().findById(invoiceId);
			if (invoiceToVoid == null) {
				getSystemHelper().println("\nThere is no invoice with the specified id");
				getSystemHelper().askPressEnterToContinue();
				return null;
			} else {
				if (getSystemHelper().askYesOrNo(
						"\nAre you sure that you want to void invoice " + invoiceId + "? (Y/N)")) {
					invoiceToVoid.setStatus(InvoiceStatus.VOIDED);
					try {
						getManager().update(invoiceToVoid);
					} catch (EntityException e) {
						logger.error("There was an error while updating the invoice", e);
						return null;
					}

					getSystemHelper().println("Invoice voided successfully");
					getSystemHelper().askPressEnterToContinue();
					return invoiceToVoid;
				} else {
					getSystemHelper().println("Invoice was not voided");
					getSystemHelper().askPressEnterToContinue();
					return null;
				}
			}
		}
	}

	private void printMenu() {
		getSystemHelper().println("\nOptions:");
		getSystemHelper().println("	[ 1 ] List all invoices");
		getSystemHelper().println("	[ 2 ] Create invoice");
		getSystemHelper().println("	[ 3 ] Load invoice");
		getSystemHelper().println("	[ 4 ] Void invoice");
		getSystemHelper().println("	[ 0 ] Log out");
		int option = getSystemHelper().askOption(
				"\nSelect an option: ",
				new int[] { LIST_ALL_INVOICES_OPTION, CREATE_INVOICE_OPTION, LOAD_INVOICE_OPTION,
						VOID_INVOICE_OPTION, LOG_OUT_OPTION },
				"Invalid option. Please select a valid option.");
		checkMainMenuOption(option);
	}

	private void checkMainMenuOption(final int option) {
		switch (option) {
		case LIST_ALL_INVOICES_OPTION:
			doCaseListAllInvoices();
			break;
		case CREATE_INVOICE_OPTION:
			doCaseCreateInvoice();
			break;
		case LOAD_INVOICE_OPTION:
			doCaseLoadInvoice();
			break;
		case VOID_INVOICE_OPTION:
			doCaseVoidInvoice();
			break;
		default:
			doCaseLogOut();
			break;
		}
	}

	private void doCaseListAllInvoices() {
		printAllInvoices();
		getSystemHelper().askPressEnterToContinue();
		printMenu();
	}

	private void doCaseCreateInvoice() {
		createInvoice();
		printMenu();
	}

	private void doCaseLoadInvoice() {
		loadInvoice();
		printMenu();
	}

	private void doCaseVoidInvoice() {
		voidInvoice();
		printMenu();
	}

	private void doCaseLogOut() {
		Employee returnedEmployee = logOut();
		if (returnedEmployee != null) {
			printLogInMenu();
		}
		printMenu();
	}

	private void expandChosenInvoice(final List<Invoice> invoices, final int option) {
		getSystemHelper().print("\n");
		Set<InvoiceProduct> invoiceProducts;
		for (Invoice invoice : invoices) {
			if (invoice.getId() == option) {
				getSystemHelper().println(
						String.format("%5s| %25s| %25s| %25s| %15s| %20s| %s", "Id",
								"Creation Date", "Employee Id Number", "Client Id Number",
								"Status", "Payment Method", "Total"));
				getSystemHelper().println(invoice.toString());
				invoiceProducts = invoice.getInvoiceProducts();
				getSystemHelper().print("\n");
				getSystemHelper().println(
						String.format("%15s | %30s | %15s | %10s | %10s | %s", "Product Code",
								"Product Name", "Department Code", "Quantity", "Price", "Total"));
				for (InvoiceProduct invoiceProduct : invoiceProducts) {
					getSystemHelper().println(invoiceProduct.toString());
				}
				return;
			}
		}
		return;
	}

	private Invoice createInvoice() {
		Invoice invoice = new Invoice(DEFAULT_ID, getCurrentEmployee(), new Date(),
				InvoiceStatus.IN_PROGRESS);
		Set<InvoiceProduct> productsSet = addProducts(invoice, new LinkedHashSet<InvoiceProduct>());
		invoice.setInvoiceProducts(productsSet);
		if (inProgressInvoice == null) {
			if (!getSystemHelper().askYesOrNo("Continue with the invoice - Add Client? Y/N")) {
				inProgressInvoice = invoice;
				return null;
			}
		}
		Client client = selectClient();
		invoice.setClient(client);
		if (inProgressInvoice == null) {
			if (!getSystemHelper().askYesOrNo("Continue with the invoice? Y/N")) {
				inProgressInvoice = invoice;
				return null;
			}
		}
		invoice.setPaymentMethod(PaymentMethod.CASH);

		BigDecimal totalPrice = commitPayment(invoice.getInvoiceProducts());
		if (totalPrice != null) {
			invoice.setTotal(totalPrice);
			return saveInvoice(invoice);
		} else {
			return null;
		}
	}

	private InvoiceProduct productToInvoiceProduct(final Product product, final int quantity,
			final Invoice invoice) {
		Department department = departmentManager.findByCode(product.getDepartmentCode());
		InvoiceProduct invoiceProduct = new InvoiceProduct(new InvoiceProductId(DEFAULT_ID,
				product.getCode()), invoice, department, product.getName(), quantity,
				product.getPrice());
		return invoiceProduct;
	}

	private Set<InvoiceProduct> addInvoiceProductToSet(
			final Set<InvoiceProduct> invoiceProductsSet, final InvoiceProduct invoiceProductToAdd) {
		for (InvoiceProduct invoiceProduct : invoiceProductsSet) {
			if (invoiceProduct.getId().getProductCode()
					.equalsIgnoreCase(invoiceProductToAdd.getId().getProductCode())) {
				invoiceProduct.setQuantity(invoiceProduct.getQuantity()
						+ invoiceProductToAdd.getQuantity());
				return invoiceProductsSet;
			}
		}
		invoiceProductsSet.add(invoiceProductToAdd);
		return invoiceProductsSet;
	}

	private Client selectUnregisteredClient(final String clientIdNumber) {
		getSystemHelper().println("There is no client registered with such Id Number");
		boolean yesOrNo = getSystemHelper().askYesOrNo(
				"\nWould you like to register the client? (Y/N)");
		if (yesOrNo) {
			Client client = new Client(clientIdNumber);
			client = registerClient(client);
			return client;
		} else {
			getSystemHelper().println("\nAnnonymous client selected successfully");
			return getAnnonymousClient();
		}
	}

	private Client registerClient(final Client clientWithIdNumber) {
		Client client = askForClientInformation(clientWithIdNumber);
		if (getSystemHelper().askYesOrNo(
				"\nAre you sure that you want to register client '" + client.getIdNumber()
						+ "'? (Y/N): ")) {
			try {
				getClientManager().create(client);
			} catch (EntityException e) {
				logger.error("There was an error while creating the client", e);
				return null;
			}
			getSystemHelper().println("\nClient registered succesfully");
		} else {
			getSystemHelper().println("\nClient was not registered");
			return getAnnonymousClient();
		}
		getSystemHelper().askPressEnterToContinue();
		return client;
	}

	private boolean validatePayment(final BigDecimal originalTotalPayment,
			final BigDecimal totalPrice) {
		BigDecimal totalPayment = originalTotalPayment;
		while (totalPayment.compareTo(totalPrice) < 0) {
			if (totalPayment.compareTo(new BigDecimal(0)) < 1) {
				getSystemHelper().println("\nInvalid value");
			} else {
				getSystemHelper().println("\nThe payment should be bigger than the total price");
			}
			if (getSystemHelper().askYesOrNo("\nInsert payment again? (Y/N)")) {
				totalPayment = getSystemHelper().askBigDecimal("\nInsert payment: ");
			} else {
				if (!getSystemHelper().askYesOrNo(
						"\nThis will cancel the invoice, are you sure? (Y/N)")) {
					totalPayment = getSystemHelper().askBigDecimal("\nInsert payment: ");
				} else {
					return false;
				}
			}
		}
		return true;
	}

	private BigDecimal calculateTotalPrice(final Set<InvoiceProduct> invoiceProductsList) {
		BigDecimal count = new BigDecimal(0);
		for (InvoiceProduct invoiceProduct : invoiceProductsList) {
			count = count.add(invoiceProduct.getPrice().multiply(
					(new BigDecimal(invoiceProduct.getQuantity()))));
		}
		return count;
	}

	private Invoice loadInvoice() {
		if (inProgressInvoice == null)
			getSystemHelper().println("\nThere no invoice in progress...");
		else {
			getSystemHelper().println(
					"\n================Loading In Progress Invoice=================");

			Invoice invoice = getInProgressInvoice();
			inProgressInvoice = null;
			if (getSystemHelper().askYesOrNo(
					"Would you want to add more products to the invoice? - Y/N")) {
				Set<InvoiceProduct> productsSet = addProducts(invoice, invoice.getInvoiceProducts());
				invoice.setInvoiceProducts(productsSet);
			}
			if (invoice.getClient() == null
					&& !getSystemHelper().askYesOrNo("Continue with the invoice - Add Client? Y/N")) {
				inProgressInvoice = invoice;
				return null;
			} else if (invoice.getClient() == null) {
				Client client = selectClient();
				invoice.setClient(client);
			}
			if (!getSystemHelper().askYesOrNo("Continue with the invoice - Proceed to pay? Y/N")) {
				inProgressInvoice = invoice;
				return null;
			}
			if (invoice.getPaymentMethod() == null) {
				invoice.setPaymentMethod(PaymentMethod.CASH);

				BigDecimal totalPrice = commitPayment(invoice.getInvoiceProducts());
				if (totalPrice != null) {
					invoice.setTotal(totalPrice);
					return saveInvoice(invoice);
				} else {
					return null;
				}
			}
		}
		getSystemHelper().askPressEnterToContinue();
		return null;
	}

	private Employee logOut() {
		if (getSystemHelper().askYesOrNo("\nAre you sure that you want to log out? (Y/N)")) {
			getSystemHelper().println("\nLogging out...");
			getSystemHelper().println("\nSuccessfully logged out");
			getSystemHelper().askPressEnterToContinue();
			return getCurrentEmployee();
		} else {
			getSystemHelper().println("\nLog out aborted");
			getSystemHelper().askPressEnterToContinue();
			return null;
		}
	}

	private Client askForClientInformation(final Client client) {
		client.setName(getSystemHelper().askString("\nClient Name: "));
		client.setContactNumber(getSystemHelper().askString("Client Contact Number: "));
		client.setEmail(getSystemHelper().askString("Client Email: "));
		client.setAddress(getSystemHelper().askString("Client Address: "));
		client.setId(DEFAULT_ID);
		return client;
	}
}
