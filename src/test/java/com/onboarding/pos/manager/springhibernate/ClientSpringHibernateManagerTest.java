package com.onboarding.pos.manager.springhibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.spring.manager.ClientSpringHibernateManager;

public class ClientSpringHibernateManagerTest {

	private static final String COUNT_ALL_QUERY =
			"select count(id) from Client";
	private static final String FIND_ALL_QUERY =
			"from Client";
	private static final String FIND_BY_ID_NUMBER_QUERY =
			"from Client where idNumber = :idNumber";

	private static final long EXPECTED_COUNT = 10;
	private static final Client CLIENT = new Client(1, "Juan Perez",
			"12.345.678-9", "87654321", "jperez@hotmail.com", "jPerezHouse");
	private static final int CLIENT_ID = 1;
	private static final String CLIENT_ID_NUMBER = "12.345.678-9";
	private static final int ZERO_OR_SMALLER_ID = 0;
	
	private ClientSpringHibernateManager clientSpringHibernateManager;
	private SessionFactory mockedSessionFactory;
	private Session mockedSession;
	private Query mockedQuery;

	@Before
	public void setUp() throws Exception {
		mockedSessionFactory = mock(SessionFactory.class);
		mockedSession = mock(Session.class);
		mockedQuery = mock(Query.class);

		when(mockedSessionFactory.getCurrentSession())
				.thenReturn(mockedSession);

		clientSpringHibernateManager = new ClientSpringHibernateManager();
		clientSpringHibernateManager.setSessionFactory(mockedSessionFactory);
	}

	@Test
	public void testCountAllSuccess() {
		when(mockedSession.createQuery(COUNT_ALL_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(EXPECTED_COUNT);
		long count = clientSpringHibernateManager.countAll();
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(COUNT_ALL_QUERY);
		verify(mockedQuery).uniqueResult();
		assertEquals(EXPECTED_COUNT, count);
	}

	@Test
	public void testFindAllSuccess() {
		List<Client> clients = new ArrayList<Client>();
		when(mockedSession.createQuery(FIND_ALL_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.list()).thenReturn(clients);
		List<Client> clientsList = clientSpringHibernateManager.findAll();
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(FIND_ALL_QUERY);
		verify(mockedQuery).list();
		assertNotNull(clientsList);
		assertEquals(clients, clientsList);
	}

	@Test
	public void testFindByIdSuccess() {
		doReturn(CLIENT).when(mockedSession).get(Client.class, CLIENT_ID);
		Client foundClient = clientSpringHibernateManager.findById(CLIENT_ID);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).get(Client.class, CLIENT_ID);
		assertEquals(CLIENT, foundClient);
	}

	@Test
	public void testFindByIdFailDueToGivenIdSmallerThanOne() {
		doReturn(null).when(mockedSession)
				.get(Client.class, ZERO_OR_SMALLER_ID);
		Client foundClient = clientSpringHibernateManager
				.findById(ZERO_OR_SMALLER_ID);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).get(Client.class, ZERO_OR_SMALLER_ID);
		assertNull(foundClient);
	}

	@Test
	public void testFindByIdFailDueToNotFound() {
		doReturn(null).when(mockedSession).get(Client.class, CLIENT_ID);
		Client foundClient = clientSpringHibernateManager.findById(CLIENT_ID);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).get(Client.class, CLIENT_ID);
		assertNull(foundClient);
	}

	@Test
	public void testFindByIdNumberSuccess() {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(CLIENT);
		Client foundClient = clientSpringHibernateManager
				.findByIdNumber(CLIENT_ID_NUMBER);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedQuery).uniqueResult();
		assertEquals(CLIENT, foundClient);
	}

	@Test
	public void testFindByIdNumberFailDueToNullGivenIdNumber() {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(null);
		Client foundClient = clientSpringHibernateManager.findByIdNumber(null);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedQuery).uniqueResult();
		assertNull(foundClient);
	}

	@Test
	public void testFindByIdNumberFailDueToNotFound() {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(null);
		Client foundClient = clientSpringHibernateManager
				.findByIdNumber(CLIENT_ID_NUMBER);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedQuery).uniqueResult();
		assertNull(foundClient);
	}

	@Test
	public void testCreateSuccess() throws EntityException {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(null);
		when(mockedSession.save(any(Client.class))).thenReturn(null);
		Client createdClient = clientSpringHibernateManager.create(CLIENT);
		verify(mockedSessionFactory, times(2)).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedSession).save(any(Client.class));
		verify(mockedQuery).uniqueResult();
		assertEquals(CLIENT, createdClient);
	}

	@Test
	public void testCreateFailDueToAlreadyExistingClient()
			throws EntityException {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(CLIENT);
		Client createdClient = clientSpringHibernateManager.create(CLIENT);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedQuery).uniqueResult();
		assertNull(createdClient);
	}

	@Test
	public void testCreateFailDueToNullGivenClient() throws EntityException {
		Client createdClient = clientSpringHibernateManager.create(null);
		verify(mockedSessionFactory, never()).getCurrentSession();
		verify(mockedSession, never()).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedSession, never()).save(any(Client.class));
		verify(mockedQuery, never()).uniqueResult();
		assertNull(createdClient);
	}

	@Test
	public void testUpdateSuccess() throws EntityException {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(CLIENT);
		doNothing().when(mockedSession).update(any(Client.class));
		Client updatedClient = clientSpringHibernateManager.update(CLIENT);
		verify(mockedSessionFactory, times(2)).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedSession).update(any(Client.class));
		verify(mockedQuery).uniqueResult();
		assertEquals(CLIENT, updatedClient);
	}

	@Test
	public void testUpdateFailDueToNotFoundClient() throws EntityException {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(null);
		Client updatedClient = clientSpringHibernateManager.update(CLIENT);
		verify(mockedSessionFactory).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedQuery).uniqueResult();
		assertNull(updatedClient);
	}

	@Test
	public void testUpdateFailDueToNullGivenClient() throws EntityException {
		Client updatedClient = clientSpringHibernateManager.update(null);
		verify(mockedSessionFactory, never()).getCurrentSession();
		verify(mockedSession, never()).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedSession, never()).update(any(Client.class));
		verify(mockedQuery, never()).uniqueResult();
		assertNull(updatedClient);
	}

	@Test
	public void testDeleteSuccess() throws EntityException {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(CLIENT);
		doNothing().when(mockedSession).delete(any(Client.class));
		clientSpringHibernateManager.delete(CLIENT);
		verify(mockedSessionFactory, times(2)).getCurrentSession();
		verify(mockedSession).createQuery(FIND_BY_ID_NUMBER_QUERY);
		verify(mockedSession).delete(any(Client.class));
		verify(mockedQuery).uniqueResult();
	}

	@Test (expected = EntityNotFoundException.class)
	public void testDeleteFailDueToNotFoundClient() throws EntityException {
		when(mockedSession.createQuery(FIND_BY_ID_NUMBER_QUERY)).thenReturn(mockedQuery);
		when(mockedQuery.uniqueResult()).thenReturn(null);
		clientSpringHibernateManager.delete(CLIENT);
	}

	@Test (expected = EntityNotFoundException.class)
	public void testDeleteFailDueToNullGivenClient() throws EntityException {
		clientSpringHibernateManager.delete(null);
	}
}
