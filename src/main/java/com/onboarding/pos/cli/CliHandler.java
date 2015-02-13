package com.onboarding.pos.cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.Validate;
import org.hibernate.SessionFactory;

import com.onboarding.pos.csv.ClientCsvReader;
import com.onboarding.pos.csv.ClientCsvWriter;
import com.onboarding.pos.csv.EmployeeCsvReader;
import com.onboarding.pos.csv.EmployeeCsvWriter;
import com.onboarding.pos.csv.ProductCsvReader;
import com.onboarding.pos.csv.ProductCsvWriter;
import com.onboarding.pos.manager.csv.ClientCsvManager;
import com.onboarding.pos.manager.csv.EmployeeCsvManager;
import com.onboarding.pos.manager.csv.ProductCsvManager;
import com.onboarding.pos.manager.hibernate.ClientHibernateManager;
import com.onboarding.pos.manager.hibernate.DepartmentHibernateManager;
import com.onboarding.pos.manager.hibernate.EmployeeHibernateManager;
import com.onboarding.pos.manager.hibernate.InvoiceHibernateManager;
import com.onboarding.pos.module.ClientModule;
import com.onboarding.pos.module.DepartmentModule;
import com.onboarding.pos.module.InvoiceModule;
import com.onboarding.pos.module.PosModule;
import com.onboarding.pos.module.ProductModule;
import com.onboarding.pos.module.csv.EmployeeCsvModule;
import com.onboarding.pos.module.hibernate.EmployeeHibernateModule;
import com.onboarding.pos.util.HibernateUtil;
import com.onboarding.pos.util.SystemHelper;

public class CliHandler {

	private static final String HELP = "help";
	private static final String INIT = "init";
	private static final String INIT_DEPARMENTS = "departments";
	private static final String INIT_CLIENTS = "clients";
	private static final String INIT_EMPLOYEES = "employees";
	private static final String INIT_INVOICES = "invoices";
	private static final String INIT_POS = "pos";
	private static final String INIT_PRODUCTS = "products";

	private SystemHelper systemHelper;

	public CliHandler(final SystemHelper givenSystemHelper) {
		setSystemHelper(givenSystemHelper);
	}

