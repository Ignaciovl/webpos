package com.onboarding.pos.csv;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractCsvHandlerTest {

	private static final String CSV_FILE = "csvFile.csv";
	private static final String NON_CSV_FILE = "nonCsvFile.txt";

	private AbstractCsvHandler csvHandler;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorWithValidCsvFile() {
		csvHandler = new AbstractCsvHandler(CSV_FILE) {
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullFilePath() {
		csvHandler = new AbstractCsvHandler(null) {
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithEmptyFilePath() {
		csvHandler = new AbstractCsvHandler("") {
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithBlankSpacesFilePath() {
		csvHandler = new AbstractCsvHandler(" ") {
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNonCSVFilePath() {
		csvHandler = new AbstractCsvHandler(NON_CSV_FILE) {
		};
	}

	@Test
	public void testHeadersFromEnum() {
		csvHandler = new AbstractCsvHandler(CSV_FILE) {
		};
		
		String[] expectedHeaders = {TestEnum.A.toString(), TestEnum.B.toString(), TestEnum.C.toString(), TestEnum.D.toString()};
		String[] resultHeaders = csvHandler.headersFromEnum(TestEnum.class);
		assertArrayEquals(expectedHeaders, resultHeaders);
	}
	
	private enum TestEnum {
		A, B, C, D
	}
	
}
