package com.onboarding.pos.manager.hibernate;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.onboarding.pos.exception.entity.EntityCreateException;
import com.onboarding.pos.exception.entity.EntityDeleteException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityUpdateException;
import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.EntityWithIntId;

public abstract class AbstractHibernateManager<T extends EntityWithIntId<T>> implements
		EntityManager<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	private SessionFactory sessionFactory;

	public AbstractHibernateManager(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public T create(T entity) throws EntityException {
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			session.save(entity);
			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			rollbackTransaction(transaction);
			throw new EntityCreateException(entity.getClass(), e);
		} finally {
			closeSession(session);
		}

		return entity;
	}

	@Override
	public T update(T entity) throws EntityException {
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			session.update(entity);
			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			rollbackTransaction(transaction);
			throw new EntityUpdateException(entity.getClass(), e);
		} finally {
			closeSession(session);
		}

		return entity;
	}

	@Override
	public void delete(T entity) throws EntityException {
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			session.delete(entity);
			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			rollbackTransaction(transaction);
			throw new EntityDeleteException(entity.getClass(), e);
		} finally {
			closeSession(session);
		}
	}

	protected void rollbackTransaction(Transaction transaction) {
		if (transaction != null) {
			try {
				transaction.rollback();
			} catch (HibernateException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}
	}

	protected void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			try {
				session.close();
			} catch (HibernateException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}
	}

}
