package com.onboarding.pos.module;

import org.apache.commons.lang3.Validate;

import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.EntityWithIntId;
import com.onboarding.pos.util.SystemHelper;

public abstract class Module<T extends EntityWithIntId<T>> {

	protected SystemHelper systemHelper;
	protected EntityManager<T> entityManager;

	public Module(SystemHelper systemHelper, EntityManager<T> manager) {
		setSystemHelper(systemHelper);
		setManager(manager);
	}

	public abstract void init();

	public SystemHelper getSystemHelper() {
		return systemHelper;
	}

	public void setSystemHelper(SystemHelper systemHelper) {
		Validate.isTrue(systemHelper != null);
		this.systemHelper = systemHelper;
	}
	
	public EntityManager<T> getManager() {
		return entityManager;
	}

	public void setManager(EntityManager<T> manager) {
		Validate.isTrue(manager != null);
		this.entityManager = manager;
	}
	
	protected void exit() {
		boolean confirm = systemHelper.askYesOrNo("\nAre you sure you want to exit? (Y/N): ");
		if (confirm) {
			systemHelper.println("\nHave a nice day!");
			systemHelper.println("===================================================");
			systemHelper.exit(0);
		}
	}

}
