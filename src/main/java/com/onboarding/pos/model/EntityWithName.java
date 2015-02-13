package com.onboarding.pos.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.Validate;

@MappedSuperclass
public abstract class EntityWithName<T> implements EntityWithIntId<T> {

	@Column(name = "name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		validateString(name, "Name", 30);
		this.name = name;
	}

	protected void validateString(String stringToValidate, String stringName, int stringLength) {
		Validate.isTrue(stringToValidate != null, stringName + " cannot be null");
		Validate.isTrue(!stringToValidate.isEmpty(), stringName + " cannot be empty");
		Validate.isTrue(!stringToValidate.trim().isEmpty(), stringName
				+ " cannot be filled with blank space(s)");
		Validate.isTrue(stringToValidate.length() <= stringLength, stringName
				+ " cannot be longer than " + stringLength + " characters");
	}
}
