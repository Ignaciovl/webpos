package com.onboarding.pos.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {

	private static final int POSITIVE_ID = 1;
	private static final int NEGATIVE_ID = -1;
	private static final String CORRECT_STRING = "String";
	private static final String NULL_STRING = null;
	private static final String EMPTY_STRING = "";
	private static final String BLANK_SPACES_STRING = "   ";
	private static final String WAY_TOO_LONG_STRING =
			"ThisStringIsLongerThanFiftyCharactersAndIfYouDon'tBelieveMeYouCanCountThem";
	private Client client;

	@Before
	public void setUp() throws Exception {
		client = new Client();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetIdSuccess() {
		client.setId(POSITIVE_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdFaillureDueToNegativeId() {
		client.setId(NEGATIVE_ID);
	}

	@Test
	public void testSetIdNumberSuccess() {
		client.setIdNumber(CORRECT_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNumberFailDueToNullString() {
		client.setIdNumber(NULL_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNumberFailDueToEmptyString() {
		client.setIdNumber(EMPTY_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNumberFailDueToBlankSpacesString() {
		client.setIdNumber(BLANK_SPACES_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNumberFailDueToWayTooLongString() {
		client.setIdNumber(WAY_TOO_LONG_STRING);
	}

	@Test
	public void testSetContactNumberSuccess() {
		client.setContactNumber(CORRECT_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetContactNumberFailDueToNullString() {
		client.setContactNumber(NULL_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetContactNumberFailDueToEmptyString() {
		client.setContactNumber(EMPTY_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetContactNumberFailDueToBlankSpacesString() {
		client.setContactNumber(BLANK_SPACES_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetContactNumberFailDueToWayTooLongString() {
		client.setContactNumber(WAY_TOO_LONG_STRING);
	}

	@Test
	public void testSetEmailSuccess() {
		client.setEmail(CORRECT_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEmailFailDueToNullString() {
		client.setEmail(NULL_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEmailFailDueToEmptyString() {
		client.setEmail(EMPTY_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEmailFailDueToBlankSpacesString() {
		client.setEmail(BLANK_SPACES_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEmailFailDueToWayTooLongString() {
		client.setEmail(WAY_TOO_LONG_STRING);
	}

	@Test
	public void testSetAddressSuccess() {
		client.setAddress(CORRECT_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAddressFailDueToNullString() {
		client.setAddress(NULL_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAddressFailDueToEmptyString() {
		client.setAddress(EMPTY_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAddressFailDueToBlankSpacesString() {
		client.setAddress(BLANK_SPACES_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAddressFailDueToWayTooLongString() {
		client.setAddress(WAY_TOO_LONG_STRING);
	}

	@Test
	public void testSetNameSuccess() {
		client.setName(CORRECT_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameFailDueToNullString() {
		client.setName(NULL_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameFailDueToEmptyString() {
		client.setName(EMPTY_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameFailDueToBlankSpacesString() {
		client.setName(BLANK_SPACES_STRING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameFailDueToWayTooLongString() {
		client.setName(WAY_TOO_LONG_STRING);
	}
}
