package com.onboarding.pos.module;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.manager.ClientManager;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.util.SystemHelper;

public class ClientModule extends Module<Client> {
	private static final int LIST_ALL_CLIENTS_OPTION = 1;
	private static final int ADD_CLIENT_OPTION = 2;
	public static final int UPDATE_CLIENT_OPTION = 3;
	public static final int DELETE_CLIENT_OPTION = 4;
	private static final int EXIT_OPTION = 0;
	private static final int POSITIVE_INT = 1000;
	private static final String ID = "Id";
	private static final String NAME = "Name";
	private static final String ID_NUMBER = "Id. Number";
	private static final String CONTACT_NUMBER = "Contact Number";
	private static final String EMAIL = "Email";
	private static final String ADDRESS = "Address";

	private Logger logger;

	public ClientModule(final SystemHelper givenSystemHelper, final ClientManager givenClientManager) {
		super(givenSystemHelper, givenClientManager);
		setLogger(LogManager.getLogger(ClientManager.class.getName()));
	}

	public void init() {
		getSystemHelper().askPressEnter("\nPress Enter to continue");
		printMenu();
	}

	public void printMenu() {
		getSystemHelper().println("\nOptions:");
		getSystemHelper().println("	[ 1 ] List all clients");
		getSystemHelper().println("	[ 2 ] Add client");
		getSystemHelper().println("	[ 3 ] Update client");
		getSystemHelper().println("	[ 4 ] Delete client");
		getSystemHelper().println("	[ 0 ] Exit");
		int option = getSystemHelper().askOption(
				"\nSelect an option: ",
				new int[] { LIST_ALL_CLIENTS_OPTION, ADD_CLIENT_OPTION, UPDATE_CLIENT_OPTION,
						DELETE_CLIENT_OPTION, EXIT_OPTION },
				"Invalid option. Please select a valid option.");
		checkOption(option);
	}

	public void printAllClients() {
		List<Client> clients;
		clients = getManager().findAll();
		Collections.sort(clients);
		getSystemHelper().print("\n");
		getSystemHelper().println(
				String.format(" %-3s| %-15s| %-13s| %-15s| %-20s| %s", ID, NAME, ID_NUMBER,
						CONTACT_NUMBER, EMAIL, ADDRESS));
		for (Client client : clients) {
			getSystemHelper().println(client.toString());
		}
		getSystemHelper().askPressEnter("\nPress Enter to continue");
	}

	public Client addClient() {
		Client client = askForClientInformationForAdd();
		if (getSystemHelper().askYesOrNo(
				"\nAre you sure that you want to add client '" + client.getIdNumber()
						+ "'? (Y/N): ")) {
			if (getManager().findByIdNumber(client.getIdNumber()) != null) {
				getSystemHelper().println("\nCould not add client");
			} else {
				try {
					getManager().create(client);
				} catch (EntityException e) {
					logger.error("There was an error while creating the client", e);
					return null;
				}
				getSystemHelper().println("\nClient added successfully");
				getSystemHelper().askPressEnter("\nPress Enter to continue");
				return client;
			}
		} else {
			getSystemHelper().println("\nClient was not added");
		}
		getSystemHelper().askPressEnter("\nPress Enter to continue");
		return null;
	}

	public Client updateClient() {
		Client client = askForClientInformationForUpdate();
		if (client == null) {
			getSystemHelper().println("\nClient not found");
		} else {
			if (getSystemHelper().askYesOrNo(
					"\nAre you sure that you want to update client '" + client.getIdNumber()
							+ "'? (Y/N): ")) {
				try {
					getManager().update(client);
				} catch (EntityException e) {
					logger.error("There was an error while updating the client", e);
					return null;
				}
				getSystemHelper().println("\nClient updated successfully");
				getSystemHelper().askPressEnter("\nPress Enter to continue");
				return client;
			} else {
				getSystemHelper().println("\nClient was not updated");
			}
		}
		getSystemHelper().askPressEnter("\nPress Enter to continue");
		return null;
	}

