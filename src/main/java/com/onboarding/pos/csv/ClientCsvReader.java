package com.onboarding.pos.csv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.model.Client;

public class ClientCsvReader extends AbstractCsvReader<Client> {

	public ClientCsvReader(final String csvFilePath) {
		super(csvFilePath);
	}

	@Override
	public LinkedList<Client> read() throws CsvReadingException {
		LinkedList<Client> clients = new LinkedList<Client>();
		try (CSVParser parser = CSVParser.parse(new File(getCsvFilePath()),
				Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader()
						.withQuote('\''));) {
			for (CSVRecord csvRecord : parser) {
				clients.add(new Client(Integer.parseInt(csvRecord
						.get(ClientCsvHeader.ID)), csvRecord
						.get(ClientCsvHeader.NAME), csvRecord
						.get(ClientCsvHeader.ID_NUMBER), csvRecord
						.get(ClientCsvHeader.CONTACT_NUMBER), csvRecord
						.get(ClientCsvHeader.EMAIL), csvRecord
						.get(ClientCsvHeader.ADDRESS)));
			}
			parser.close();
		} catch (IOException e) {
			throw new CsvReadingException(getCsvFilePath(), e);
		}
		return clients;
	}

}