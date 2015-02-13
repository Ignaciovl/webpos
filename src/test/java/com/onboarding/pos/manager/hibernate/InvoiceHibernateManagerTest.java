package com.onboarding.pos.manager.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.onboarding.pos.model.Client;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.model.Invoice;
import com.onboarding.pos.model.InvoiceProduct;
import com.onboarding.pos.model.InvoiceProductId;
import com.onboarding.pos.model.InvoiceStatus;
import com.onboarding.pos.model.PaymentMethod;

public class InvoiceHibernateManagerTest {

	private InvoiceHibernateManager invoiceManager;
	private SessionFactory sessionFactoryMock;
	private Session sessionMock;
	private Transaction transactionMock;
	private Query queryMock;
	
	@Before
	public void setUp() throws Exception {
		sessionFactoryMock = mock(SessionFactory.class);
		sessionMock = mock(Session.class);
		transactionMock = mock(Transaction.class);
		queryMock = mock(Query.class);

		when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
		when(sessionMock.beginTransaction()).thenReturn(transactionMock);

		invoiceManager = new InvoiceHibernateManager(sessionFactoryMock);
	}

	@After
	public void tearDown() throws Exception {
		sessionFactoryMock = null;
		sessionMock = null;
		transactionMock = null;
		queryMock = null;
		invoiceManager = null;
	}

	@Test
	public void testConstructInvoiceHibernateManager() throws Exception {
		assertNotNull(invoiceManager);
	}
	
	@Test
	public void testInvoiceHibernateManagerIsAHibernateManager() throws Exception {
		assertTrue(invoiceManager instanceof AbstractHibernateManager);
	}
	
