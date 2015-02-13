package com.onboarding.pos.exception.entity;

import com.onboarding.pos.model.EntityWithIntId;

@SuppressWarnings("rawtypes")
public class EntityAlreadyExistsException extends EntityException {

	private static final long serialVersionUID = -4098491108598053685L;

	public EntityAlreadyExistsException(Class<? extends EntityWithIntId> entityClass, String existingUniqueField) {
		super("There is already a " + entityClass.getSimpleName() + " with the specified " + existingUniqueField, entityClass);
	}

}
