package com.onboarding.pos.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.Validate;

import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.model.Product;

public class ProductCsvWriter extends AbstractCsvWriter<Product> {

	public ProductCsvWriter(String csvFilePath) {
		super(csvFilePath);
	}

	@Override
	public List<Product> write(List<Product> products) throws CsvWritingException {
		Validate.isTrue(products != null, "The product list cannot be NULL");

		String csvFilePath = getCsvFilePath();
		BufferedWriter fileWriter = null;
		CSVPrinter printer = null;
		
		try {
			fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
			printer = CSVFormat.DEFAULT.print(fileWriter);
			
			printer.printRecords(productsToRecords(products));
			
			fileWriter.flush();
		} catch (IOException e) {
			throw new CsvWritingException(csvFilePath, e);
		} finally {
			if (fileWriter != null)
				try { fileWriter.close(); } catch(IOException ex) { };
			if (printer != null)
				try { printer.close(); } catch(IOException ex) { };
		}
		
		return products;
	}

	private List<String[]> productsToRecords(List<Product> products) {
		List<String[]> records = new ArrayList<String[]>();
		
		records.add(headersFromEnum(ProductCsvHeader.class));
		
		for (Product product : products) {
			records.add(new String[] {
				Integer.toString(product.getId()), 
				product.getCode(), 
				product.getName(),
				product.getDepartmentCode(), 
				product.getPrice().toString() 
			});
		}

		return records;
	}
}
