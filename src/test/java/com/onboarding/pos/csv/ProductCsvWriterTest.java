package com.onboarding.pos.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.model.Product;

public class ProductCsvWriterTest {

	private static final String EXISTING_CSV_FILE = "src/test/resources/csv/TestProducts.csv";
	private static final String NON_EXISTING_CSV_FILE = "src/test/resources/csv/NonExistingCsvFile.csv";

	private ProductCsvWriter csvWriter;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		csvWriter = null;
	}
	
	@Test
	public void testWriteToExistingFile() throws CsvWritingException, IOException {
		createTestFile(EXISTING_CSV_FILE);
		List<Product> productsToWrite = buildProductToWrite();
		
		csvWriter = new ProductCsvWriter(EXISTING_CSV_FILE);
		
		List<Product> products = csvWriter.write(productsToWrite);
		assertNotNull(products);
		assertEquals(productsToWrite.size(), products.size());
	}
	
	@Test
	public void testWriteToNonExistingFile() throws CsvWritingException {
		deleteTestFile(NON_EXISTING_CSV_FILE);
		List<Product> productsToWrite = buildProductToWrite();
		
		csvWriter = new ProductCsvWriter(NON_EXISTING_CSV_FILE);
		
		List<Product> products = csvWriter.write(productsToWrite);
		assertNotNull(products);
		assertEquals(productsToWrite.size(), products.size());
		
		deleteTestFile(NON_EXISTING_CSV_FILE);
	}
	
	private List<Product> buildProductToWrite() {
		List<Product> productsToWrite = new ArrayList<Product>();
		
		productsToWrite.add(new Product(1, "0000000001", "Men blue jeans", "01", new BigDecimal(15000.00)));
		productsToWrite.add(new Product(2, "0000000002", "Women blue jeans", "02", new BigDecimal(16000.00)));
		productsToWrite.add(new Product(3, "0000000003", "Men black shoes", "03", new BigDecimal(30000.00)));
		productsToWrite.add(new Product(4, "0000000004", "Women red shoes", "04", new BigDecimal(40000.00)));
		productsToWrite.add(new Product(5, "0000000005", "Men white shirt", "01", new BigDecimal(10000.00)));
		productsToWrite.add(new Product(6, "0000000006", "Women white shirt", "02", new BigDecimal(8000.00)));
		productsToWrite.add(new Product(7, "0000000007", "Men white sneakers", "03", new BigDecimal(21000.00)));
		productsToWrite.add(new Product(8, "0000000008", "Women white sneakers", "04", new BigDecimal(18000.00)));
		productsToWrite.add(new Product(9, "0000000009", "Men black belt", "05", new BigDecimal(6000.00)));
		productsToWrite.add(new Product(10, "0000000010", "Women red belt", "06", new BigDecimal(9000.00)));
		productsToWrite.add(new Product(11, "0000000011", "Black bagpack", "07", new BigDecimal(25000.00)));
		productsToWrite.add(new Product(12, "0000000012", "White purse", "06", new BigDecimal(33000.00)));
		
		return productsToWrite;
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
