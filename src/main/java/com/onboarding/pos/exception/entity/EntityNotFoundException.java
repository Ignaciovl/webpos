package com.onboarding.pos.exception.entity;

import com.onboarding.pos.model.EntityWithIntId;

@SuppressWarnings("rawtypes")
public class EntityNotFoundException extends EntityException {

	private static final long serialVersionUID = 6525279019085507932L;
	
	public EntityNotFoundException(Class<? extends EntityWithIntId> entityClass) {
		super(entityClass.getSimpleName() + " not found", entityClass);
	}

}
