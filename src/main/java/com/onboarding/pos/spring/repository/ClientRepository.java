package com.onboarding.pos.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onboarding.pos.spring.config.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
	
	long count();
	
	List<Client> findAll();
	
	Client findOne(Integer id);
	
	List<Client> findClientsByIdNumber(@Param(value = "idNumber") String idNumber);
	
	@SuppressWarnings("unchecked")
	Client save(Client client);
	
	void delete(Integer id);
	
	void delete(Client client);
}
