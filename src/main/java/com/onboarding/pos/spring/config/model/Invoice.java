package com.onboarding.pos.spring.config.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "invoice")
public class Invoice implements EntityWithIntId<Invoice> {

	@Id
	@SequenceGenerator(name = "gen_invoice_id", sequenceName = "seq_invoice_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_invoice_id")
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 29)
	private Date created;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private InvoiceStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "total", precision = 13, scale = 4)
	private BigDecimal total;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice", orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	private Set<InvoiceProduct> invoiceProducts = new HashSet<InvoiceProduct>(0);

	public Invoice() {
	}

	public Invoice(int id, Employee employee, Date created, InvoiceStatus status) {
		setId(id);
		setEmployee(employee);
		setCreated(created);
		setStatus(status);
	}

	public Invoice(int id, Employee employee, Client client, Date created, InvoiceStatus status) {
		setId(id);
		setEmployee(employee);
		setClient(client);
		setCreated(created);
		setStatus(status);
	}

	public Invoice(int id, Employee employee, Client client, Date created, InvoiceStatus status,
			PaymentMethod paymentMethod, BigDecimal total) {
		setId(id);
		setEmployee(employee);
		setClient(client);
		setCreated(created);
		setStatus(status);
		setPaymentMethod(paymentMethod);
		setTotal(total);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		Validate.isTrue(created != null, "Created date cannot be null");
		this.created = created;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		Validate.isTrue(employee != null, "Employee cannot be null");
		this.employee = employee;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		Validate.isTrue(client != null, "Client cannot be null");
		this.client = client;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		Validate.isTrue(status != null, "Status cannot be null");
		this.status = status;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + id;
		result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		Invoice other = (Invoice) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (id != other.id)
			return false;
		if (paymentMethod != other.paymentMethod)
			return false;
		if (status != other.status)
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		String statusToPrint, paymentMethodToPrint, totalToPrint;
		if (getStatus() == null) {
			statusToPrint = "-";
		} else {
			statusToPrint = getStatus().toString();
		}
		if (getPaymentMethod() == null) {
			paymentMethodToPrint = "-";
		} else {
			paymentMethodToPrint = getPaymentMethod().toString();
		}
		if (getTotal() == null) {
			totalToPrint = "-";
		} else {
			totalToPrint = decimalFormat.format(getTotal().doubleValue());
		}
		return String.format("%5s| %25s| %25s| %25s| %15s| %20s| %s", getId(), getCreated(),
				getEmployee().getIdNumber(), getClient().getIdNumber(), statusToPrint,
				paymentMethodToPrint, totalToPrint);
	}

	@Override
	public int compareTo(Invoice invoice) {
		if (getId() < invoice.getId())
			return -1;
		if (getId() > invoice.getId())
			return 1;
		return 0;
	}

}
