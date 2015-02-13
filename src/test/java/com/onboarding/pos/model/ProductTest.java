package com.onboarding.pos.model;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

	private Product product;

	@Before
	public void setUp() throws Exception {
		product = new Product();
	}

	@After
	public void tearDown() throws Exception {
		product = null;
	}

	@Test
	public void testProductInt() {
		product = new Product(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductIntZero() {
		product = new Product(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductIntNegative() {
		product = new Product(-1);
	}

	@Test
	public void testProductString() {
		product = new Product("1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductStringNull() {
		product = new Product(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductStringEmpty() {
		product = new Product("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductStringBlankSpace() {
		product = new Product(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductStringWayTooLong() {
		product = new Product("12345678901");
	}

	@Test
	public void testSetId() {
		product.setId(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdZero() {
		product.setId(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNegative() {
		product.setId(-1);
	}

	@Test
	public void testSetCode() {
		product.setCode("1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeNull() {
		product.setCode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeEmpty() {
		product.setCode("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeBlankSpace() {
		product.setCode(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCodeWayTooLong() {
		product.setCode("12345678901");
	}

	@Test
	public void testSetName() {
		product.setName("Name accepted");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameNull() {
		product.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameEmpty() {
		product.setName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameBlankSpace() {
		product.setName(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameWayTooLong() {
		product.setName("This name should not be accepted. Is too long! Is a short name, not the whole user guide!");
	}

	@Test
	public void testSetDepartmentCode() {
		product.setDepartmentCode("01");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDepartmentCodeNull() {
		product.setDepartmentCode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDepartmentCodeEmpty() {
		product.setDepartmentCode("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDepartmentCodeBlankSpace() {
		product.setDepartmentCode(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDepartmentCodeWayTooLong() {
		product.setDepartmentCode("0000001");
	}

	@Test
	public void testSetPrice() {
		product.setPrice(new BigDecimal(15000));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPriceNull() {
		product.setPrice(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPriceZero() {
		product.setPrice(BigDecimal.ZERO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPriceZeroAgain() {
		product.setPrice(new BigDecimal(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPriceNegative() {
		product.setPrice(new BigDecimal(-1));
	}

}
