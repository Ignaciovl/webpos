package com.onboarding.pos.spring.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "client", uniqueConstraints =
@UniqueConstraint(columnNames = "id_number"))
public class Client {
	
	@Id
	@SequenceGenerator(name = "gen_client_id",
	sequenceName = "seq_client_id", allocationSize = 1)
	@GeneratedValue(strategy =
	GenerationType.SEQUENCE, generator = "gen_client_id")
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "id_number", unique = true, nullable = false, length = 15)
	private String idNumber;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
