package com.onboarding.pos.manager.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.onboarding.pos.manager.DepartmentManager;
import com.onboarding.pos.model.Department;

public class DepartmentHibernateManager extends AbstractHibernateManager<Department> implements
		DepartmentManager {

	private static final String HQL_PARAM_CODE = "code";
	private static final String HQL_PARAM_NAME = "name";
	
	private static final String HQL_COUNT_ALL = "select count(id) from Department";
	private static final String HQL_FIND_ALL = "from Department";
	private static final String HQL_FIND_BY_CODE = "from Department where code = :" + HQL_PARAM_CODE;
	private static final String HQL_FIND_BY_NAME = "from Department where name LIKE :" + HQL_PARAM_NAME;

	public DepartmentHibernateManager(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public long countAll() {
		long count;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery(HQL_COUNT_ALL);
			count = (long) query.uniqueResult();

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			count = 0;
		} finally {
			closeSession(session);
		}

		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findAll() {
		List<Department> departments;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery(HQL_FIND_ALL);
			departments = (List<Department>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			departments = new ArrayList<Department>();
		} finally {
			closeSession(session);
		}

		return departments;
	}

	@Override
	public Department findById(int id) {
		Department department;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			department = (Department) session.get(Department.class, id);

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			department = null;
		} finally {
			closeSession(session);
		}

		return department;
	}

	@Override
	public Department findByCode(String code) {
		Department department;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery(HQL_FIND_BY_CODE);
			query.setString(HQL_PARAM_CODE, code);
			department = (Department) query.uniqueResult();

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			department = null;
		} finally {
			closeSession(session);
		}

		return department;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findByName(String name) {
		List<Department> departments;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery(HQL_FIND_BY_NAME);
			query.setString(HQL_PARAM_NAME, "%" + name + "%");
			departments = (List<Department>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			departments = new ArrayList<Department>();
		} finally {
			closeSession(session);
		}

		return departments;
	}

}
