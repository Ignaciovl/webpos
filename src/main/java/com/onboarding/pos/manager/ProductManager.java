package com.onboarding.pos.manager;

import java.util.List;

import com.onboarding.pos.model.Product;

public interface ProductManager extends EntityManager<Product> {

	public Product findByCode(String code);

	public List<Product> findByName(String name);

	public List<Product> findByDepartmentCode(String departmentCode);

}
