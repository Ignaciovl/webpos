package com.onboarding.pos.module;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.csv.ClientCsvManager;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.util.SystemHelper;

public class ClientModuleTest {

	private static final int ID = 1;
	private static final int AMMOUNT_OF_LINES_READ = 5;
	private static final String ID_NUMBER = "12.345.678-9";
	private static final String INVALID_ID_NUMBER = " ";
	private static final String NAME = "Armando Casas";
	private static final String CONTACT_NUMBER = "87654321";
	private static final String EMAIL = "acasas@hotmail.com";
	private static final String ADDRESS = "Far Far Away";
	private static final Client CLIENT = new Client(ID, NAME, ID_NUMBER, CONTACT_NUMBER, EMAIL,
			ADDRESS);

	private SystemHelper mockedSystemHelper;
	private ClientCsvManager mockedClientCsvManager;
	private ClientModule clientModule;

	@Before
	public void setUp() throws Exception {
		mockedSystemHelper = mock(SystemHelper.class);
		mockedClientCsvManager = mock(ClientCsvManager.class);
		clientModule = new ClientModule(mockedSystemHelper, mockedClientCsvManager);
	}

	@Test
	public void testConstructorSuccess() {
		assertNotNull(clientModule);
		assertNotNull(clientModule.getSystemHelper());
		assertNotNull(clientModule.getManager());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailDueToNullSystemHelper() {
		new ClientModule(null, mockedClientCsvManager);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFailDueToNullClientCsvManager() {
		new ClientModule(mockedSystemHelper, null);
	}

	@Test
	public void testPrintAllClientsSuccess() {
		List<Client> clients = new LinkedList<Client>();
		when(mockedClientCsvManager.findAll()).thenReturn(clients);
		clientModule.printAllClients();
		verify(mockedClientCsvManager).findAll();
	}

	@Test
	public void testAddClientSuccess() throws EntityException {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER, NAME, CONTACT_NUMBER,
				EMAIL, ADDRESS);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(null);
		when(mockedClientCsvManager.create(any(Client.class))).thenReturn(CLIENT);
		Client addedClient = clientModule.addClient();
		verify(mockedSystemHelper, times(AMMOUNT_OF_LINES_READ)).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		verify(mockedClientCsvManager).create(any(Client.class));
		assertTrue(ID_NUMBER.equalsIgnoreCase(addedClient.getIdNumber()));
	}

	@Test
	public void testAddClientCancelledByUser() {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER, NAME, CONTACT_NUMBER,
				EMAIL, ADDRESS);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		Client addedClient = clientModule.addClient();
		verify(mockedSystemHelper, times(AMMOUNT_OF_LINES_READ)).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		assertNull(addedClient);
	}

	@Test
	public void testAddClientFailDueToAlreadyExistingClient() {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER, NAME, CONTACT_NUMBER,
				EMAIL, ADDRESS);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(CLIENT);
		Client addedClient = clientModule.addClient();
		verify(mockedSystemHelper, times(AMMOUNT_OF_LINES_READ)).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		assertNull(addedClient);
	}

	@Test(expected = NullPointerException.class)
	public void testAddClientFailDueToInvalidIdNumber() {
		when(mockedSystemHelper.readLine()).thenReturn(INVALID_ID_NUMBER, NAME, CONTACT_NUMBER,
				EMAIL, ADDRESS);
		clientModule.addClient();
	}

	@Test
	public void testUpdateClientSuccess() throws EntityException {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER, NAME, CONTACT_NUMBER,
				EMAIL, ADDRESS);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(CLIENT);
		when(mockedClientCsvManager.update(any(Client.class))).thenReturn(CLIENT);
		Client updatedClient = clientModule.updateClient();
		verify(mockedSystemHelper, times(AMMOUNT_OF_LINES_READ)).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		verify(mockedClientCsvManager).update(any(Client.class));
		assertTrue(ID_NUMBER.equalsIgnoreCase(updatedClient.getIdNumber()));
	}

	@Test
	public void testUpdateClientCancelledByUser() {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER, NAME, CONTACT_NUMBER,
				EMAIL, ADDRESS);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(CLIENT);
		Client updatedClient = clientModule.updateClient();
		verify(mockedSystemHelper, times(AMMOUNT_OF_LINES_READ)).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		assertNull(updatedClient);
	}

	@Test
	public void testUpdateClientFailDueToNotFoundClient() {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(null);
		Client updatedClient = clientModule.updateClient();
		verify(mockedSystemHelper).askString(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		assertNull(updatedClient);
	}

	@Test
	public void testUpdateClientFailDueToInvalidIdNumber() {
		when(mockedSystemHelper.readLine()).thenReturn(INVALID_ID_NUMBER);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(null);
		Client updatedClient = clientModule.updateClient();
		verify(mockedSystemHelper).askString(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		assertNull(updatedClient);
	}

	@Test
	public void testDeleteClientSuccess() throws EntityException {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(CLIENT);
		doNothing().when(mockedClientCsvManager).delete(any(Client.class));
		Client deletedClient = clientModule.deleteClient();
		verify(mockedSystemHelper).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		verify(mockedClientCsvManager).delete(any(Client.class));
		assertTrue(ID_NUMBER.equalsIgnoreCase(deletedClient.getIdNumber()));
	}

	@Test
	public void testDeleteClientCancelledByUser() {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(false);
		Client deletedClient = clientModule.deleteClient();
		verify(mockedSystemHelper).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		assertNull(deletedClient);
	}

	@Test
	public void testDeleteClientFailDueToNotFoundClient() {
		when(mockedSystemHelper.askString(anyString())).thenReturn(ID_NUMBER);
		when(mockedSystemHelper.askYesOrNo(anyString())).thenReturn(true);
		when(mockedClientCsvManager.findByIdNumber(anyString())).thenReturn(null);
		Client deletedClient = clientModule.deleteClient();
		verify(mockedSystemHelper).askString(anyString());
		verify(mockedSystemHelper).askYesOrNo(anyString());
		verify(mockedClientCsvManager).findByIdNumber(anyString());
		assertNull(deletedClient);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteClientFailDueToInvalidIdNumber() {
		when(mockedSystemHelper.readLine()).thenReturn(INVALID_ID_NUMBER);
		clientModule.deleteClient();
	}
}
