package com.onboarding.pos.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.csv.ProductCsvManager;
import com.onboarding.pos.manager.hibernate.ClientHibernateManager;
import com.onboarding.pos.manager.hibernate.DepartmentHibernateManager;
import com.onboarding.pos.manager.hibernate.EmployeeHibernateManager;
import com.onboarding.pos.manager.hibernate.InvoiceHibernateManager;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.model.Invoice;
import com.onboarding.pos.model.InvoiceProduct;
import com.onboarding.pos.model.InvoiceProductId;
import com.onboarding.pos.model.InvoiceStatus;
import com.onboarding.pos.model.Product;
import com.onboarding.pos.util.SystemHelper;

public class PosModuleTest {

	private static final int POSITIVE_NUMBER = 1000;
	private static final int ANOTHER_POSITIVE_NUMBER = 2000;
	private static final int EXPECTED_QUANTITY = 3;;
	private static final BigDecimal BIG_DECIMAL = new BigDecimal(990);
	private static final BigDecimal BIGGER_DECIMAL = new BigDecimal(2000);
	private static final BigDecimal SMALLER_DECIMAL = new BigDecimal(490);
	private static final BigDecimal BIGGEST_DECIMAL = new BigDecimal(10000);
	private static final Object BIG_DECIMAL_SUM = new BigDecimal(5450);
	private static final String EMPLOYEE_ID_NUMBER = "11.111.111-1";
	private static final String EMPLOYEE_PASSWORD = "123456";

	private static final Calendar CALENDAR = new GregorianCalendar();;
	private static final Employee EMPLOYEE = new Employee(POSITIVE_NUMBER, "Employee",
			EMPLOYEE_ID_NUMBER, "8765432", "employee@email.com", "address", "position",
			EMPLOYEE_PASSWORD);
	private static final Product PRODUCT = new Product(POSITIVE_NUMBER, "1", "Description", "1",
			BIG_DECIMAL);
	private static final Product ANOTHER_PRODUCT = new Product(POSITIVE_NUMBER, "2", "Description",
			"2", SMALLER_DECIMAL);
	private static final Client CLIENT = new Client(POSITIVE_NUMBER, "Client", "12.345.678-9",
			"88888888", "client@email.com", "client address");
	private static final Invoice INVOICE = new Invoice(POSITIVE_NUMBER, EMPLOYEE, CLIENT,
			CALENDAR.getTime(), InvoiceStatus.IN_PROGRESS);
	private static final Invoice ANOTHER_INVOICE = new Invoice(ANOTHER_POSITIVE_NUMBER, EMPLOYEE,
			CLIENT, CALENDAR.getTime(), InvoiceStatus.IN_PROGRESS);
	private static final Invoice NULL_INVOICE = null;
	private static final Department DEPARTMENT = new Department(1, "1", "name");
	private static final InvoiceProduct FIRST_INVOICE_PRODUCT = new InvoiceProduct(
			new InvoiceProductId(1, "1"), DEPARTMENT, "Description", 3, SMALLER_DECIMAL);
	private static final InvoiceProduct SECOND_INVOICE_PRODUCT = new InvoiceProduct(
			new InvoiceProductId(1, "2"), DEPARTMENT, "AnotherDescription", 2, BIG_DECIMAL);
	private static final InvoiceProduct THIRD_INVOICE_PRODUCT = new InvoiceProduct(
			new InvoiceProductId(1, "3"), DEPARTMENT, "OneMoreDescription", 1, BIGGER_DECIMAL);
	private static final Set<InvoiceProduct> INVOICE_PRODUCTS_SET = new LinkedHashSet<InvoiceProduct>();
	private static final List<Invoice> INVOICES_LIST = new LinkedList<Invoice>();

	private PosModule posModule;
	private SystemHelper mockedSystemHelper;
	private InvoiceHibernateManager mockedInvoiceHibernateManager;
	private ProductCsvManager mockedProductCsvManager;
	private ClientHibernateManager mockedClientHibernateManager;
	private EmployeeHibernateManager mockedEmployeeHibernateManager;
	private DepartmentHibernateManager mockedDepartmentHibernateManager;

