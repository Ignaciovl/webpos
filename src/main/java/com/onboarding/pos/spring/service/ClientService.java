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
	ClientRepository clientRepository;
	
	public long count() {
		return clientRepository.count();
	}
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findOne(Integer id) {
		return clientRepository.findOne(id);
	}
	
	public List<Client> findClientsByIdNumber(String idNumber) {
		return clientRepository.findClientsByIdNumber(idNumber);
	}
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	
	public void delete(Integer id) {
		clientRepository.delete(id);
	}
	public void delete(Client client) {
		clientRepository.delete(client);
	}
}
