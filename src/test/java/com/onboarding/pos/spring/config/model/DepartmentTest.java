package com.onboarding.pos.spring.config.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DepartmentTest {

	private Department department;

	@Before
	public void setUp() throws Exception {
		department = new Department();
	}

	@After
	public void tearDown() throws Exception {
		department = null;
	}

	@Test
	public void testSetId() {
		department.setId(1);
	}

	@Test
	public void testSetIdZero() {
		department.setId(0);
	}

	@Test
	public void testSetIdNegative() {
		department.setId(-1);
	}

	@Test
	public void testSetCode() {
		department.setCode("1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeNull() {
		department.setCode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeEmpty() {
		department.setCode("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeBlankSpace() {
		department.setCode(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeWayTooLong() {
		department.setCode("12345678901");
	}

	@Test
	public void testSetName() {
		department.setName("Name accepted");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameNull() {
		department.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameEmpty() {
		department.setName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameBlankSpace() {
		department.setName(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameWayTooLong() {
		department.setName("This name should not be accepted. Is too long! Is a short name, not the whole user guide!");
	}

}
