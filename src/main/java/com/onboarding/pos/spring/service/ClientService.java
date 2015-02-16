package com.onboarding.pos.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.spring.manager.ClientSpringHibernateManager;

@Service
public class ClientService implements EntityService<Client> {
	
	private ClientSpringHibernateManager clientSpringHibernateManager;

	public ClientSpringHibernateManager getClientSpringHibernateManager() {
		return clientSpringHibernateManager;
	}

	public void setClientSpringHibernateManager(
			ClientSpringHibernateManager clientSpringHibernateManager) {
		this.clientSpringHibernateManager = clientSpringHibernateManager;
	}

	@Override
	@Transactional
	public long countAll() {
		return getClientSpringHibernateManager().countAll();
	}

	@Override
	@Transactional
	public List<Client> findAll() {
		return getClientSpringHibernateManager().findAll();
	}

	@Override
	@Transactional
	public Client findById(int id) {
		return getClientSpringHibernateManager().findById(id);
	}

	@Transactional
	public Client findByIdNumber(String idNumber) {
		return getClientSpringHibernateManager().findByIdNumber(idNumber);
	}

	@Override
	@Transactional
	public Client create(Client client) throws EntityException {
		return getClientSpringHibernateManager().create(client);
	}

	@Override
	@Transactional
	public Client update(Client client) throws EntityException {
		return getClientSpringHibernateManager().update(client);
	}

	@Override
	@Transactional
	public void delete(Client client) throws EntityException {
		getClientSpringHibernateManager().delete(client);
	}
}
