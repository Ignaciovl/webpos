package com.onboarding.pos.spring.config.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public abstract class EntityWithName<T> implements EntityWithIntId<T> {

	@Column(name = "name", nullable = false)
	@Size(max = 30, message = "Name cannot be longer than 30 characters")
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