	@Test
	public void testCountAllSuccessfully() throws Exception {
		long expectedCount = 999;

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenReturn(expectedCount);

		long count = invoiceManager.countAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).uniqueResult();
		assertEquals(expectedCount, count);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCountAllCatchingQuerySyntaxException() throws Exception {
		long expectedCount = 0;

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		long resultCount = invoiceManager.countAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).uniqueResult();
		assertEquals(expectedCount, resultCount);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCountAllCatchingNonUniqueResultException() throws Exception {
		long expectedCount = 0;

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenThrow(NonUniqueResultException.class);

		long resultCount = invoiceManager.countAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).uniqueResult();
		assertEquals(expectedCount, resultCount);
	}
	
	@Test
	public void testFindAllSuccessfully() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		
		List<Invoice> expectedList = new ArrayList<Invoice>();
		expectedList.add(new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(2, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(3, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllCatchingQuerySyntaxException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Invoice> resultList = invoiceManager.findAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllCatchingHibernateException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Invoice> resultList = invoiceManager.findAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByIdSuccessfully() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		
		Invoice expectedInvoice = new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS);

		when(sessionMock.get(Invoice.class, 1)).thenReturn(expectedInvoice);

		Invoice resultInvoice = invoiceManager.findById(1);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).get(Invoice.class, 1);
		assertEquals(expectedInvoice, resultInvoice);
	}

	@Test
	public void testFindByIdNonExistingId() throws Exception {
		Invoice expectedInvoice = null;

		when(sessionMock.get(Invoice.class, 1000)).thenReturn(expectedInvoice);

		Invoice resultInvoice = invoiceManager.findById(1000);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).get(Invoice.class, 1000);
		assertEquals(expectedInvoice, resultInvoice);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByIdCatchingQuerySyntaxException() throws Exception {
		Invoice expectedInvoice = null;

		when(sessionMock.get(Invoice.class, 0)).thenThrow(QuerySyntaxException.class);
		
		Invoice resultInvoice = invoiceManager.findById(0);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).get(Invoice.class, 0);
		assertEquals(expectedInvoice, resultInvoice);
	}

	@Test
	public void testFindByClientIdNumberExistingInvoices() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		when(clientMock.getIdNumber()).thenReturn("0.000.000-0");
		
		List<Invoice> expectedList = new ArrayList<Invoice>();
		expectedList.add(new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(2, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(3, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByClientIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq("0.000.000-0"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@Test
	public void testFindByClientIdNumberNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByClientIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq("0.000.000-0"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByClientIdNumberNullNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByClientIdNumber(null);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), isNull());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByClientIdNumberCatchingQuerySyntaxException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Invoice> resultList = invoiceManager.findByClientIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).setString(anyString(), eq("0.000.000-0"));
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByClientIdNumberCatchingHibernateException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Invoice> resultList = invoiceManager.findByClientIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq("0.000.000-0"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByEmployeeIdNumberExistingInvoices() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		when(employeeMock.getIdNumber()).thenReturn("0.000.000-0");
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		
		List<Invoice> expectedList = new ArrayList<Invoice>();
		expectedList.add(new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(2, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(3, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByEmployeeIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq("0.000.000-0"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@Test
	public void testFindByEmployeeIdNumberNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByEmployeeIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq("0.000.000-0"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByEmployeeIdNumberNullNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByEmployeeIdNumber(null);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), isNull());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByEmployeeIdNumberCatchingQuerySyntaxException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Invoice> resultList = invoiceManager.findByEmployeeIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).setString(anyString(), eq("0.000.000-0"));
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByEmployeeIdNumberCatchingHibernateException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Invoice> resultList = invoiceManager.findByEmployeeIdNumber("0.000.000-0");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq("0.000.000-0"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByPaymentMethodExistingInvoices() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		
		List<Invoice> expectedList = new ArrayList<Invoice>();
		expectedList.add(new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS, PaymentMethod.CASH, new BigDecimal(10000)));
		expectedList.add(new Invoice(2, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS, PaymentMethod.CASH, new BigDecimal(20000)));
		expectedList.add(new Invoice(3, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS, PaymentMethod.CASH, new BigDecimal(30000)));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByPaymentMethod(PaymentMethod.CASH);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq(PaymentMethod.CASH));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByPaymentMethodNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByPaymentMethod(PaymentMethod.CASH);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq(PaymentMethod.CASH));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByPaymentMethodNullExistingInvoices() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		
		List<Invoice> expectedList = new ArrayList<Invoice>();
		expectedList.add(new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(2, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(3, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByPaymentMethod(null);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), isNull());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByPaymentMethodNullNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByPaymentMethod(null);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), isNull());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByPaymentMethodCatchingQuerySyntaxException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Invoice> resultList = invoiceManager.findByPaymentMethod(PaymentMethod.CASH);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).setParameter(anyString(), eq(PaymentMethod.CASH));
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByPaymentMethodCatchingHibernateException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Invoice> resultList = invoiceManager.findByPaymentMethod(PaymentMethod.CASH);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq(PaymentMethod.CASH));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByStatusExistingInvoices() throws Exception {
		Employee employeeMock = mock(Employee.class);
		when(employeeMock.getId()).thenReturn(1);
		
		Client clientMock = mock(Client.class);
		when(clientMock.getId()).thenReturn(1);
		
		List<Invoice> expectedList = new ArrayList<Invoice>();
		expectedList.add(new Invoice(1, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(2, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));
		expectedList.add(new Invoice(3, employeeMock, clientMock, new Date(), InvoiceStatus.IN_PROGRESS));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByStatus(InvoiceStatus.IN_PROGRESS);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq(InvoiceStatus.IN_PROGRESS));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByStatusNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByStatus(InvoiceStatus.COMPLETED);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq(InvoiceStatus.COMPLETED));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testFindByStatusNullNonExistingInvoices() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Invoice> resultList = invoiceManager.findByStatus(null);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), isNull());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByStatusCatchingQuerySyntaxException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Invoice> resultList = invoiceManager.findByStatus(InvoiceStatus.IN_PROGRESS);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).setParameter(anyString(), eq(InvoiceStatus.IN_PROGRESS));
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByStatusCatchingHibernateException() throws Exception {
		List<Invoice> expectedList = new ArrayList<Invoice>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Invoice> resultList = invoiceManager.findByStatus(InvoiceStatus.IN_PROGRESS);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setParameter(anyString(), eq(InvoiceStatus.IN_PROGRESS));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testCreateInvoiceWithoutProducts() throws Exception {
		Date created = Calendar.getInstance().getTime();
		
		Employee employee = new Employee();
		employee.setId(1);
		
		Client client = new Client();
		client.setId(1);
		
		Invoice newInvoice = new Invoice(0, employee, client, created, InvoiceStatus.IN_PROGRESS);
		Invoice expectedInvoice = new Invoice(1, employee, client, created, InvoiceStatus.IN_PROGRESS);
		
		when(sessionMock.save(newInvoice)).thenAnswer(new Answer<Serializable>() {
			@Override
			public Serializable answer(InvocationOnMock invocation) throws Throwable {
				invocation.getArgumentAt(0, Invoice.class).setId(1);
				return 1;
			}
		});
		
		Invoice resultInvoice = invoiceManager.create(newInvoice);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).save(newInvoice);
		verify(transactionMock).commit();
		assertEquals(expectedInvoice, resultInvoice);
	}
	
	@Test
	public void testCreateInvoiceWithProducts() throws Exception {
		Date created = Calendar.getInstance().getTime();
		
		Employee employee = new Employee();
		employee.setId(1);
		
		Client client = new Client();
		client.setId(1);
		
		Invoice newInvoice = new Invoice(0, employee, client, created, InvoiceStatus.IN_PROGRESS);
		Set<InvoiceProduct> newInvoiceProducts = new HashSet<InvoiceProduct>();
		newInvoiceProducts.add(new InvoiceProduct(new InvoiceProductId(0, "00000001"), newInvoice, new Department(1), "product1", 1, new BigDecimal(10000)));
		newInvoiceProducts.add(new InvoiceProduct(new InvoiceProductId(0, "00000002"), newInvoice, new Department(2), "product2", 2, new BigDecimal(20000)));
		newInvoiceProducts.add(new InvoiceProduct(new InvoiceProductId(0, "00000003"), newInvoice, new Department(3), "product3", 3, new BigDecimal(30000)));
		newInvoice.setInvoiceProducts(newInvoiceProducts);
		
		Invoice expectedInvoice = new Invoice(1, employee, client, created, InvoiceStatus.IN_PROGRESS);
		Set<InvoiceProduct> expectedInvoiceProducts = new HashSet<InvoiceProduct>();
		expectedInvoiceProducts.add(new InvoiceProduct(new InvoiceProductId(1, "00000001"), expectedInvoice, new Department(1), "product1", 1, new BigDecimal(10000)));
		expectedInvoiceProducts.add(new InvoiceProduct(new InvoiceProductId(1, "00000002"), expectedInvoice, new Department(2), "product2", 2, new BigDecimal(20000)));
		expectedInvoiceProducts.add(new InvoiceProduct(new InvoiceProductId(1, "00000003"), expectedInvoice, new Department(3), "product3", 3, new BigDecimal(30000)));
		expectedInvoice.setInvoiceProducts(expectedInvoiceProducts);
		
		when(sessionMock.save(newInvoice)).thenAnswer(new Answer<Serializable>() {
			@Override
			public Serializable answer(InvocationOnMock invocation) throws Throwable {
				Invoice invoice = invocation.getArgumentAt(0, Invoice.class);
				invoice.setId(1);
				for (InvoiceProduct invoiceProduct : invoice.getInvoiceProducts()) {
					invoiceProduct.getId().setInvoiceId(invoice.getId());
					invoiceProduct.setInvoice(invoice);
				}
				return invoice.getId();
			}
		});
		
		Invoice resultInvoice = invoiceManager.create(newInvoice);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).save(newInvoice);
		verify(transactionMock).commit();
		assertEquals(expectedInvoice, resultInvoice);
		assertEquals(expectedInvoiceProducts, newInvoiceProducts);
	}
	
}
