package com.onboarding.pos.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "invoice_product")
public class InvoiceProduct {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "invoiceId", column = @Column(name = "invoice_id", nullable = false)),
			@AttributeOverride(name = "productCode", column = @Column(name = "product_code", nullable = false, length = 10)) })
	private InvoiceProductId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("invoiceId")
	@JoinColumn(name = "invoice_id", nullable = false, insertable = false, updatable = false)
	private Invoice invoice;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@Column(name = "product_name", nullable = false, length = 50)
	private String productName;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false, precision = 13, scale = 4)
	private BigDecimal price;

	public InvoiceProduct() {
	}

	public InvoiceProduct(InvoiceProductId id, Department department, String productName,
			int quantity, BigDecimal price) {
		setId(id);
		setDepartment(department);
		setProductName(productName);
		setQuantity(quantity);
		setPrice(price);
	}

	public InvoiceProduct(InvoiceProductId id, Invoice invoice, Department department,
			String productName, int quantity, BigDecimal price) {
		setId(id);
		setInvoice(invoice);
		setDepartment(department);
		setProductName(productName);
		setQuantity(quantity);
		setPrice(price);
	}

	public InvoiceProductId getId() {
		return id;
	}

	public void setId(InvoiceProductId id) {
		this.id = id;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		Validate.isTrue(department != null, "Department cannot be null");
		this.department = department;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		Validate.isTrue(productName != null, "Product description cannot be null");
		Validate.isTrue(!productName.isEmpty(), "Product description cannot be empty");
		Validate.isTrue(!productName.trim().isEmpty(),
				"Product description cannot be filled with blank space(s)");
		Validate.isTrue(productName.length() <= 50,
				"Product description cannot be longer than 50 characters");
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		Validate.isTrue(quantity > 0, "Quantity cannot be less than zero");
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		Validate.isTrue(price != null, "Price cannot be null");
		Validate.isTrue(price.compareTo(BigDecimal.ZERO) == 1,
				"Price cannot be less than or equals to zero");
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + quantity;
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
		InvoiceProduct other = (InvoiceProduct) obj;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		String priceToPrint, totalToPrint;
		if (getPrice() == null) {
			priceToPrint = "-";
			totalToPrint = "-";
		} else {
			priceToPrint = decimalFormat.format(getPrice().doubleValue());
			totalToPrint = decimalFormat.format(getPrice().multiply(new BigDecimal(getQuantity()))
					.doubleValue());
		}
		return String.format("%15s | %30s | %15s | %10s | %10s | %s", getId().getProductCode(),
				getProductName(), getDepartment().getCode(), getQuantity(), priceToPrint,
				totalToPrint);
	}

}
