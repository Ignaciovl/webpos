package com.onboarding.pos.manager.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.exception.csv.CsvReadingException;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.EntityWithIntId;

public abstract class AbstractCsvManager<T extends EntityWithIntId<T>> implements EntityManager<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	private AbstractCsvReader<T> csvReader;
	private AbstractCsvWriter<T> csvWriter;
	private List<T> entities;

	public AbstractCsvManager(AbstractCsvReader<T> csvReader, AbstractCsvWriter<T> csvWriter) {
		setCsvReader(csvReader);
		setCsvWriter(csvWriter);
	}

	public AbstractCsvReader<T> getCSVReader() {
		return csvReader;
	}

	public void setCsvReader(AbstractCsvReader<T> csvReader) {
		this.csvReader = csvReader;
	}

	public AbstractCsvWriter<T> getCSVWriter() {
		return csvWriter;
	}

	public void setCsvWriter(AbstractCsvWriter<T> csvWriter) {
		this.csvWriter = csvWriter;
	}

	@Override
	public long countAll() {
		return getEntities().size();
	}

	@Override
	public List<T> findAll() {
		return getEntities();
	}

	@Override
	public T findById(int id) {
		for (T entity : getEntities()) {
			if (entity.getId() == id)
				return entity;
		}

		return null;
	}

	protected int nextId() {
		List<T> currentEntities = getEntities();
		if (currentEntities.isEmpty())
			return 1;

		int lastIndex = currentEntities.size() - 1;
		T lastEntities = currentEntities.get(lastIndex);

		return lastEntities.getId() + 1;
	}

	protected List<T> getEntities() {
		if (entities == null)
			loadEntities();

		return entities;
	}

	protected List<T> getEntitiesForcingReload() {
		loadEntities();

		return entities;
	}

	protected void setEntities(List<T> entities) {
		this.entities = entities;
	}

	protected void sortEntities(List<T> entitiesToSort) {
		Collections.sort(entitiesToSort);
	}

	protected void loadEntities() {
		List<T> entitiesFromCSV = null;
		try {
			entitiesFromCSV = getCSVReader().read();
			Collections.sort(entitiesFromCSV);
		} catch (CsvReadingException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			entitiesFromCSV = new ArrayList<T>();
		}
		setEntities(entitiesFromCSV);
	}

	protected void saveEntities() throws CsvWritingException {
		try {
			List<T> entitiesToSave = getEntities();
			Collections.sort(entitiesToSave);
			getCSVWriter().write(entitiesToSave);
		} catch (CsvWritingException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			throw e;
		}
	}

}