	@Before
	public void setUp() {
		INVOICE.setClient(CLIENT);

		INVOICE_PRODUCTS_SET.add(FIRST_INVOICE_PRODUCT);
		INVOICE_PRODUCTS_SET.add(SECOND_INVOICE_PRODUCT);
		INVOICE_PRODUCTS_SET.add(THIRD_INVOICE_PRODUCT);
		INVOICES_LIST.add(INVOICE);
		INVOICES_LIST.add(ANOTHER_INVOICE);

		mockedSystemHelper = mock(SystemHelper.class);
		mockedInvoiceHibernateManager = mock(InvoiceHibernateManager.class);
		mockedProductCsvManager = mock(ProductCsvManager.class);
		mockedClientHibernateManager = mock(ClientHibernateManager.class);
		mockedEmployeeHibernateManager = mock(EmployeeHibernateManager.class);
		mockedDepartmentHibernateManager = mock(DepartmentHibernateManager.class);

		posModule = new PosModule(mockedSystemHelper, mockedInvoiceHibernateManager,
				mockedProductCsvManager, mockedClientHibernateManager,
				mockedEmployeeHibernateManager, mockedDepartmentHibernateManager);
	}

	@Test
	public void testPrintAllInvoices() {
		when(mockedInvoiceHibernateManager.findAll()).thenReturn(INVOICES_LIST);
		when(mockedSystemHelper.askOption(anyString(), any(int[].class), anyString()))
				.thenReturn(0);
		posModule.printAllInvoices();
		verify(mockedInvoiceHibernateManager).findAll();
		verify(mockedSystemHelper).askOption(anyString(), any(int[].class), anyString());
	}

