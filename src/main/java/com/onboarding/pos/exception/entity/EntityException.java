package com.onboarding.pos.exception.entity;

import com.onboarding.pos.exception.PosException;
import com.onboarding.pos.model.EntityWithIntId;

@SuppressWarnings("rawtypes")
public class EntityException extends PosException {

	private static final long serialVersionUID = 2080185025542233675L;
	
	private Class<? extends EntityWithIntId> entityClass;

	public EntityException(String message, Class<? extends EntityWithIntId> entityClass) {
		super(message);
		this.entityClass = entityClass;
	}
	
	public EntityException(String message, Class<? extends EntityWithIntId> entityClass, Throwable cause) {
		super(message, cause);
		this.entityClass = entityClass;
	}

	public Class<? extends EntityWithIntId> getEntityClass() {
		return entityClass;
	}
	
}
