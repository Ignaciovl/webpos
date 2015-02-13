package com.onboarding.pos.manager;

import java.util.List;

import com.onboarding.pos.model.Department;

public interface DepartmentManager extends EntityManager<Department> {

	public Department findByCode(String code);

	public List<Department> findByName(String name);

}
