package com.onboarding.pos.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityWithIdNumber<T> extends EntityWithName<T> {

	@Column(name = "id_number", unique = true, nullable = false, length = 15)
	private String idNumber;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		validateString(idNumber, "Id Number", 15);
		this.idNumber = idNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		validateString(contactNumber, "Contact Number", 15);
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		validateString(email, "Email", 50);
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		validateString(address, "Address", 50);
		this.address = address;
	}
}
