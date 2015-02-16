package com.onboarding.pos.spring;

import org.springframework.ui.Model;

import com.onboarding.pos.model.EntityWithIntId;
import com.onboarding.pos.spring.service.EntityService;

public interface EntityController<T extends EntityWithIntId<T>> {

	public EntityService<T> getService();
	
	public void setService(EntityService<T> service);
	
	public String list(Model model);
	
	public String add(T obj);
	
	public String update(T obj);
	
	public String remove(int id);
	
	public String edit(int id, Model model);
}
