package com.onboarding.pos.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.model.Client;

public final class ClientCsvWriterTest {

	private static final String CORRECT_FILE_PATH =
			"src/test/resources/csv/TestClients(Write).csv";
	private static final String WRONG_FILE_PATH =
			"src/test/resources/csv/WrongClients.csv";
	private static final String NOT_A_FILE_PATH = "notAFilePath";
	private static final String NULL_FILE_PATH = null;
	private static final String EMPTY_FILE_PATH = "";
	private static final String BLANK_SPACES_FILE_PATH = " ";
	private static final int CLIENT_ONE_ID = 1;
	private static final String CLIENT_ONE_NAME = "Pedro Inostroza";
	private static final String CLIENT_ONE_ID_NUMBER = "12.345.678-9";
	private static final String CLIENT_ONE_CONTACT_NUMBER = "87654321";
	private static final String CLIENT_ONE_EMAIL = "pinostroza@hotmail.com";
	private static final String CLIENT_ONE_ADDRESS = "Far Far Away";
	private static final int CLIENT_TWO_ID = 2;
	private static final String CLIENT_TWO_NAME = "Armando Casas";
	private static final String CLIENT_TWO_ID_NUMBER = "8.765.432-1";
	private static final String CLIENT_TWO_CONTACT_NUMBER = "98765432";
	private static final String CLIENT_TWO_EMAIL = "acasas@gmail.com";
	private static final String CLIENT_TWO_ADDRESS = "Springfield";
	private static final int LIST_SIZE = 2;

	private Client clientOne;
	private Client clientTwo;
	private LinkedList<Client> clients;
	private ClientCsvWriter clientCsvWriter;

	@Before
	public void setUp() throws CsvReadingException {
		clientOne = new Client(CLIENT_ONE_ID, CLIENT_ONE_NAME,
				CLIENT_ONE_ID_NUMBER, CLIENT_ONE_CONTACT_NUMBER,
				CLIENT_ONE_EMAIL, CLIENT_ONE_ADDRESS);
		clientTwo = new Client(CLIENT_TWO_ID, CLIENT_TWO_NAME,
				CLIENT_TWO_ID_NUMBER, CLIENT_TWO_CONTACT_NUMBER,
				CLIENT_TWO_EMAIL, CLIENT_TWO_ADDRESS);
		clients = new LinkedList<Client>();
	}

	@After
	public void tearDown() throws CsvWritingException {
		LinkedList<Client> emptyClients = new LinkedList<Client>();
		clientCsvWriter = new ClientCsvWriter(CORRECT_FILE_PATH);
		clientCsvWriter.write(emptyClients);
		deleteTestFile(WRONG_FILE_PATH);
	}

	@Test
	public void testConstructorWithCorrectFilePath() {
		clientCsvWriter = new ClientCsvWriter(CORRECT_FILE_PATH);
		assertNotNull(clientCsvWriter);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNotAFilePath() throws CsvReadingException {
		clientCsvWriter = new ClientCsvWriter(NOT_A_FILE_PATH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullFilePath() throws CsvReadingException {
		clientCsvWriter = new ClientCsvWriter(NULL_FILE_PATH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithEmptyFilePath() throws CsvReadingException {
		clientCsvWriter = new ClientCsvWriter(EMPTY_FILE_PATH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithBlankSpacesFilePath()
			throws CsvReadingException {
		clientCsvWriter = new ClientCsvWriter(BLANK_SPACES_FILE_PATH);
	}

	@Test
	public void testWriteWithCorrectFilePath() throws CsvWritingException {
		clientCsvWriter = new ClientCsvWriter(CORRECT_FILE_PATH);
		clients.add(clientOne);
		clients.add(clientTwo);
		List<Client> writtenClients = clientCsvWriter.write(clients);
		assertNotNull(writtenClients);
		assertTrue(writtenClients.size() == LIST_SIZE);
	}

	@Test
	public void testWriteWithCorrectFilePathAndEmptyList()
			throws CsvWritingException {
		clientCsvWriter = new ClientCsvWriter(CORRECT_FILE_PATH);
		List<Client> writtenClients = clientCsvWriter.write(clients);
		assertNotNull(writtenClients);
		assertTrue(writtenClients.isEmpty());
	}

	@Test
	public void testWriteWithWrongFilePath() throws CsvWritingException {
		clientCsvWriter = new ClientCsvWriter(WRONG_FILE_PATH);
		clients.add(clientOne);
		clients.add(clientTwo);
		List<Client> writtenClients = clientCsvWriter.write(clients);
		assertNotNull(writtenClients);
		assertTrue(writtenClients.size() == LIST_SIZE);
	}

	@Test
	public void testWriteWithWrongFilePathAndEmptyList()
			throws CsvWritingException {
		clientCsvWriter = new ClientCsvWriter(CORRECT_FILE_PATH);
		List<Client> writtenClients = clientCsvWriter.write(clients);
		assertNotNull(writtenClients);
		assertTrue(writtenClients.isEmpty());
	}

	private void deleteTestFile(final String filePath) {
		File fileToDelete = new File(filePath);
		if (fileToDelete.exists() && fileToDelete.isFile()) {
			fileToDelete.delete();
		}
	}
}
