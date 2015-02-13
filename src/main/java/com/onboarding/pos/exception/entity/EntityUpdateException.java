package com.onboarding.pos.exception.entity;

import com.onboarding.pos.model.EntityWithIntId;

@SuppressWarnings("rawtypes")
public class EntityUpdateException extends EntityException {

	private static final long serialVersionUID = -8976544560001841021L;

	public EntityUpdateException(Class<? extends EntityWithIntId> entityClass, Throwable cause) {
		super("Could not complete the update process for " + entityClass.getSimpleName(), entityClass, cause);
	}
	
}
