package com.onboarding.pos.spring.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.onboarding.pos.spring.config.model.Client;
import com.onboarding.pos.spring.service.ClientService;

@Controller
public class ClientController {

	private static final String ADD_CLIENTS_PATH = "/clients/add";
	private static final String ADD_CLIENTS_VIEW = "/view/add-client-form.jsp";

	private static final String CLIENTS_LIST_VIEW = "/view/list-of-clients.jsp";
	private static final String CLIENTS_LIST_PATH = "/clients/list";

	private static final String EDIT_CLIENTS_PATH = "/clients/edit/";
	private static final String EDIT_CLIENTS_VIEW = "/view/edit-client-form.jsp";

	private static final String DELETE_CLIENTS_PATH = "/clients/delete/";

	@Autowired
	private ClientService clientService;

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(final ClientService givenclientService) {
		this.clientService = givenclientService;
	}

	@RequestMapping(value = ADD_CLIENTS_PATH, method = RequestMethod.GET)
	public ModelAndView addClientPage() {
		ModelAndView modelAndView = new ModelAndView(ADD_CLIENTS_VIEW);
		modelAndView.addObject("client", new Client());
		return modelAndView;
	}

	@RequestMapping(value = ADD_CLIENTS_PATH, method = RequestMethod.POST)
	public ModelAndView addingClient(@ModelAttribute @Valid final Client client,
			final BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView(ADD_CLIENTS_VIEW);
		}
		ModelAndView modelAndView = new ModelAndView(CLIENTS_LIST_VIEW);
		getClientService().save(client);
		String message = "Client was successfully added";
		modelAndView.addObject("message", message);
		List<Client> clients = getClientService().findAll();
		Collections.sort(clients);
		modelAndView.addObject("clients", clients);
		return modelAndView;
	}

	@RequestMapping(value = CLIENTS_LIST_PATH)
	public ModelAndView listOfClients() {
		ModelAndView modelAndView = new ModelAndView(CLIENTS_LIST_VIEW);
		List<Client> clients = getClientService().findAll();
		Collections.sort(clients);
		modelAndView.addObject("clients", clients);
		return modelAndView;
	}

	@RequestMapping(value = EDIT_CLIENTS_PATH + "{id}", method = RequestMethod.GET)
	public ModelAndView editClientsPage(@PathVariable final Integer id) {
		ModelAndView modelAndView = new ModelAndView(EDIT_CLIENTS_VIEW);
		Client client = getClientService().findOne(id);
		modelAndView.addObject("client", client);
		return modelAndView;
	}

	@RequestMapping(value = EDIT_CLIENTS_PATH + "{id}", method = RequestMethod.POST)
	public ModelAndView editingClient(@ModelAttribute @Valid final Client client,
			final BindingResult bindingResult, @PathVariable final Integer id) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView(EDIT_CLIENTS_VIEW);
		}
		ModelAndView modelAndView = new ModelAndView(CLIENTS_LIST_VIEW);
		getClientService().save(client);
		String message = "Client was successfully edited";
		modelAndView.addObject("message", message);
		List<Client> clients = getClientService().findAll();
		Collections.sort(clients);
		modelAndView.addObject("clients", clients);
		return modelAndView;
	}

	@RequestMapping(value = DELETE_CLIENTS_PATH + "{id}", method = RequestMethod.GET)
	public ModelAndView deleteClient(@PathVariable final Integer id) {
		ModelAndView modelAndView = new ModelAndView(CLIENTS_LIST_VIEW);
		getClientService().delete(id);
		String message = "Client was successfully deleted";
		modelAndView.addObject("message", message);
		List<Client> clients = getClientService().findAll();
		Collections.sort(clients);
		modelAndView.addObject("clients", clients);
		return modelAndView;
	}
}
