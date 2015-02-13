package com.onboarding.pos.manager.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.manager.ClientManager;
import com.onboarding.pos.model.Client;

public class ClientHibernateManager extends AbstractHibernateManager<Client> implements ClientManager {

	private static final String COUNT_ALL_QUERY =
			"select count(id) from Client";
	private static final String FIND_ALL_QUERY =
			"from Client";
	private static final String FIND_BY_ID_NUMBER_QUERY =
			"from Client where idNumber = :idNumber";
	private static final String ID_NUMBER = "idNumber";

	public ClientHibernateManager(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public long countAll() {
		Session session = null;
		Transaction transaction = null;
		long count;
		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(COUNT_ALL_QUERY);
			count = (long) query.uniqueResult();
			transaction.commit();
		} catch (HibernateException e) {
			logger.error("Couldn't count all clients", e);
			rollbackTransaction(transaction);
			count = 0;
		} finally {
			closeSession(session);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAll() {
		Session session = null;
		Transaction transaction = null;
		List<Client> clients;
		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(FIND_ALL_QUERY);
			clients = (List<Client>) query.list();
			transaction.commit();
		} catch (HibernateException e) {
			logger.error("Couldn't find all clients", e);
			rollbackTransaction(transaction);
			clients = new ArrayList<Client>();
		} finally {
			closeSession(session);
		}
		return clients;
	}

	@Override
	public Client findById(final int id) {
		Session session = null;
		Transaction transaction = null;
		Client client;
		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			client = (Client) session.get(Client.class, id);
			transaction.commit();
		} catch (HibernateException e) {
			logger.error("Couldn't find client by id", e);
			rollbackTransaction(transaction);
			client = null;
		} finally {
			closeSession(session);
		}
		return client;
	}

	public Client findByIdNumber(final String idNumber) {
		Session session = null;
		Transaction transaction = null;
		Client client;
		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(FIND_BY_ID_NUMBER_QUERY);
			query.setString(ID_NUMBER, idNumber);
			client = (Client) query.uniqueResult();
			transaction.commit();
		} catch (HibernateException e) {
			logger.error("Couldn't find client by id number", e);
			rollbackTransaction(transaction);
			client = null;
		} finally {
			closeSession(session);
		}
		return client;
	}

	@Override
	public Client create(final Client client) throws EntityException {
		if (client == null || findByIdNumber(client.getIdNumber()) != null) {
			return null;
		}
		return super.create(client);
	}

	@Override
	public Client update(final Client client) throws EntityException {
		if (client == null) {
			return null;
		}
		Client checkedClient = findByIdNumber(client.getIdNumber());
		if (checkedClient == null) {
			return null;
		}
		return super.update(checkedClient);
	}

	@Override
	public void delete(final Client client) throws EntityException {
		if (client == null) {
			throw new EntityNotFoundException(Client.class);
		}
		Client checkedClient = findByIdNumber(client.getIdNumber());
		if (checkedClient == null) {
			throw new EntityNotFoundException(Client.class);
		}
		super.delete(checkedClient);
	}
}
