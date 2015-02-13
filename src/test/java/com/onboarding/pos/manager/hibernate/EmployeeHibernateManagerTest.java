package com.onboarding.pos.manager.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.hibernate.EmployeeHibernateManager;
import com.onboarding.pos.model.Employee;
import com.onboarding.pos.util.HibernateUtil;

public class EmployeeHibernateManagerTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCountAll() {		
		EmployeeHibernateManager empMan = new EmployeeHibernateManager(HibernateUtil.getSessionFactory());
		assertNotNull(empMan.countAll());
	}

	@Test
	public void testFindAll() {
		EmployeeHibernateManager empMan = new EmployeeHibernateManager(HibernateUtil.getSessionFactory());	
		assertNotNull(empMan.findAll());
	}

	@Test
	public void testFindById() {		
		EmployeeHibernateManager empMan = new EmployeeHibernateManager(HibernateUtil.getSessionFactory());
		int id = 10;
		assertNull(empMan.findById(id));
	}

	@Test
	public void testCreate() throws EntityException {
		
		EmployeeHibernateManager empMan = new EmployeeHibernateManager(HibernateUtil.getSessionFactory());	
		
		
		Employee emp = new Employee(2,"Joe","17342832-4","95180950","castuardo@outlook.com","Alcazar#2106","Cashier", "123456");
		Employee empG = new Employee(1,"Joe","17342832-4","98573958","joem@hotmail.com","Av.CristobalColon#4647A","Asistant", "micasa");
		assertEquals(empMan.create(emp), emp);
		assertNotEquals(empMan.create(empG), empG);
		empMan.delete(emp);
		
		
	}

	@Test
	public void testUpdate() throws EntityException{
		
		EmployeeHibernateManager empMan = new EmployeeHibernateManager(HibernateUtil.getSessionFactory());	
		
		assertNotNull(empMan.countAll() == 0);
		
		Employee emp = new Employee(10,"Joe","17342832-4","95180950","castuardo@outlook.com","Alcazar#2106","Cashier", "123456");
		Employee empUp = new Employee(10,"Joe","17342832-4","95180950","cstuardom@hotmail.com","Av.CristobalColon#4647B","Manager", "123abc");
		Employee empG = new Employee(20,"Joe","17343832-4","98573958","joem@hotmail.com","Av.CristobalColon#4647A","Asistant", "micasa");

		assertEquals(empMan.create(emp), emp);
		assertNotEquals(emp, empUp);
		assertNotEquals(empMan.update(empG), empG);
		assertEquals(empMan.update(empUp), empUp);
		
		empMan.delete(empUp);		
	}

	@Test
	public void testDelete() throws EntityException {

		EmployeeHibernateManager empMan = new EmployeeHibernateManager(HibernateUtil.getSessionFactory());	
		Employee emp = new Employee(11,"Joe","17342832-4","95180950","castuardo@outlook.com","Alcazar#2106","Cashier", "123456");
		Employee empG = new Employee(21,"Joe","19654789-4","98573958","joem@hotmail.com","Av.CristobalColon#4647A","Asistant", "micasa");

		assertNull(empMan.findById(empG.getId()));
		assertEquals(empMan.create(emp), emp);
		empMan.delete(emp);
		assertNull(empMan.findById(emp.getId()));		
		empMan.delete(empG);
	}
}
