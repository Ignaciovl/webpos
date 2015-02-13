package com.onboarding.pos.exception.entity;

import com.onboarding.pos.model.EntityWithIntId;

@SuppressWarnings("rawtypes")
public class EntityCreateException extends EntityException {
	
	private static final long serialVersionUID = -6393260536265575725L;

	public EntityCreateException(Class<? extends EntityWithIntId> entityClass, Throwable cause) {
		super("Could not complete the create process for " + entityClass.getSimpleName(), entityClass, cause);
	}
	
}
