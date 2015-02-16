package com.onboarding.pos.spring.service;

import java.util.List;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.model.EntityWithIntId;

public interface EntityService<T extends EntityWithIntId<T>> {

	public long countAll();

	public List<T> findAll();

	public T findById(int id);

	public T create(T obj) throws EntityException;

	public T update(T obj) throws EntityException;

	public void delete(T obj) throws EntityException;
	
}
