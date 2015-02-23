package com.onboarding.pos.spring.config.model;

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
@Table(name = "department", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Department implements EntityWithIntId<Department> {

	@Id
	@SequenceGenerator(name = "gen_department_id", sequenceName = "seq_department_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_department_id")
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "code", unique = true, nullable = false, length = 3)
	private String code;
	
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<InvoiceProduct> invoiceProducts = new HashSet<InvoiceProduct>(0);

	public Department() {
	}
	
	public Department(int id) {
		setId(id);
	}

	public Department(String code) {
		setCode(code);
	}

	public Department(String code, String name) {
		setCode(code);
		setName(name);
	}

	public Department(int id, String code, String name) {
		setId(id);
		setCode(code);
		setName(name);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		Validate.isTrue(code != null, "Department code cannot be null");
		Validate.isTrue(!code.isEmpty(), "Department code cannot be empty");
		Validate.isTrue(!code.trim().isEmpty(), "Department code cannot be filled with blank space(s)");
		Validate.isTrue(code.length() <= 3, "Department code cannot be longer than 3 characters");
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Validate.isTrue(name != null, "Department name cannot be null");
		Validate.isTrue(!name.isEmpty(), "Department name cannot be empty");
		Validate.isTrue(!name.trim().isEmpty(), "Department name cannot be filled with blank space(s)");
		Validate.isTrue(name.length() <= 30, "Department name cannot be longer than 30 characters");
		this.name = name;
	}
	
	public Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Department other = (Department) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", code=" + code + ", name=" + name + "]";
	}

	@Override
	public int compareTo(Department department) {
		if (getId() < department.getId())
			return -1;
		if (getId() > department.getId())
			return 1;
		return getCode().compareTo(department.getCode());
	}

}
