package com.onboarding.pos.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.model.Client;

public class ClientCsvWriter extends AbstractCsvWriter<Client> {

	public ClientCsvWriter(final String csvFilePath) {
		super(csvFilePath);
	}

	public List<Client> write(final List<Client> clients)
			throws CsvWritingException {
		try (CSVPrinter printer = new CSVPrinter(new PrintWriter(new File(
				getCsvFilePath())), CSVFormat.DEFAULT.withQuote('\''))) {
			List<String[]> header = new ArrayList<String[]>();
			header.add(headersFromEnum(ClientCsvHeader.class));
			printer.printRecords(header);
			for (Client client : clients) {
				println(printer, client.getId() + "", client.getName(),
						client.getIdNumber(), client.getContactNumber(),
						client.getEmail(), client.getAddress());
			}
			return clients;
		} catch (FileNotFoundException e) {
			throw new CsvWritingException(getCsvFilePath(), e);
		} catch (IOException e) {
			throw new CsvWritingException(getCsvFilePath(), e);
		}
	}

	private void println(final CSVPrinter printer, final String id,
			final String name, final String idNumber,
			final String contactNumber, final String email,
			final String address)
			throws CsvWritingException {
		try {
			printer.print(id);
			printer.print(name);
			printer.print(idNumber);
			printer.print(contactNumber);
			printer.print(email);
			printer.print(address);
			printer.println();
		} catch (IOException e) {
			throw new CsvWritingException(getCsvFilePath(), e);
		}
	}
}
