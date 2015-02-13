package com.onboarding.pos.manager.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.EmployeeManager;
import com.onboarding.pos.model.Employee;

public class EmployeeHibernateManager extends AbstractHibernateManager<Employee> implements EmployeeManager {

	public EmployeeHibernateManager(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public long countAll() {
		int all = findAll().size();
		return all;
	}

	@Override
	public List<Employee> findAll() {

		Session hibSes = getSessionFactory().getCurrentSession();
		hibSes.beginTransaction();
		Query query = hibSes.createQuery("from Employee");
		@SuppressWarnings("unchecked")
		List<Employee> emps = (List<Employee>) query.list();
		hibSes.getTransaction().commit();
		closeSession(hibSes);
		return emps;
	}

	@Override
	public Employee findById(int id) {

		Session hibSes = getSessionFactory().getCurrentSession();
		hibSes.beginTransaction();
		Query query = hibSes.createQuery("from Employee employee where employee.id = ?");
		query.setInteger(0, id);
		@SuppressWarnings("unchecked")
		List<Employee> emps = (List<Employee>) query.list();
		hibSes.getTransaction().commit();
		closeSession(hibSes);
		if (emps.size() > 0) {
			return emps.get(0);
		} else {
			return null;
		}
	}

	public Employee findByIdNumber(String idNumber) {

		Session hibSes = getSessionFactory().getCurrentSession();
		hibSes.beginTransaction();
		Query query = hibSes.createQuery("from Employee employee where employee.idNumber = ?");
		query.setString(0, idNumber);
		@SuppressWarnings("unchecked")
		List<Employee> emps = (List<Employee>) query.list();
		hibSes.getTransaction().commit();
		closeSession(hibSes);
		if (emps.size() > 0) {
			return emps.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Employee create(Employee emp) throws EntityException {

		Employee empCheck = findByIdNumber(emp.getIdNumber());
		if (empCheck != null) {
			return null;
		} else {
			return super.create(emp);
		}

	}

	@Override
	public Employee update(Employee emp) throws EntityException {

		Employee empCheck = findByIdNumber(emp.getIdNumber());
		if (empCheck == null) {
			return null;
		} else {
			emp.setId(empCheck.getId());
			return super.update(emp);
		}
	}

	@Override
	public void delete(Employee emp) throws EntityException {

		Employee empCheck = findByIdNumber(emp.getIdNumber());
		if (empCheck == null) {
		} else {
			emp.setId(empCheck.getId());
			super.delete(emp);
		}

	}

}
