package com.onboarding.pos.manager.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.csv.ClientCsvReader;
import com.onboarding.pos.csv.ClientCsvWriter;
import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.exception.entity.EntityAlreadyExistsException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.model.Client;

public class ClientCsvManagerTest {
	private static final int AMMOUNT_OF_CLIENTS = 5;
	private static final int EXISTENT_CLIENT_ID = 3;
	private static final int UNEXISTENT_CLIENT_ID = 6;
	private static final int CREATED_CLIENT_ID = 6;
	private static final String EXISTENT_CLIENT_ID_NUMBER = "33.333.333-3";
	private static final String UNEXISTENT_CLIENT_ID_NUMBER = "66.666.666-6";
	private static final Client CLIENT_ONE = new Client(1, "Client 1", "11.111.111-1", "11111111",
			"one@email.address", "address 1");
	private static final Client CLIENT_TWO = new Client(2, "Client 2", "22.222.222-2", "22222222",
			"two@email.address", "address 2");
	private static final Client CLIENT_THREE = new Client(3, "Client 3", "33.333.333-3",
			"33333333", "three@email.address", "address 3");
	private static final Client CLIENT_FOUR = new Client(4, "Client 4", "44.444.444-4", "44444444",
			"four@email.address", "address 4");
	private static final Client CLIENT_FIVE = new Client(5, "Client 5", "55.555.555-5", "55555555",
			"five@email.address", "address 5");
	private static final Client CLIENT_TO_CREATE = new Client(3, "Client 6", "66.666.666-6",
			"66666666", "six@email.address", "address 6");
	private static final Client CLIENT_TO_CREATE_WITH_EXISTENT_ID_NUMBER = new Client(3,
			"Client 6", "33.333.333-3", "66666666", "six@email.address", "address 6");
	private static final Client CLIENT_TO_UPDATE = new Client(3, "Client 6", "33.333.333-3",
			"66666666", "six@email.address", "address 6");
	private static final Client CLIENT_TO_UPDATE_OR_DELETE_WITH_UNEXISTENT_ID_NUMBER = new Client(
			3, "Client 6", "66.666.666-6", "66666666", "six@email.address", "address 6");
	private static final Client CLIENT_TO_DELETE = new Client(3, "Client 3", "33.333.333-3",
			"33333333", "three@email.address", "address 3");

	private LinkedList<Client> clientsList = new LinkedList<Client>();
	private ClientCsvReader mockedClientCSVReader;
	private ClientCsvWriter mockedClientCSVWriter;
	private ClientCsvManager clientCSVManager;

	@Before
	public void setUp() throws Exception {
		clientsList.add(CLIENT_ONE);
		clientsList.add(CLIENT_TWO);
		clientsList.add(CLIENT_THREE);
		clientsList.add(CLIENT_FOUR);
		clientsList.add(CLIENT_FIVE);
		mockedClientCSVReader = mock(ClientCsvReader.class);
		when(mockedClientCSVReader.read()).thenReturn(clientsList);
		mockedClientCSVWriter = mock(ClientCsvWriter.class);
		doReturn(clientsList).when(mockedClientCSVWriter).write(anyListOf(Client.class));
		clientCSVManager = new ClientCsvManager(mockedClientCSVReader, mockedClientCSVWriter);
	}

	@Test
	public void testCountAllWhenNotYetLoaded() throws CsvReadingException {
		long count = clientCSVManager.countAll();
		verify(mockedClientCSVReader).read();
		assertTrue(count == AMMOUNT_OF_CLIENTS);
	}

	@Test
	public void testCountAllWhenAlreadyLoaded() throws CsvReadingException {
		clientCSVManager.getEntitiesForcingReload();
		long count = clientCSVManager.countAll();
		verify(mockedClientCSVReader, times(1)).read();
		assertTrue(count == AMMOUNT_OF_CLIENTS);
	}

	@Test
	public void testFindAllWhenNotYetLoaded() throws CsvReadingException {
		List<Client> foundClients = clientCSVManager.findAll();
		verify(mockedClientCSVReader).read();
		assertTrue(foundClients.size() == AMMOUNT_OF_CLIENTS);
	}

	@Test
	public void testFindAllWhenAlreadyLoaded() throws CsvReadingException {
		clientCSVManager.getEntitiesForcingReload();
		List<Client> foundClients = clientCSVManager.findAll();
		verify(mockedClientCSVReader, times(1)).read();
		assertTrue(foundClients.size() == AMMOUNT_OF_CLIENTS);
	}

	@Test
	public void testFindByIdSuccessWhenNotYetLoaded() throws CsvReadingException {
		Client client = clientCSVManager.findById(EXISTENT_CLIENT_ID);
		verify(mockedClientCSVReader).read();
		assertTrue(client.getId() == EXISTENT_CLIENT_ID);
		assertTrue(client.getIdNumber().equalsIgnoreCase(EXISTENT_CLIENT_ID_NUMBER));
	}

