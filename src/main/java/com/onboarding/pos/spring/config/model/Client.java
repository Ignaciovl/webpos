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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "client", uniqueConstraints =
@UniqueConstraint(columnNames = "id_number"))
public class Client extends EntityWithIdNumber<Client> {

	@Id
	@SequenceGenerator(name = "gen_client_id",
	sequenceName = "seq_client_id", allocationSize = 1)
	@GeneratedValue(strategy =
	GenerationType.SEQUENCE, generator = "gen_client_id")
	@Column(name = "id", unique = true, nullable = false)
	@NotNull(message = "ID cannot be null")
	@Min(0)
	private int id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	private Set<Invoice> invoices = new HashSet<Invoice>(0);

	public Client() {
	}
	
	public Client(final String idNumber) {
		setIdNumber(idNumber);
	}

	public Client(final int givenId,
			final String givenName, final String givenIdNumber) {
		setId(givenId);
		setName(givenName);
		setIdNumber(givenIdNumber);
	}

	public Client(final int clientId, final String clientName,
			final String clientIdNumber, final String clientContactNumber,
			final String clientEmail, final String clientAddress) {
		this.setId(clientId);
		this.setName(clientName);
		this.setIdNumber(clientIdNumber);
		this.setContactNumber(clientContactNumber);
		this.setEmail(clientEmail);
		this.setAddress(clientAddress);
	}

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		this.id = newId;
	}

	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(final Set<Invoice> givenInvoices) {
		this.invoices = givenInvoices;
	}

	@Override
	public String toString() {
		return String.format(" %-3s| %-15s| %-13s| %-15s| %-20s| %s",
				getId(), getName(), getIdNumber(),
				getContactNumber(), getEmail(), getAddress());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (
				(getAddress() == null) ? 0 : getAddress().hashCode());
		result = prime * result + ((getContactNumber() == null)
				? 0 : getContactNumber().hashCode());
		result = prime * result + (
				(getEmail() == null) ? 0 : getEmail().hashCode());
		result = prime * result + (int)
				(getId() ^ (getId() >>> (prime + 1)));
		result = prime * result + (
				(getIdNumber() == null) ? 0 : getIdNumber().hashCode());
		result = prime * result + (
				(getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object client) {
		if (this == client) {
			return true;
		}
		if (client == null) {
			return false;
		}
		if (getClass() != client.getClass()) {
			return false;
		}
		return equalsOtherClientPartOne(client);
	}

	private boolean equalsOtherClientPartOne(final Object client) {
		Client otherClient = (Client) client;
		if (getAddress() == null) {
			if (otherClient.getAddress() != null) {
				return false;
			}
		} else if (!getAddress().equals(otherClient.getAddress())) {
			return false;
		}
		if (getContactNumber() == null) {
			if (otherClient.getContactNumber() != null) {
				return false;
			}
		} else if (!getContactNumber().equals(otherClient.getContactNumber())) {
			return false;
		}
		return equalsOtherClientPartTwo(otherClient, client);
	}

	private boolean equalsOtherClientPartTwo(
			final Client otherClient, final Object client) {
		if (getEmail() == null) {
			if (otherClient.getEmail() != null) {
				return false;
			}
		} else if (!getEmail().equals(otherClient.getEmail())) {
			return false;
		}
		if (getId() != otherClient.getId()) {
			return false;
		}
		if (getIdNumber() == null) {
			if (otherClient.getIdNumber() != null) {
				return false;
			}
		} else if (!getIdNumber().equals(otherClient.getIdNumber())) {
			return false;
		}
		return equalsOtherClientPartThree(otherClient, client);
	}

	private boolean equalsOtherClientPartThree(
			final Client otherClient, final Object client) {
		if (getName() == null) {
			if (otherClient.getName() != null) {
				return false;
			}
		} else if (!getName().equals(otherClient.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final Client client) {
		if (getId() < client.getId()) {
			return -1;
		} else if (getId() > client.getId()) {
			return 1;
		} else {
			return 0;
		}
	}
}
