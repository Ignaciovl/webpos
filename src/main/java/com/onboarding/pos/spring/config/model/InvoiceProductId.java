package com.onboarding.pos.spring.config.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InvoiceProductId implements Serializable {

	private static final long serialVersionUID = 1149371455903842362L;

	@Column(name = "invoice_id", nullable = false)
	private int invoiceId;
	@Column(name = "product_code", nullable = false, length = 10)
	private String productCode;

	public InvoiceProductId() {
	}

	public InvoiceProductId(int invoiceId, String productCode) {
		setInvoiceId(invoiceId);
		setProductCode(productCode);
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + invoiceId;
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
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
		InvoiceProductId other = (InvoiceProductId) obj;
		if (invoiceId != other.invoiceId)
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceProductId [invoiceId=" + invoiceId + ", productCode=" + productCode + "]";
	}

}
