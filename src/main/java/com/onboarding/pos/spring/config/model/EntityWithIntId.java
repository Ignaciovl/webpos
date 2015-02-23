package com.onboarding.pos.spring.config.model;

public interface EntityWithIntId<T> extends Comparable<T> {

	public int getId();

	public void setId(int id);

}
