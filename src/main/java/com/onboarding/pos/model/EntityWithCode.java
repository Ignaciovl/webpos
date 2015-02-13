package com.onboarding.pos.model;

public abstract class EntityWithCode<T> extends EntityWithName<T> {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		validateString(code, "Code", 10);
		this.code = code;
	}
}
