package com.onboarding.pos.manager.csv;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.exception.entity.EntityAlreadyExistsException;
import com.onboarding.pos.exception.entity.EntityCreateException;
import com.onboarding.pos.exception.entity.EntityDeleteException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.exception.entity.EntityUpdateException;
import com.onboarding.pos.manager.ClientManager;
import com.onboarding.pos.model.Client;

public class ClientCsvManager extends AbstractCsvManager<Client> implements ClientManager {

	public ClientCsvManager(final AbstractCsvReader<Client> csvReader,
			final AbstractCsvWriter<Client> csvWriter) {
		super(csvReader, csvWriter);
	}

	public Client findByIdNumber(final String idNumber) {
		for (Client client : getEntities()) {
			if (client.getIdNumber().equalsIgnoreCase(idNumber)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public Client create(final Client client) throws EntityException {
		if (findByIdNumber(client.getIdNumber()) != null) {
			throw new EntityAlreadyExistsException(Client.class, "IdNumber");
		}
		client.setId(nextId());
		getEntities().add(client);
		
		try {
			saveEntities();
		} catch (CsvWritingException e) {
			getEntities().remove(client);
			throw new EntityCreateException(Client.class, e);
		}
		
		return client;
	}

	@Override
	public Client update(final Client client) throws EntityException {
		Client storedClient = findByIdNumber(client.getIdNumber());
		if (storedClient == null) {
			throw new EntityNotFoundException(Client.class);
		}
		client.setId(storedClient.getId());
		
		getEntities().remove(storedClient);
		getEntities().add(client);

		try {
			saveEntities();
		} catch (CsvWritingException e) {
			getEntities().remove(client);
			getEntities().add(storedClient);
			throw new EntityUpdateException(Client.class, e);
		}

		return client;
	}

	@Override
	public void delete(final Client client) throws EntityException {
		Client storedClient = findByIdNumber(client.getIdNumber());
		if (storedClient == null) {
			throw new EntityNotFoundException(Client.class);
		}
		getEntities().remove(storedClient);

		try {
			saveEntities();
		} catch (CsvWritingException e) {
			getEntities().add(storedClient);
			throw new EntityDeleteException(Client.class, e);
		}
	}
	
}
