package com.onboarding.pos.csv;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.model.Product;

public class ProductCsvReaderTest {

	private static final String EXISTING_CSV_FILE = "src/test/resources/csv/TestProducts.csv";
	private static final String NON_EXISTING_CSV_FILE = "src/test/resources/csv/NonExistingCsvFile.csv";

	private ProductCsvReader csvReader;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		csvReader = null;
	}

	@Test
	public void testReadFromExistingFile() throws CsvReadingException, IOException {
		createTestFile(EXISTING_CSV_FILE);
		
		csvReader = new ProductCsvReader(EXISTING_CSV_FILE);

		List<Product> products = csvReader.read();
		assertNotNull(products);
	}

	@Test(expected = CsvReadingException.class)
	public void testReadFromNonExistingFile() throws CsvReadingException {
		deleteTestFile(NON_EXISTING_CSV_FILE);
		
		csvReader = new ProductCsvReader(NON_EXISTING_CSV_FILE);
		
		csvReader.read();
	}
	
	private void createTestFile(String filePath) throws IOException {
		File fileToCreate = new File(filePath);
		if (fileToCreate.isFile() && !fileToCreate.exists()) { 
			fileToCreate.createNewFile();
		}
	}
	
	private void deleteTestFile(String filePath) {
		File fileToDelete = new File(filePath);
		if (fileToDelete.exists() && fileToDelete.isFile()) {
			fileToDelete.delete();
		}
	}

}