	@Test
	public void testFindByIdSuccessWhenAlreadyLoaded() throws CsvReadingException {
		clientCSVManager.getEntitiesForcingReload();
		Client client = clientCSVManager.findById(EXISTENT_CLIENT_ID);
		verify(mockedClientCSVReader, times(1)).read();
		assertTrue(client.getId() == EXISTENT_CLIENT_ID);
		assertTrue(client.getIdNumber().equalsIgnoreCase(EXISTENT_CLIENT_ID_NUMBER));
	}

	@Test
	public void testFindByIdFailWhenNotYetLoaded() throws CsvReadingException {
		Client client = clientCSVManager.findById(UNEXISTENT_CLIENT_ID);
		verify(mockedClientCSVReader).read();
		assertNull(client);
	}

	@Test
	public void testFindByIdFailWhenAlreadyLoaded() throws CsvReadingException {
		clientCSVManager.getEntitiesForcingReload();
		Client client = clientCSVManager.findById(UNEXISTENT_CLIENT_ID);
		verify(mockedClientCSVReader, times(1)).read();
		assertNull(client);
	}

	@Test
	public void testFindByIdNumberSuccessWhenNotYetLoaded() throws CsvReadingException {
		Client client = clientCSVManager.findByIdNumber(EXISTENT_CLIENT_ID_NUMBER);
		verify(mockedClientCSVReader).read();
		assertTrue(client.getId() == EXISTENT_CLIENT_ID);
		assertTrue(client.getIdNumber().equalsIgnoreCase(EXISTENT_CLIENT_ID_NUMBER));
	}

	@Test
	public void testFindByIdNumberSuccessWhenAlreadyLoaded() throws CsvReadingException {
		clientCSVManager.getEntitiesForcingReload();
		Client client = clientCSVManager.findByIdNumber(EXISTENT_CLIENT_ID_NUMBER);
		verify(mockedClientCSVReader, times(1)).read();
		assertTrue(client.getId() == EXISTENT_CLIENT_ID);
		assertTrue(client.getIdNumber().equalsIgnoreCase(EXISTENT_CLIENT_ID_NUMBER));
	}

	@Test
	public void testFindByIdNumberFailWhenNotYetLoaded() throws CsvReadingException {
		Client client = clientCSVManager.findByIdNumber(UNEXISTENT_CLIENT_ID_NUMBER);
		verify(mockedClientCSVReader).read();
		assertNull(client);
	}

	@Test
	public void testFindByIdNumberFailWhenAlreadyLoaded() throws CsvReadingException {
		clientCSVManager.getEntitiesForcingReload();
		Client client = clientCSVManager.findByIdNumber(UNEXISTENT_CLIENT_ID_NUMBER);
		verify(mockedClientCSVReader, times(1)).read();
		assertNull(client);
	}

	@Test
	public void testCreateSuccess() throws EntityException, CsvWritingException {
		Client createdClient = clientCSVManager.create(CLIENT_TO_CREATE);
		verify(mockedClientCSVWriter).write(anyListOf(Client.class));
		assertNotNull(createdClient);
		assertTrue(createdClient.getId() == CREATED_CLIENT_ID);
	}

	@Test(expected = EntityAlreadyExistsException.class)
	public void testCreateFail() throws EntityException, CsvWritingException {
		Client createdClient = clientCSVManager.create(CLIENT_TO_CREATE_WITH_EXISTENT_ID_NUMBER);
		verifyZeroInteractions(mockedClientCSVWriter.write(anyListOf(Client.class)));
		assertNull(createdClient);
	}

	@Test
	public void testUpdateSuccess() throws EntityException, CsvWritingException {
		Client updatedClient = clientCSVManager.update(CLIENT_TO_UPDATE);
		verify(mockedClientCSVWriter).write(anyListOf(Client.class));
		assertNotNull(updatedClient);
		assertTrue(updatedClient.getId() == EXISTENT_CLIENT_ID);
		assertTrue(updatedClient.getIdNumber().equalsIgnoreCase(EXISTENT_CLIENT_ID_NUMBER));
	}

	@Test(expected = EntityNotFoundException.class)
	public void testUpdateFail() throws EntityException, CsvWritingException {
		Client updatedClient = clientCSVManager
				.update(CLIENT_TO_UPDATE_OR_DELETE_WITH_UNEXISTENT_ID_NUMBER);
		verifyZeroInteractions(mockedClientCSVWriter.write(anyListOf(Client.class)));
		assertNull(updatedClient);
	}

	@Test
	public void testDeleteSuccess() throws EntityException, CsvWritingException {
		clientCSVManager.delete(CLIENT_TO_DELETE);
		verify(mockedClientCSVWriter).write(anyListOf(Client.class));
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteFail() throws EntityException, CsvWritingException {
		clientCSVManager.delete(CLIENT_TO_UPDATE_OR_DELETE_WITH_UNEXISTENT_ID_NUMBER);
		verifyZeroInteractions(mockedClientCSVWriter.write(anyListOf(Client.class)));
	}
}
