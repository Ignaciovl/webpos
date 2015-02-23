package com.onboarding.pos.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onboarding.pos.spring.config.model.Client;
import com.onboarding.pos.spring.repository.ClientRepository;

@Service
@Transactional
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public ClientRepository getClientRepository() {
		return clientRepository;
	}

	public void setClientRepository(final ClientRepository givenClientRepository) {
		this.clientRepository = givenClientRepository;
	}

	public long count() {
		return getClientRepository().count();
	}
	
	public List<Client> findAll() {
		return getClientRepository().findAll();
	}
	
	public Client findOne(final Integer id) {
		return getClientRepository().findOne(id);
	}
	
	public List<Client> findClientsByIdNumber(final String idNumber) {
		return getClientRepository().findClientsByIdNumber(idNumber);
	}
	
	public Client save(final Client client) {
		return getClientRepository().save(client);
	}
	
	public void delete(final Integer id) {
		getClientRepository().delete(id);
	}
	public void delete(final Client client) {
		getClientRepository().delete(client);
	}
}
