package com.onboarding.pos.model;

public interface EntityWithIntId<T> extends Comparable<T> {

	public int getId();

	public void setId(int id);

}
