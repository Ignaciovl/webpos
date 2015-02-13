package com.onboarding.pos.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.model.Client;

public final class ClientCsvReaderTest {

	private static final int LIST_SIZE = 5;
	private static final String CORRECT_FILE_PATH =
			"src/test/resources/csv/TestClients(Read).csv";
	private static final String NOT_A_FILE_PATH = "notAFilePath";
	private static final String EMPTY_FILE_FILE_PATH =
			"src/test/resources/csv/EmptyClients.csv";
	private static final String NULL_FILE_PATH = null;
	private static final String EMPTY_FILE_PATH = "";
	private static final String BLANK_SPACES_FILE_PATH = " ";

	private ClientCsvReader clientCsvReader;

	@Test
	public void testConstructorWithCorrectFilePath() {
		clientCsvReader = new ClientCsvReader(CORRECT_FILE_PATH);
		assertNotNull(clientCsvReader);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNotAFilePath() {
		clientCsvReader = new ClientCsvReader(NOT_A_FILE_PATH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullFilePath() {
		clientCsvReader = new ClientCsvReader(NULL_FILE_PATH);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithEmptyFilePath() {
		clientCsvReader = new ClientCsvReader(EMPTY_FILE_PATH);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithBlankSpacesFilePath() {
		clientCsvReader = new ClientCsvReader(BLANK_SPACES_FILE_PATH);
	}

	@Test
	public void testReadWithCorrectFilePath() throws CsvReadingException {
		clientCsvReader = new ClientCsvReader(CORRECT_FILE_PATH);
		LinkedList<Client> readClients = clientCsvReader.read();
		assertNotNull(readClients);
		assertTrue(readClients.size() == LIST_SIZE);
	}

	@Test
	public void testReadWithAnEmptyFile() throws CsvReadingException {
		clientCsvReader = new ClientCsvReader(EMPTY_FILE_FILE_PATH);
		LinkedList<Client> readClients = clientCsvReader.read();
		assertNotNull(readClients);
		assertTrue(readClients.isEmpty());
	}
}
