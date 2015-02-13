package com.onboarding.pos.manager.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.onboarding.pos.manager.InvoiceManager;
import com.onboarding.pos.model.Invoice;
import com.onboarding.pos.model.InvoiceStatus;
import com.onboarding.pos.model.PaymentMethod;

public class InvoiceHibernateManager extends AbstractHibernateManager<Invoice> implements InvoiceManager {

	private static final String HQL_PARAM_CLIENT = "client";
	private static final String HQL_PARAM_EMPLOYEE = "employee";
	private static final String HQL_PARAM_PAYMENT_METHOD = "paymentMethod";
	private static final String HQL_PARAM_STATUS = "status";
	
	private static final String HQL_COUNT_ALL = "select count(id) from Invoice";
	private static final String HQL_FIND_ALL = "from Invoice as I left join fetch I.employee left join fetch I.client";
	private static final String HQL_FIND_BY_CLIENT = HQL_FIND_ALL + " where I.client.idNumber = :" + HQL_PARAM_CLIENT;
	private static final String HQL_FIND_BY_EMPLOYEE = HQL_FIND_ALL + " where I.employee.idNumber = :" + HQL_PARAM_EMPLOYEE;
	private static final String HQL_FIND_BY_PAYMENT_METHOD = HQL_FIND_ALL + " where I.paymentMethod = :" + HQL_PARAM_PAYMENT_METHOD;
	private static final String HQL_FIND_BY_STATUS = HQL_FIND_ALL + " where I.status = :" + HQL_PARAM_STATUS;

	public InvoiceHibernateManager(SessionFactory sessionFactory) {
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
	public List<Invoice> findAll() {
		List<Invoice> invoices;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery(HQL_FIND_ALL);
			invoices = (List<Invoice>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			invoices = new ArrayList<Invoice>();
		} finally {
			closeSession(session);
		}

		return invoices;
	}

	@Override
	public Invoice findById(int id) {
		Invoice invoice;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			invoice = (Invoice) session.get(Invoice.class, id);
			if (invoice != null) {
				Hibernate.initialize(invoice.getClient());
				Hibernate.initialize(invoice.getEmployee());
			}

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			invoice = null;
		} finally {
			closeSession(session);
		}

		return invoice;
	}
	
	@Override
	public List<Invoice> findByClientIdNumber(String idNumber) {
		return findByParam(HQL_FIND_BY_CLIENT, HQL_PARAM_CLIENT, idNumber);
	}
	
	@Override
	public List<Invoice> findByEmployeeIdNumber(String idNumber) {
		return findByParam(HQL_FIND_BY_EMPLOYEE, HQL_PARAM_EMPLOYEE, idNumber);
	}
	
	@Override
	public List<Invoice> findByPaymentMethod(PaymentMethod paymentMethod) {
		return findByParam(HQL_FIND_BY_PAYMENT_METHOD, HQL_PARAM_PAYMENT_METHOD, paymentMethod);
	}
	
	@Override
	public List<Invoice> findByStatus(InvoiceStatus status) {
		return findByParam(HQL_FIND_BY_STATUS, HQL_PARAM_STATUS, status);
	}
	
	@SuppressWarnings("unchecked")
	private List<Invoice> findByParam(String hql, String hqlParam, Object param) {
		List<Invoice> invoices;

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setParameter(hqlParam, param);
			invoices = (List<Invoice>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			invoices = new ArrayList<Invoice>();
		} finally {
			closeSession(session);
		}

		return invoices;
	}

}


