package com.onboarding.pos.spring.manager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.onboarding.pos.exception.entity.EntityCreateException;
import com.onboarding.pos.exception.entity.EntityDeleteException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityUpdateException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.EntityWithIntId;

public abstract class AbstractSpringHibernateManager<T extends EntityWithIntId<T>> implements
EntityManager<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public T create(T entity) throws EntityException {
		Session session = null;

		try {
			session = getSessionFactory().getCurrentSession();
			session.save(entity);
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			throw new EntityCreateException(entity.getClass(), e);
		}

		return entity;
	}

	@Override
	public T update(T entity) throws EntityException {
		Session session = null;

		try {
			session = getSessionFactory().getCurrentSession();
			session.update(entity);
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			throw new EntityUpdateException(entity.getClass(), e);
		}

		return entity;
	}

	@Override
	public void delete(T entity) throws EntityException {
		Session session = null;

		try {
			session = getSessionFactory().getCurrentSession();
			session.delete(entity);
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			throw new EntityDeleteException(entity.getClass(), e);
		}
	}
}
