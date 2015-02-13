package com.onboarding.pos.manager;

import com.onboarding.pos.model.Client;

public interface ClientManager extends EntityManager<Client> {

	public Client findByIdNumber(String idNumber);
	
}