	@Test
	public void testAddProductsWithDifferentProductCodes() {
		when(mockedProductCsvManager.findByCode("1")).thenReturn(PRODUCT);
		when(mockedProductCsvManager.findByCode("2")).thenReturn(ANOTHER_PRODUCT);
		when(mockedDepartmentHibernateManager.findByCode("1")).thenReturn(DEPARTMENT);
		when(mockedDepartmentHibernateManager.findByCode("2")).thenReturn(DEPARTMENT);
		when(mockedSystemHelper.askString(anyString())).thenReturn("1", "2");
		when(mockedSystemHelper.askInt(anyString())).thenReturn(2, 1);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true, false);
		Set<InvoiceProduct> productsList = posModule.addProducts(INVOICE,
				new LinkedHashSet<InvoiceProduct>());
		verify(mockedProductCsvManager).findByCode("1");
		verify(mockedProductCsvManager).findByCode("2");
		verify(mockedDepartmentHibernateManager).findByCode("1");
		verify(mockedDepartmentHibernateManager).findByCode("2");
		verify(mockedSystemHelper, times(2)).askString(anyString());
		verify(mockedSystemHelper, times(2)).askInt(anyString());
		verify(mockedSystemHelper, times(2)).askYesOrNo(anyString());
		assertEquals(productsList.size(), 2);
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getId().getProductCode(), PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getQuantity(), 2);
		assertEquals(((InvoiceProduct) productsList.toArray()[1]).getId().getProductCode(), ANOTHER_PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[1]).getQuantity(), 1);
	}

	@Test
	public void testAddProductsWithRepeatedProductCode() {
		when(mockedProductCsvManager.findByCode("1")).thenReturn(PRODUCT);
		when(mockedDepartmentHibernateManager.findByCode("1")).thenReturn(DEPARTMENT);
		when(mockedSystemHelper.askString(anyString())).thenReturn("1");
		when(mockedSystemHelper.askInt(anyString())).thenReturn(2, 1);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true, false);
		Set<InvoiceProduct> productsList = posModule.addProducts(INVOICE,
				new LinkedHashSet<InvoiceProduct>());
		verify(mockedProductCsvManager, times(2)).findByCode("1");
		verify(mockedDepartmentHibernateManager, times(2)).findByCode("1");
		verify(mockedSystemHelper, times(2)).askString(anyString());
		verify(mockedSystemHelper, times(2)).askInt(anyString());
		verify(mockedSystemHelper, times(2)).askYesOrNo(anyString());
		assertEquals(productsList.size(), 1);
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getId().getProductCode(), PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getQuantity(), EXPECTED_QUANTITY);
	}

	@Test
	public void testAddProductsWithUnexistentProductCode() {
		when(mockedProductCsvManager.findByCode("1")).thenReturn(PRODUCT);
		when(mockedProductCsvManager.findByCode("unexistentCode")).thenReturn((Product) null);
		when(mockedDepartmentHibernateManager.findByCode("1")).thenReturn(DEPARTMENT);
		when(mockedSystemHelper.askString(anyString())).thenReturn("1", "unexistentCode");
		when(mockedSystemHelper.askInt(anyString())).thenReturn(2);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true, false);
		Set<InvoiceProduct> productsList = posModule.addProducts(INVOICE,
				new LinkedHashSet<InvoiceProduct>());
		verify(mockedProductCsvManager).findByCode("1");
		verify(mockedProductCsvManager).findByCode("unexistentCode");
		verify(mockedDepartmentHibernateManager).findByCode("1");
		verify(mockedSystemHelper, times(2)).askString(anyString());
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedSystemHelper, times(2)).askYesOrNo(anyString());
		assertEquals(productsList.size(), 1);
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getId().getProductCode(), PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getQuantity(), 2);
	}

	@Test
	public void testAddProductsWithInvalidProductCode() {
		when(mockedProductCsvManager.findByCode("1")).thenReturn(PRODUCT);
		when(mockedProductCsvManager.findByCode(" ")).thenReturn((Product) null);
		when(mockedDepartmentHibernateManager.findByCode("1")).thenReturn(DEPARTMENT);
		when(mockedSystemHelper.askString(anyString())).thenReturn("1", " ");
		when(mockedSystemHelper.askInt(anyString())).thenReturn(2);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true, false);
		Set<InvoiceProduct> productsList = posModule.addProducts(INVOICE,
				new LinkedHashSet<InvoiceProduct>());
		verify(mockedProductCsvManager).findByCode("1");
		verify(mockedProductCsvManager).findByCode(" ");
		verify(mockedDepartmentHibernateManager).findByCode("1");
		verify(mockedSystemHelper, times(2)).askString(anyString());
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedSystemHelper, times(2)).askYesOrNo(anyString());
		assertEquals(productsList.size(), 1);
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getId().getProductCode(), PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getQuantity(), 2);
	}

	@Test
	public void testAddProductsWithUnexistentFirstProductCode() {
		when(mockedProductCsvManager.findByCode("unexistentCode")).thenReturn((Product) null);
		when(mockedProductCsvManager.findByCode("1")).thenReturn(PRODUCT);
		when(mockedDepartmentHibernateManager.findByCode("1")).thenReturn(DEPARTMENT);
		when(mockedSystemHelper.askString(anyString())).thenReturn("unexistentCode", "1");
		when(mockedSystemHelper.askInt(anyString())).thenReturn(2);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		Set<InvoiceProduct> productsList = posModule.addProducts(INVOICE,
				new LinkedHashSet<InvoiceProduct>());
		verify(mockedProductCsvManager).findByCode("unexistentCode");
		verify(mockedProductCsvManager).findByCode("1");
		verify(mockedDepartmentHibernateManager).findByCode("1");
		verify(mockedSystemHelper, times(2)).askString(anyString());
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		assertEquals(productsList.size(), 1);
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getId().getProductCode(), PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getQuantity(), 2);
	}

	@Test
	public void testAddProductsWithInvalidFirstProductCode() {
		when(mockedProductCsvManager.findByCode(" ")).thenReturn((Product) null);
		when(mockedProductCsvManager.findByCode("1")).thenReturn(PRODUCT);
		when(mockedDepartmentHibernateManager.findByCode("1")).thenReturn(DEPARTMENT);
		when(mockedSystemHelper.askString(anyString())).thenReturn(" ", "1");
		when(mockedSystemHelper.askInt(anyString())).thenReturn(2);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		Set<InvoiceProduct> productsList = posModule.addProducts(INVOICE,
				new LinkedHashSet<InvoiceProduct>());
		verify(mockedProductCsvManager).findByCode(" ");
		verify(mockedProductCsvManager).findByCode("1");
		verify(mockedDepartmentHibernateManager).findByCode("1");
		verify(mockedSystemHelper, times(2)).askString(anyString());
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		assertEquals(productsList.size(), 1);
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getId().getProductCode(), PRODUCT.getCode());
		assertEquals(((InvoiceProduct) productsList.toArray()[0]).getQuantity(), 2);
	}

	@Test
	public void selectClientSuccessTest() throws EntityException {

		Client testClient = new Client();
		testClient.setIdNumber("11.234.567-k");
		when(mockedClientHibernateManager.findByIdNumber("11.234.567-k")).thenReturn(testClient);
		when(mockedSystemHelper.readLine()).thenReturn("11.234.567-k");
		Client returnedClient = posModule.selectClient();
		verify(mockedClientHibernateManager).findByIdNumber("11.234.567-k");
		verify(mockedSystemHelper).readLine();
		assertEquals(testClient, returnedClient);
	}

	@Test
	public void selectClientNullClientTest() throws EntityException {

		Client testClient = new Client();
		testClient.setIdNumber("11.234.567-k");
		when(mockedClientHibernateManager.findByIdNumber("00000000000")).thenReturn(null);
		when(mockedSystemHelper.readLine()).thenReturn("00000000000");
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		Client returnedClient = posModule.selectClient();
		verify(mockedClientHibernateManager).findByIdNumber("00000000000");
		verify(mockedSystemHelper).readLine();
		verify(mockedSystemHelper).askYesOrNo(anyString());
		assertNull(returnedClient);
	}

	@Test
	public void selectClientIdNumberNotFoundTest() throws EntityException {

		Client testClient = new Client();
		testClient.setIdNumber("11.234.567-k");
		when(mockedClientHibernateManager.findByIdNumber("11.234.567-k")).thenReturn(testClient);
		when(mockedSystemHelper.readLine()).thenReturn("00000000000");
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		Client returnedClient = posModule.selectClient();
		verify(mockedClientHibernateManager).findByIdNumber("00000000000");
		verify(mockedSystemHelper).readLine();
		verify(mockedSystemHelper).askYesOrNo(anyString());
		assertNull(returnedClient);
	}

	@Test
	public void testCommitPaymentSuccess() {
		when(mockedSystemHelper.askBigDecimal(anyString())).thenReturn(BIGGEST_DECIMAL);
		BigDecimal totalPrice = posModule.commitPayment(INVOICE_PRODUCTS_SET);
		verify(mockedSystemHelper).askBigDecimal(anyString());
		assertEquals(totalPrice, BIG_DECIMAL_SUM);
	}

	@Test
	public void testCommitPaymentWithTotalBiggerThanPayWithCorrecting() {
		when(mockedSystemHelper.askBigDecimal(anyString())).thenReturn(BIGGER_DECIMAL,
				BIGGEST_DECIMAL);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		BigDecimal totalPrice = posModule.commitPayment(INVOICE_PRODUCTS_SET);
		verify(mockedSystemHelper, times(2)).askBigDecimal(anyString());
		assertEquals(totalPrice, BIG_DECIMAL_SUM);
	}

	@Test
	public void testCommitPaymentWithZeroOrNegativePayWithCorrecting() {
		when(mockedSystemHelper.askBigDecimal(anyString())).thenReturn(new BigDecimal(0),
				BIGGEST_DECIMAL);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		BigDecimal totalPrice = posModule.commitPayment(INVOICE_PRODUCTS_SET);
		verify(mockedSystemHelper, times(2)).askBigDecimal(anyString());
		assertEquals(totalPrice, BIG_DECIMAL_SUM);
	}

	@Test
	public void testCommitPaymentWithTotalBiggerThanPayWithoutCorrecting() {
		when(mockedSystemHelper.askBigDecimal(anyString())).thenReturn(BIGGER_DECIMAL);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false, true);
		BigDecimal totalPrice = posModule.commitPayment(INVOICE_PRODUCTS_SET);
		verify(mockedSystemHelper).askBigDecimal(anyString());
		assertNull(totalPrice);
	}

	@Test
	public void testCommitPaymentWithZeroOrNegativePayWithoutCorrecting() {
		when(mockedSystemHelper.askBigDecimal(anyString())).thenReturn(new BigDecimal(0));
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false, true);
		BigDecimal totalPrice = posModule.commitPayment(INVOICE_PRODUCTS_SET);
		verify(mockedSystemHelper).askBigDecimal(anyString());
		assertNull(totalPrice);
	}

	@Test
	public void saveInvoiceSuccessTest() throws EntityException {

		when(mockedInvoiceHibernateManager.create(INVOICE)).thenReturn(INVOICE);
		when(mockedClientHibernateManager.findByIdNumber(CLIENT.getIdNumber())).thenReturn(
				INVOICE.getClient());
		Invoice returnedInvoice = posModule.saveInvoice(INVOICE);
		verify(mockedInvoiceHibernateManager).create(any(Invoice.class));
		verify(mockedClientHibernateManager, times(1)).findByIdNumber(anyString());

		assertEquals(INVOICE, returnedInvoice);
	}

	@Test
	public void saveInvoiceNullInvoiceTest() throws EntityException {
		Invoice returnedInvoice = posModule.saveInvoice(NULL_INVOICE);
		verify(mockedInvoiceHibernateManager, never()).create(any(Invoice.class));
		verify(mockedClientHibernateManager, never()).findByIdNumber(anyString());
		assertNull(returnedInvoice);
	}

	@Test
	public void testVoidInvoiceSuccess() throws EntityException {
		when(mockedSystemHelper.askInt(anyString())).thenReturn(POSITIVE_NUMBER);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		when(mockedInvoiceHibernateManager.findById(POSITIVE_NUMBER)).thenReturn(INVOICE);
		when(mockedInvoiceHibernateManager.update(INVOICE)).thenReturn(INVOICE);
		Invoice voidedInvoice = posModule.voidInvoice();
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedInvoiceHibernateManager).findById(POSITIVE_NUMBER);
		verify(mockedInvoiceHibernateManager).update(INVOICE);
		assertEquals(voidedInvoice, INVOICE);
	}

	@Test
	public void testVoidInvoiceWithUnexistentId() throws EntityException {
		when(mockedSystemHelper.askInt(anyString())).thenReturn(POSITIVE_NUMBER);
		when(mockedInvoiceHibernateManager.findById(POSITIVE_NUMBER)).thenReturn(null);
		Invoice voidedInvoice = posModule.voidInvoice();
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedInvoiceHibernateManager).findById(POSITIVE_NUMBER);
		verify(mockedSystemHelper, never()).askYesOrNo(anyString());
		verify(mockedInvoiceHibernateManager, never()).update(any(Invoice.class));
		assertNull(voidedInvoice);
	}

	@Test
	public void testVoidInvoiceWithnvalidId() throws EntityException {
		when(mockedSystemHelper.askInt(anyString())).thenReturn(0);
		Invoice voidedInvoice = posModule.voidInvoice();
		verify(mockedSystemHelper).askInt(anyString());
		verify(mockedSystemHelper, never()).askYesOrNo(anyString());
		verify(mockedInvoiceHibernateManager, never()).findById(anyInt());
		verify(mockedInvoiceHibernateManager, never()).update(any(Invoice.class));
		assertNull(voidedInvoice);
	}
}
