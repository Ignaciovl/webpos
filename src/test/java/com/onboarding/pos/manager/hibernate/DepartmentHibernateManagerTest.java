package com.onboarding.pos.manager.hibernate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.model.Department;

public class DepartmentHibernateManagerTest {

	private DepartmentHibernateManager departmentManager;
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

		departmentManager = new DepartmentHibernateManager(sessionFactoryMock);
	}

	@After
	public void tearDown() throws Exception {
		sessionFactoryMock = null;
		sessionMock = null;
		transactionMock = null;
		queryMock = null;
		departmentManager = null;
	}
	
	@Test
	public void testCountAllSuccessfully() {
		long expectedCount = 999;

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenReturn(expectedCount);

		long count = departmentManager.countAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).uniqueResult();
		assertEquals(expectedCount, count);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCountAllCatchingQuerySyntaxException() {
		long expectedCount = 0;

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		long resultCount = departmentManager.countAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).uniqueResult();
		assertEquals(expectedCount, resultCount);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCountAllCatchingNonUniqueResultException() {
		long expectedCount = 0;

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenThrow(NonUniqueResultException.class);

		long resultCount = departmentManager.countAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).uniqueResult();
		assertEquals(expectedCount, resultCount);
	}

	@Test
	public void testFindAllSuccessfully() {
		List<Department> expectedList = new ArrayList<Department>();
		expectedList.add(new Department(1, "01", "Department 1"));
		expectedList.add(new Department(2, "02", "Department 2"));
		expectedList.add(new Department(3, "03", "Department 3"));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Department> resultList = departmentManager.findAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllCatchingQuerySyntaxException() {
		List<Department> expectedList = new ArrayList<Department>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Department> resultList = departmentManager.findAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllCatchingHibernateException() {
		List<Department> expectedList = new ArrayList<Department>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Department> resultList = departmentManager.findAll();
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@Test
	public void testFindByIdSuccessfully() {
		Department expectedDepartment = new Department(1, "01", "Department 1");

		when(sessionMock.get(Department.class, 1)).thenReturn(expectedDepartment);

		Department resultDepartment = departmentManager.findById(1);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).get(Department.class, 1);
		assertEquals(expectedDepartment, resultDepartment);
	}

	@Test
	public void testFindByIdNonExistingId() {
		Department expectedDepartment = null;

		when(sessionMock.get(Department.class, 1000)).thenReturn(expectedDepartment);

		Department resultDepartment = departmentManager.findById(1000);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).get(Department.class, 1000);
		assertEquals(expectedDepartment, resultDepartment);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByIdCatchingHibernateException() {
		Department expectedDepartment = null;

		when(sessionMock.get(Department.class, 0)).thenThrow(HibernateException.class);

		Department resultDepartment = departmentManager.findById(0);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).get(Department.class, 0);
		assertEquals(expectedDepartment, resultDepartment);
	}

	@Test
	public void testFindByCodeSuccessfully() {
		Department expectedDepartment = new Department(1, "01", "Department 1");

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenReturn(expectedDepartment);

		Department resultDepartment = departmentManager.findByCode("01");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setString(anyString(), eq("01"));
		verify(queryMock).uniqueResult();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@Test
	public void testFindByCodeNonExistingCode() {
		Department expectedDepartment = null;

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenReturn(expectedDepartment);

		Department resultDepartment = departmentManager.findByCode("00");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setString(anyString(), eq("00"));
		verify(queryMock).uniqueResult();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByCodeCatchingQuerySyntaxException() {
		Department expectedDepartment = null;

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		Department resultDepartment = departmentManager.findByCode("01");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).setString(anyString(), eq("01"));
		verify(queryMock, never()).uniqueResult();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByCodeCatchingNonUniqueResultException() {
		Department expectedDepartment = null;

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenThrow(NonUniqueResultException.class);

		Department resultDepartment = departmentManager.findByCode("01");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setString(anyString(), eq("01"));
		verify(queryMock).uniqueResult();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@Test
	public void testFindByNameSuccessfully() {
		List<Department> expectedList = new ArrayList<Department>();
		expectedList.add(new Department(1, "01", "Department 1"));
		expectedList.add(new Department(2, "02", "Department 2"));
		expectedList.add(new Department(3, "03", "Department 3"));

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Department> resultList = departmentManager.findByName("Department");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setString(anyString(), eq("%Department%"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@Test
	public void testFindByNameNonExistingName() {
		List<Department> expectedList = new ArrayList<Department>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(expectedList);

		List<Department> resultList = departmentManager.findByName("Department");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setString(anyString(), eq("%Department%"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByNameCatchingQuerySyntaxException() {
		List<Department> expectedList = new ArrayList<Department>();

		when(sessionMock.createQuery(anyString())).thenThrow(QuerySyntaxException.class);

		List<Department> resultList = departmentManager.findByName("Department");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock, never()).setString(anyString(), eq("%Department%"));
		verify(queryMock, never()).list();
		assertEquals(expectedList, resultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByNameCatchingHibernateException() {
		List<Department> expectedList = new ArrayList<Department>();

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(queryMock.list()).thenThrow(HibernateException.class);

		List<Department> resultList = departmentManager.findByName("Department");
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).createQuery(anyString());
		verify(queryMock).setString(anyString(), eq("%Department%"));
		verify(queryMock).list();
		assertEquals(expectedList, resultList);
	}
	
	@Test
	public void testCreate() throws EntityException {
		Department newDepartment = new Department("01", "Department 1");
		Department expectedDepartment = new Department(1, "01", "Department 1");

		when(sessionMock.save(newDepartment)).thenAnswer(new Answer<Serializable>() {
			@Override
			public Serializable answer(InvocationOnMock invocation) throws Throwable {
				invocation.getArgumentAt(0, Department.class).setId(1);
				return 1;
			}
		});

		Department resultDepartment = departmentManager.create(newDepartment);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).save(newDepartment);
		verify(transactionMock).commit();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = EntityException.class)
	public void testCreateExpectingEntityException() throws EntityException {
		Department newDepartment = new Department("01", "Department 1");

		when(sessionMock.save(newDepartment)).thenThrow(HibernateException.class);

		departmentManager.create(newDepartment);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateVerifyingRollback() throws EntityException {
		Department newDepartment = new Department("01", "Department 1");

		when(sessionMock.save(newDepartment)).thenThrow(HibernateException.class);

		try {
			departmentManager.create(newDepartment);
			fail();
		} catch (EntityException e) {
			verify(sessionFactoryMock).getCurrentSession();
			verify(sessionMock).save(newDepartment);
			verify(transactionMock, atLeastOnce()).rollback();
		}
	}

	@Test
	public void testUpdate() throws EntityException {
		Department expectedDepartment = new Department(1, "01", "Department Updated");

		Department resultDepartment = departmentManager.update(expectedDepartment);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).update(expectedDepartment);
		verify(transactionMock).commit();
		assertEquals(expectedDepartment, resultDepartment);
	}
	
	@Test(expected = EntityException.class)
	public void testUpdateExpectingEntityException() throws EntityException {
		Department expectedDepartment = new Department(1, "01", "Department Updated");

		doThrow(HibernateException.class).when(sessionMock).update(expectedDepartment);

		departmentManager.update(expectedDepartment);
	}
	
	@Test
	public void testUpdateVerifyingRollback() throws EntityException {
		Department expectedDepartment = new Department(1, "01", "Department Updated");

		doThrow(HibernateException.class).when(sessionMock).update(expectedDepartment);

		try {
			departmentManager.update(expectedDepartment);
			fail();
		} catch (EntityException e) {
			verify(sessionFactoryMock).getCurrentSession();
			verify(sessionMock).update(expectedDepartment);
			verify(transactionMock, atLeastOnce()).rollback();
		}
	}

	@Test
	public void testDelete() throws EntityException {
		Department expectedDepartment = new Department(1, "01", "Department 1");

		departmentManager.delete(expectedDepartment);
		
		verify(sessionFactoryMock).getCurrentSession();
		verify(sessionMock).delete(expectedDepartment);
		verify(transactionMock).commit();
	}
	
	@Test(expected = EntityException.class)
	public void testDeleteExpectingEntityException() throws EntityException {
		Department expectedDepartment = new Department(1, "01", "Department 1");

		doThrow(HibernateException.class).when(sessionMock).delete(expectedDepartment);

		departmentManager.delete(expectedDepartment);
	}
	
	@Test
	public void testDeleteVerifyingRollback() throws EntityException {
		Department expectedDepartment = new Department(1, "01", "Department 1");

		doThrow(HibernateException.class).when(sessionMock).delete(expectedDepartment);

		try {
			departmentManager.delete(expectedDepartment);
			fail();
		} catch (EntityException e) {

			verify(sessionFactoryMock).getCurrentSession();
			verify(sessionMock).delete(expectedDepartment);
			verify(transactionMock, atLeastOnce()).rollback();
		}
	}

}
