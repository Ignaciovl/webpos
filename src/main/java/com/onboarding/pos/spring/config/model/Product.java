package com.onboarding.pos.spring.config.model;

import java.math.BigDecimal;

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
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
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
