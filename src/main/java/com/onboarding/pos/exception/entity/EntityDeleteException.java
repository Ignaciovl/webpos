package com.onboarding.pos.exception.entity;

import com.onboarding.pos.model.EntityWithIntId;

@SuppressWarnings("rawtypes")
public class EntityDeleteException extends EntityException {

	private static final long serialVersionUID = -3721519067662364132L;

	public EntityDeleteException(Class<? extends EntityWithIntId> entityClass, Throwable cause) {
		super("Could not complete the delete process for " + entityClass.getSimpleName(), entityClass, cause);
	}
	
}