	public Client deleteClient() {
		String idNumber = getSystemHelper().askString("\nClient Id Number: ");
		Client client = new Client(idNumber);
		if (getSystemHelper().askYesOrNo(
				"\nAre you sure that you want to delete client '" + client.getIdNumber()
						+ "'? (Y/N): ")) {
			if (getManager().findByIdNumber(client.getIdNumber()) == null) {
				getSystemHelper().println("\nClient not found");
			} else {
				try {
					getManager().delete(client);
				} catch (EntityException e) {
					logger.error("There was an error while deleting the client", e);
					return null;
				}
				getSystemHelper().println("\nClient deleted successfully");
				getSystemHelper().askPressEnter("\nPress Enter to continue");
				return client;
			}
		} else {
			getSystemHelper().println("\nClient was not deleted");
		}
		getSystemHelper().askPressEnter("\nPress Enter to continue");
		return null;
	}

	@Override
	public ClientManager getManager() {
		return (ClientManager) super.getManager();
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(final Logger newLogger) {
		logger = newLogger;
	}

	private void checkOption(final int option) {
		switch (option) {
		case LIST_ALL_CLIENTS_OPTION:
			doCaseListAllClients();
			break;
		case ADD_CLIENT_OPTION:
			doCaseAddClient();
			break;
		case UPDATE_CLIENT_OPTION:
			doCaseUpdateClient();
			break;
		case DELETE_CLIENT_OPTION:
			doCaseDeleteClient();
			break;
		default:
			doDefaultCase();
			break;
		}
	}

	private void doCaseListAllClients() {
		printAllClients();
		printMenu();
	}

	private void doCaseAddClient() {
		addClient();
		printMenu();
	}

	private void doCaseUpdateClient() {
		updateClient();
		printMenu();
	}

	private void doCaseDeleteClient() {
		deleteClient();
		printMenu();
	}

	private void doDefaultCase() {
		exit();
		getSystemHelper().println("\nExit aborted");
		getSystemHelper().askPressEnter("\nPress Enter to continue");
		printMenu();
	}

	private Client askForClientInformationForAdd() {
		String idNumber = getSystemHelper().askString("\nClient Id Number: ");
		while (idNumber.trim().isEmpty()) {
			idNumber = getSystemHelper().askString("\nInvalid Id Number, insert again: ");
		}
		String name = getSystemHelper().askString("Client Name: ");
		while (name.trim().isEmpty()) {
			name = getSystemHelper().askString("\nInvalid Name, insert again: ");
		}
		String contactNumber = getSystemHelper().askString("Client Contact Number: ");
		while (contactNumber.trim().isEmpty()) {
			contactNumber = getSystemHelper().askString("\nInvalid Contact Number, insert again: ");
		}
		String email = getSystemHelper().askString("Client Email: ");
		while (email.trim().isEmpty()) {
			email = getSystemHelper().askString("\nInvalid Email, insert again: ");
		}
		String address = getSystemHelper().askString("Client Address: ");
		while (address.trim().isEmpty()) {
			address = getSystemHelper().askString("\nInvalid Address, insert again: ");
		}
		Client client = new Client(POSITIVE_INT, name, idNumber, contactNumber, email, address);
		return client;
	}

	private Client askForClientInformationForUpdate() {//TODO fix¡¡¡
		String idNumber = getSystemHelper().askString("\nClient Id Number: ");
		Client client = getManager().findByIdNumber(idNumber);
		if (client == null) {
			return null;
		}
		getSystemHelper().println("\nLeave the line in blank if you don't want to update a field");
		String name = getSystemHelper().askString("Client Name: ");
		if (!name.trim().isEmpty()) {
			client.setName(name);
		}
		String contactNumber = getSystemHelper().askString("Client Contact Number: ");
		if (!contactNumber.trim().isEmpty()) {
			client.setContactNumber(contactNumber);
		}
		String email = getSystemHelper().askString("Client Email: ");
		if (!email.trim().isEmpty()) {
			client.setEmail(email);
		}
		String address = getSystemHelper().askString("Client Address: ");
		if (!address.trim().isEmpty()) {
			client.setAddress(address);
		}
		return client;
	}
}
