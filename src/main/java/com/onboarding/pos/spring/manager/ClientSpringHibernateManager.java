package com.onboarding.pos.spring.manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.manager.ClientManager;
import com.onboarding.pos.model.Client;

public class ClientSpringHibernateManager extends AbstractSpringHibernateManager<Client> implements ClientManager {

	private static final String COUNT_ALL_QUERY =
			"select count(id) from Client";
	private static final String FIND_ALL_QUERY =
			"from Client";
	private static final String FIND_BY_ID_NUMBER_QUERY =
			"from Client where idNumber = :idNumber";
	private static final String ID_NUMBER = "idNumber";

	@Override
	public long countAll() {
		Session session = null;
		long count;
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createQuery(COUNT_ALL_QUERY);
			count = (long) query.uniqueResult();
		} catch (HibernateException e) {
			logger.error("Couldn't count all clients", e);
			count = 0;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAll() {
		Session session = null;
		List<Client> clients;
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createQuery(FIND_ALL_QUERY);
			clients = (List<Client>) query.list();
		} catch (HibernateException e) {
			logger.error("Couldn't find all clients", e);
			clients = new ArrayList<Client>();
		}
		return clients;
	}

	@Override
	public Client findById(int id) {
		Session session = null;
		Client client;
		try {
			session = getSessionFactory().getCurrentSession();
			client = (Client) session.get(Client.class, id);
		} catch (HibernateException e) {
			logger.error("Couldn't find client by id", e);
			client = null;
		}
		return client;
	}

	@Override
	public Client findByIdNumber(String idNumber) {
		Session session = null;
		Client client;
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createQuery(FIND_BY_ID_NUMBER_QUERY);
			query.setString(ID_NUMBER, idNumber);
			client = (Client) query.uniqueResult();
		} catch (HibernateException e) {
			logger.error("Couldn't find client by id number", e);
			client = null;
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
