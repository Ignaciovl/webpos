package com.onboarding.pos.spring.config.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public abstract class EntityWithIdNumber<T> extends EntityWithName<T> {

	@Column(name = "id_number", unique = true, nullable = false, length = 15)
	@Size(max = 15, message = "Id Number cannot be longer than 15 characters")
	@NotNull(message = "Id Number cannot be null")
	@NotEmpty(message = "Id Number cannot be empty")
	private String idNumber;

	@Column(name = "contact_number")
	@Size(max = 15, message = "Contact Number cannot be longer than 15 characters")
	private String contactNumber;

	@Column(name = "email")
	@Size(max = 50, message = "Email cannot be longer than 50 characters")
	@Email(message = "Invalid email")
	private String email;

	@Column(name = "address")
	@Size(max = 50, message = "Address cannot be longer than 50 characters")
	private String address;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
