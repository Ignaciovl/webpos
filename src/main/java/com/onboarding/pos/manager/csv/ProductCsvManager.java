package com.onboarding.pos.manager.csv;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.onboarding.pos.csv.AbstractCsvReader;
import com.onboarding.pos.csv.AbstractCsvWriter;
import com.onboarding.pos.exception.csv.CsvWritingException;
import com.onboarding.pos.exception.entity.EntityAlreadyExistsException;
import com.onboarding.pos.exception.entity.EntityCreateException;
import com.onboarding.pos.exception.entity.EntityDeleteException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.exception.entity.EntityUpdateException;
import com.onboarding.pos.manager.ProductManager;
import com.onboarding.pos.model.Product;

public class ProductCsvManager extends AbstractCsvManager<Product> implements ProductManager {

	public ProductCsvManager(AbstractCsvReader<Product> csvReader, AbstractCsvWriter<Product> csvWriter) {
		super(csvReader, csvWriter);
	}

	@Override
	public Product findByCode(String code) {
		Validate.isTrue(code != null && !code.isEmpty(), "Invalid code");

		for (Product product : getProducts()) {
			if (product.getCode().equals(code))
				return product;
		}

		return null;
	}

	@Override
	public List<Product> findByName(String name) {
		Validate.isTrue(name != null, "Invalid name");

		List<Product> productsByName = new ArrayList<Product>();
		for (Product product : getProducts()) {
			if (StringUtils.containsIgnoreCase(product.getName(), name))
				productsByName.add(product);
		}

		return productsByName;
	}

	@Override
	public List<Product> findByDepartmentCode(String departmentCode) {
		Validate.isTrue(departmentCode != null, "Invalid department code");

		List<Product> productsByDepartment = new ArrayList<Product>();
		for (Product product : getProducts()) {
			if (StringUtils.containsIgnoreCase(product.getDepartmentCode(), departmentCode))
				productsByDepartment.add(product);
		}

		return productsByDepartment;
	}

	@Override
	public Product create(Product product) throws EntityException {
		validateProduct(product);

		if (findByCode(product.getCode()) != null)
			throw new EntityAlreadyExistsException(Product.class, "code");

		product.setId(nextId());
		getProducts().add(product);

		try {
			saveEntities();
		} catch (CsvWritingException e) {
			getProducts().remove(product);
			throw new EntityCreateException(Product.class, e);
		}

		return findById(product.getId());
	}

	@Override
	public Product update(Product product) throws EntityException {
		validateProductId(product);
		validateProduct(product);

		Product storedProduct = findById(product.getId());
		if (storedProduct == null)
			throw new EntityNotFoundException(Product.class);

		Product existingProduct = findByCode(product.getCode());
		if (existingProduct != null && storedProduct.getId() != existingProduct.getId())
			throw new EntityAlreadyExistsException(Product.class, "code");

		getProducts().remove(storedProduct);
		getProducts().add(product);

		try {
			saveEntities();
		} catch (CsvWritingException e) {
			getProducts().remove(product);
			getProducts().add(storedProduct);
			throw new EntityUpdateException(Product.class, e);
		}

		return findById(product.getId());
	}

	@Override
	public void delete(Product product) throws EntityException {
		validateProductId(product);

		Product storedProduct = findById(product.getId());
		if (storedProduct == null)
			throw new EntityNotFoundException(Product.class);

		getProducts().remove(storedProduct);

		try {
			saveEntities();
		} catch (CsvWritingException e) {
			getProducts().add(storedProduct);
			throw new EntityDeleteException(Product.class, e);
		}
	}

	private void validateProduct(Product product) {
		Validate.isTrue(product != null, "Product cannot be null");
		new Product(product.getCode(), product.getName(), product.getDepartmentCode(),
				product.getPrice());
	}

	private void validateProductId(Product product) {
		Validate.isTrue(product != null, "Product cannot be null");
		Validate.isTrue(product.getId() > 0, "Invalid Product ID");
	}

	private List<Product> getProducts() {
		return getEntities();
	}

}
