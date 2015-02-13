package com.onboarding.pos.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

public class Product implements EntityWithIntId<Product> {

	private int id;
	private String code;
	private String name;
	private String departmentCode;
	private BigDecimal price;

	public Product() {
	}

	public Product(int id) {
		setId(id);
	}
	
	public Product(String code) {
		setCode(code);
	}

	public Product(int id, String code, String name, String departmentCode, BigDecimal price) {
		setId(id);
		setCode(code);
		setName(name);
		setDepartmentCode(departmentCode);
		setPrice(price);
	}
	
	public Product(String code, String name, String departmentCode, BigDecimal price) {
		setCode(code);
		setName(name);
		setDepartmentCode(departmentCode);
		setPrice(price);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		Validate.isTrue(id > 0, "Product ID cannot be negative or zero");
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		Validate.isTrue(code != null, "Product code cannot be null");
		Validate.isTrue(!code.isEmpty(), "Product code cannot be empty");
		Validate.isTrue(!code.trim().isEmpty(), "Product code cannot be filled with blank space(s)");
		Validate.isTrue(code.length() <= 10, "Product code cannot be longer than 10 characters");
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Validate.isTrue(name != null, "Product name cannot be null");
		Validate.isTrue(!name.isEmpty(), "Product name cannot be empty");
		Validate.isTrue(!name.trim().isEmpty(),
				"Product name cannot be filled with blank space(s)");
		Validate.isTrue(name.length() <= 50,
				"Product name cannot be longer than 50 characters");
		this.name = name;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		Validate.isTrue(departmentCode != null, "Product department code cannot be null");
		Validate.isTrue(!departmentCode.isEmpty(), "Product department code cannot be empty");
		Validate.isTrue(!departmentCode.trim().isEmpty(),
				"Product department code cannot be filled with blank space(s)");
		Validate.isTrue(departmentCode.length() <= 3,
				"Product department code cannot be longer than 3 characters");
		this.departmentCode = departmentCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		Validate.isTrue(price != null, "Product price cannot be null");
		Validate.isTrue(price.compareTo(BigDecimal.ZERO) == 1,
				"Product price cannot be less than or equals to zero");
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((departmentCode == null) ? 0 : departmentCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + id;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Product other = (Product) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (departmentCode == null) {
			if (other.departmentCode != null)
				return false;
		} else if (!departmentCode.equals(other.departmentCode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (id != other.id)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name
				+ ", departmentCode=" + departmentCode + ", price=" + price + "]";
	}

	@Override
	public int compareTo(Product product) {
		if (getId() < product.getId())
			return -1;
		if (getId() > product.getId())
			return 1;
		return getCode().compareTo(product.getCode());
	}

}
