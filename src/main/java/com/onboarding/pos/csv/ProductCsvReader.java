package com.onboarding.pos.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.model.Product;

public class ProductCsvReader extends AbstractCsvReader<Product> {

	public ProductCsvReader(String csvFilePath) {
		super(csvFilePath);
	}

	@Override
	public List<Product> read() throws CsvReadingException {
		List<Product> products = new ArrayList<Product>();
		
		String csvFilePath = getCsvFilePath();
		BufferedReader reader = null;
		CSVParser parser = null;

		try {
			reader = new BufferedReader(new FileReader(csvFilePath));
			parser = CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord(true).parse(reader);

			products = recordsToProducts(parser.getRecords());
		} catch (IOException e) {
			throw new CsvReadingException(csvFilePath, e);
		} finally {
			if (reader != null)
				try { reader.close(); } catch (IOException e) {}
			if (parser != null)
				try { parser.close(); } catch (IOException e) {}
		}

		return products;
	}
	
	private List<Product> recordsToProducts(List<CSVRecord> records) {
		List<Product> products = new ArrayList<Product>();
		
		for (CSVRecord record : records) {
			Product product = new Product();
			product.setId(Integer.valueOf(record.get(ProductCsvHeader.ID)));
			product.setCode(record.get(ProductCsvHeader.CODE));
			product.setName(record.get(ProductCsvHeader.NAME));
			product.setDepartmentCode(record.get(ProductCsvHeader.DEPARTMENT));
			product.setPrice(new BigDecimal(record.get(ProductCsvHeader.PRICE)));
			products.add(product);
		}
		
		return products;
	}

}
