package com.onboarding.pos.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(columnNames = "id_number"))
public class Employee extends EntityWithIdNumber<Employee> {

	@Id
	@SequenceGenerator(name = "gen_employee_id", sequenceName = "seq_employee_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_employee_id")
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "position")
	private String position;
	@Column(name = "password", nullable = false, length = 16)
	private String password;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Invoice> invoices = new HashSet<Invoice>(0);

	public Employee() {
	}

	public Employee(int id, String name, String idNumber, String password) {
		
		this.setId(id);
		this.setName(name);
		this.setIdNumber(idNumber);
		this.setPassword(password);
	}

	public Employee(int id, String name, String idNumber, String contactNumber, String email,
			String address, String position, String password) {
		
		this.setId(id);
		this.setName(name);
		this.setIdNumber(idNumber);
		this.setContactNumber(contactNumber);
		this.setEmail(email);
		this.setAddress(address);
		this.setPosition(position);
		this.setPassword(password);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		
		Validate.isTrue(position != null && !position.isEmpty() && !position.trim().isEmpty(),
				"Invalid position");
		this.position = position;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String newPassword) {
		
		validateString(newPassword, "password", 16);
		this.password = newPassword;
	}

	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Override
	public String toString() {
		return String.format("| %5s| %25s| %10s| %15s| %25s| %30s| %15s | %17s |", getId(),
				getName(), getIdNumber(), getContactNumber(), getEmail(), getAddress(),
				getPosition(), getPassword());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		result = prime * result
				+ ((getContactNumber() == null) ? 0 : getContactNumber().hashCode());
		result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
		result = prime * result + ((getIdNumber() == null) ? 0 : getIdNumber().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
		result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (getAddress() == null) {
			if (other.getAddress() != null)
				return false;
		} else if (!getAddress().equals(other.getAddress()))
			return false;
		if (getId() != other.getId())
			return false;
		if (getContactNumber() == null) {
			if (other.getContactNumber() != null)
				return false;
		} else if (!getContactNumber().equals(other.getContactNumber()))
			return false;
		if (getEmail() == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!getEmail().equals(other.getEmail()))
			return false;
		if (getIdNumber() == null) {
			if (other.getIdNumber() != null)
				return false;
		} else if (!getIdNumber().equals(other.getIdNumber()))
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getPosition() == null) {
			if (other.getPosition() != null)
				return false;
		} else if (!getPosition().equals(other.getPosition()))
			return false;
		if (getPassword() == null) {
			if (other.getPassword() != null)
				return false;
		} else if (!getPassword().equals(other.getPassword()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Employee employee) {
		if (getId() < employee.getId())
			return -1;
		if (getId() > employee.getId())
			return 1;
		return getIdNumber().compareTo(employee.getIdNumber());
	}
}