	public void start(final String[] args) {
		Options options = buildOptions();
		CommandLineParser parser = new BasicParser();

		try {
			CommandLine line = parser.parse(options, args);

			if (line.hasOption(INIT)) {
				init(line.getOptionValue(INIT));
			} else {
				help(options);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SystemHelper getSystemHelper() {
		return systemHelper;
	}

	public void setSystemHelper(final SystemHelper newSystemHelper) {
		Validate.isTrue(newSystemHelper != null, "SystemHelper cannot be null");
		this.systemHelper = newSystemHelper;
	}

	public SessionFactory getSessionFactory() {
		return HibernateUtil.getSessionFactory();
	}

	@SuppressWarnings("static-access")
	private Options buildOptions() {
		Options options = new Options();
		String initDescription =
				"Initialize the specified module.\nModules available: "
				+ INIT_CLIENTS
				+ ", "
				+ INIT_DEPARMENTS
				+ ", "
				+ INIT_EMPLOYEES
				+ ", " + INIT_INVOICES + ", " + INIT_POS + ", " + INIT_PRODUCTS;
		Option help = new Option(HELP, "Prints out the help message");
		Option init = OptionBuilder.withArgName("module").hasArg()
				.withType(String.class).withDescription(initDescription)
				.create(INIT);

		options.addOption(help);
		options.addOption(init);

		return options;
	}

	private void help(final Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("modelpos", options, true);
	}

	private void init(final String module) {
		if (INIT_DEPARMENTS.equals(module)) {
			initDepartmentModule();
		} else if (INIT_CLIENTS.equals(module)) {
			initClientModule();
		} else if (INIT_EMPLOYEES.equals(module)) {
			initEmployeeModule();
		} else if (INIT_INVOICES.equals(module)) {
			initInvoiceModule();
		} else if (INIT_POS.equals(module)) {
			initPosModule();
		} else if (INIT_PRODUCTS.equals(module)) {
			initProductModule();
		} else {
			help(buildOptions());
		}
	}

	private void initClientModule() {
		printClientModuleHeader();
		int option = getSystemHelper().askOption("\nSelect an option: ",
				new int[] { 1, 2 },
				"Invalid option. Please select a valid option.");
		while (true) {
			switch (option) {
			case 1:
				initClientCsvModule();
				break;
			case 2:
				initClientHibernateModule();
				break;
			default:
				continue;
			}
		}
	}

	private void printClientModuleHeader() {
		getSystemHelper().println(
				"===================================================");
		getSystemHelper().println("	Welcome to modelPOS Client Module	");
		getSystemHelper().println(
				"===================================================");
		getSystemHelper().println("\nOptions:");
		getSystemHelper().println("	[ 1 ] Work with Csv Files");
		getSystemHelper().println("	[ 2 ] Work with Hibernate");
	}

	private void initClientCsvModule() {
		ClientCsvReader clientCsvReader = new ClientCsvReader(getSystemHelper()
				.getAppProperty("clientsCsv"));
		ClientCsvWriter clientCsvWriter = new ClientCsvWriter(getSystemHelper()
				.getAppProperty("clientsCsv"));
		ClientModule clientModule = new ClientModule(
				getSystemHelper(), new ClientCsvManager(clientCsvReader,
						clientCsvWriter));
		getSystemHelper().println("\n===================================================");
		getSystemHelper().println("	      Working with CSV files");
		getSystemHelper().println("===================================================");
		clientModule.init();
	}

	private void initClientHibernateModule() {
		ClientModule clientModule = new ClientModule(
				getSystemHelper(), new ClientHibernateManager(
						getSessionFactory()));
		getSystemHelper().println("\n===================================================");
		getSystemHelper().println("	      Working with Hibernate");
		getSystemHelper().println("===================================================");
		clientModule.init();
	}

	private void initDepartmentModule() {
		DepartmentModule departmentModule = new DepartmentModule(
				getSystemHelper(), new DepartmentHibernateManager(
						getSessionFactory()));
		departmentModule.init();
	}

	private void initEmployeeModule() {
		printEmployeeModuleHeader();
		int option = getSystemHelper().askOption("\nSelect an option: ",
				new int[] { 1, 2 },
				"Invalid option. Please select a valid option.");
		while (true) {
			switch (option) {
			case 1:
				initEmployeeCsvModule();
				break;
			case 2:
				initEmployeeHibernateModule();
				break;
			default:
				continue;
			}
		}
	}

	private void printEmployeeModuleHeader() {
		getSystemHelper().println(
				"===================================================");
		getSystemHelper().println("	Welcome to modelPOS Employee Module	");
		getSystemHelper().println(
				"===================================================");
		getSystemHelper().println("\nOptions:");
		getSystemHelper().println("	[ 1 ] Work with Csv Files");
		getSystemHelper().println("	[ 2 ] Work with Hibernate");
	}

	private void initEmployeeHibernateModule() {
		EmployeeHibernateManager empHibMan = new EmployeeHibernateManager(
				getSessionFactory());
		EmployeeHibernateModule empHibMod = new EmployeeHibernateModule(
				systemHelper, empHibMan);
		empHibMod.init();
	}

	private void initEmployeeCsvModule() {
		EmployeeCsvReader employeeCsvReader = new EmployeeCsvReader(
				getSystemHelper().getAppProperty("clientsCsv"));
		EmployeeCsvWriter employeeCsvWriter = new EmployeeCsvWriter(
				getSystemHelper().getAppProperty("clientsCsv"));
		EmployeeCsvModule employeeCsvModule = new EmployeeCsvModule(
				getSystemHelper(), new EmployeeCsvManager(employeeCsvReader,
						employeeCsvWriter));
		employeeCsvModule.init();
	}

	private void initInvoiceModule() {
		InvoiceModule invoiceModule = new InvoiceModule(getSystemHelper(),
				new InvoiceHibernateManager(getSessionFactory()));
		invoiceModule.init();
	}

	private void initProductModule() {
		String productCsvFilePath = getSystemHelper().getAppProperty(
				"productsCsv");
		ProductCsvReader csvReader = new ProductCsvReader(productCsvFilePath);
		ProductCsvWriter csvWriter = new ProductCsvWriter(productCsvFilePath);
		ProductCsvManager productManager = new ProductCsvManager(csvReader,
				csvWriter);
		ProductModule productModule = new ProductModule(getSystemHelper(),
				productManager, new DepartmentHibernateManager(
						getSessionFactory()));
		productModule.init();
	}

	private void initPosModule() {
		InvoiceHibernateManager invoiceManager = new InvoiceHibernateManager(
				getSessionFactory());
		String productCsvFilePath = getSystemHelper().getAppProperty(
				"productsCsv");
		ProductCsvManager productManager = new ProductCsvManager(
				new ProductCsvReader(productCsvFilePath), new ProductCsvWriter(
						productCsvFilePath));
		ClientHibernateManager clientManager = new ClientHibernateManager(
				getSessionFactory());
		EmployeeHibernateManager employeeManager = new EmployeeHibernateManager(
				getSessionFactory());
		DepartmentHibernateManager departmentManager =
				new DepartmentHibernateManager(
				getSessionFactory());
		PosModule posModule = new PosModule(getSystemHelper(), invoiceManager,
				productManager, clientManager, employeeManager,
				departmentManager);
		posModule.init();
	}
}
