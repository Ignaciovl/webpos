package com.onboarding.pos.manager.csv;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.csv.ProductCsvReader;
import com.onboarding.pos.csv.ProductCsvWriter;
import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.model.Product;

@SuppressWarnings("unchecked")
public class ProductCsvManagerTest {

	private Product productWithId1;
	private Product productWithId2;
	private Product productWithId3;
	private List<Product> products;
	private AbstractCsvReader<Product> csvReaderMock;
	private AbstractCsvWriter<Product> csvWriterMock;
	private ProductCsvManager productCSVManager;
	
	@Before
	public void setUp() throws Exception {
		productWithId1 = new Product(1, "0000000001", "Men blue jeans", "01", new BigDecimal(15000.00));
		productWithId2 = new Product(2, "0000000002", "Women blue jeans", "02", new BigDecimal(16000.00));
		productWithId3 = new Product(3, "0000000003", "Men black shoes", "03", new BigDecimal(30000.00));
		
		products = new ArrayList<Product>();
		products.add(productWithId1);
		products.add(productWithId2);
		products.add(productWithId3);
		
		csvReaderMock = mock(ProductCsvReader.class);
		when(csvReaderMock.read()).thenReturn(products);
		
		csvWriterMock = mock(ProductCsvWriter.class);
		when(csvWriterMock.write(anyListOf(Product.class))).thenAnswer(new Answer<List<Product>>() {
			@Override
			public List<Product> answer(InvocationOnMock invocation) throws Throwable {
				return (List<Product>) invocation.getArgumentAt(0, List.class);
			}
		});
		
		productCSVManager = new ProductCsvManager(csvReaderMock, csvWriterMock);
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testGetEntitiesForcingLoadFromCSV() throws CsvReadingException {
		List<Product> entities = productCSVManager.getEntitiesForcingReload();
		verify(csvReaderMock, times(1)).read();
		assertEquals(products, entities);
	}
	
	@Test
	public void testGetEntitiesNotForcingButLoadsFromCSV() throws CsvReadingException {
		List<Product> entities = productCSVManager.getEntities();
		verify(csvReaderMock, times(1)).read();
		assertEquals(products, entities);
	}
	
	@Test
	public void testGetEntitiesNotForcingAndNotLoadingFromCSV() throws CsvReadingException {
		productCSVManager.setEntities(products);
		List<Product> entities = productCSVManager.getEntities();
		verifyZeroInteractions(csvReaderMock);
		assertEquals(products, entities);
	}
	
	@Test
	public void testNextIdReadingFromCSV() throws CsvReadingException {
		int nextId = productCSVManager.nextId();
		verify(csvReaderMock, times(1)).read();
		assertEquals(4, nextId);
	}
	
	@Test
	public void testNextIdNotReadingFromCSV() throws CsvReadingException {
		productCSVManager.setEntities(products);
		int nextId = productCSVManager.nextId();
		verifyZeroInteractions(csvReaderMock);
		assertEquals(4, nextId);
	}
	
	@Test
	public void testLoadFromCSV() throws CsvReadingException {
		productCSVManager.loadEntities();
		verify(csvReaderMock, times(1)).read();
		verifyZeroInteractions(csvWriterMock);
	}
	
	@Test
	public void testSaveToCSVReadingFromCSVBeforeSaving() throws CsvReadingException, CsvWritingException {
		productCSVManager.saveEntities();
		verify(csvReaderMock, times(1)).read();
		verify(csvWriterMock, times(1)).write(products);
	}
	
	@Test
	public void testSaveToCSVNotReadingFromCSVBeforeSaving() throws CsvWritingException {
		productCSVManager.setEntities(products);
		productCSVManager.saveEntities();
		verifyZeroInteractions(csvReaderMock);
		verify(csvWriterMock, times(1)).write(products);
	}
	
	@Test
	public void testCountAllReadingFromCSV() throws CsvReadingException {
		long count = productCSVManager.countAll();
		verify(csvReaderMock, times(1)).read();
		assertEquals(products.size(), count);
	}
	
	@Test
	public void testCountAllNotReadingFromCSV() throws CsvReadingException {
		productCSVManager.setEntities(products);
		long count = productCSVManager.countAll();
		verifyZeroInteractions(csvReaderMock);
		assertEquals(products.size(), count);
	}
	
	@Test
	public void testFindAllReadingFromCSV() throws CsvReadingException {
		List<Product> allProducts = productCSVManager.findAll();
		verify(csvReaderMock, times(1)).read();
		assertEquals(products, allProducts);
	}
	
	@Test
	public void testFindAllNotReadingFromCSV() throws CsvReadingException {
		productCSVManager.setEntities(products);
		List<Product> allProducts = productCSVManager.findAll();
		verifyZeroInteractions(csvReaderMock);
		assertEquals(products, allProducts);
	}
	
	@Test
	public void testFindByIdReadingFromCSV() throws CsvReadingException {
		Product product = productCSVManager.findById(2);
		verify(csvReaderMock, times(1)).read();
		assertEquals(productWithId2, product);
	}
	
	@Test
	public void testFindByIdNotReadingFromCSV() throws CsvReadingException {
		productCSVManager.setEntities(products);
		Product product = productCSVManager.findById(2);
		verifyZeroInteractions(csvReaderMock);
		assertEquals(productWithId2, product);
	}
	
	@Test
	public void testCreateSuccessfully() throws EntityException, CsvWritingException, CsvReadingException {
		Product productToCreate = new Product("0000000004", "Women red shoes", "04", new BigDecimal(40000.00));
		Product productCreated = productCSVManager.create(productToCreate);
		verify(csvWriterMock, times(1)).write(anyListOf(Product.class));
		verify(csvReaderMock, times(1)).read();
		assertEquals(productToCreate, productCreated);
	}
	
	@Test(expected = EntityException.class)
	public void testCreateWithExistingCode() throws EntityException, CsvWritingException, CsvReadingException {
		Product productToCreate = new Product("0000000001", "Women red shoes", "04", new BigDecimal(40000.00));
		productCSVManager.create(productToCreate);
	}
	
	@Test
	public void testUpdateSuccessfully() throws EntityException, CsvWritingException, CsvReadingException {
		Product productWithId1ToUpdate = new Product(1, "0000000001", "Men black jeans", "01", new BigDecimal(20000.00));
		Product productUpdated = productCSVManager.update(productWithId1ToUpdate);
		verify(csvWriterMock, times(1)).write(anyListOf(Product.class));
		verify(csvReaderMock, times(1)).read();
		assertEquals(productWithId1ToUpdate, productUpdated);
	}
	
	@Test(expected = EntityException.class)
	public void testUpdateNonExistingProduct() throws EntityException, CsvWritingException, CsvReadingException {
		Product productNotCreatedToUpdate = new Product(4, "0000000004", "Women red shoes", "04", new BigDecimal(40000.00));
		productCSVManager.update(productNotCreatedToUpdate);
	}
	
	@Test(expected = EntityException.class)
	public void testUpdateWithExistingCode() throws EntityException, CsvWritingException, CsvReadingException {
		Product productWithId1AndExistingCodeToUpdate = new Product(1, "0000000002", "Men black jeans", "01", new BigDecimal(20000.00));
		productCSVManager.update(productWithId1AndExistingCodeToUpdate);
	}
	
	@Test
	public void testDeleteSuccessfully() throws EntityException, CsvWritingException, CsvReadingException {
		Product productWithId1ToDelete = new Product(1, "0000000001", "Men black jeans", "01", new BigDecimal(20000.00));
		productCSVManager.delete(productWithId1ToDelete);
		verify(csvWriterMock, times(1)).write(anyListOf(Product.class));
		verify(csvReaderMock, times(1)).read();
	}
	
	@Test(expected = EntityException.class)
	public void testDeleteNonExistingProduct() throws EntityException, CsvWritingException, CsvReadingException {
		Product productNotCreatedToDelete = new Product(4, "0000000004", "Women red shoes", "04", new BigDecimal(40000.00));
		productCSVManager.delete(productNotCreatedToDelete);
	}

}
