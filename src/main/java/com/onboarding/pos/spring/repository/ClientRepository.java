package com.onboarding.pos.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onboarding.pos.spring.config.model.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
	
	long count();
	List<Client> findAll();
	Client findOne(Integer id);
	List<Client> findClientsByIdNumber(String idNumber);
	@SuppressWarnings("unchecked")
	Client save(Client client);
	void delete(Integer id);
	void delete(Client client);
}
