package com.onboarding.pos.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.onboarding.pos.spring.config.model.Client;
import com.onboarding.pos.spring.service.ClientService;

@Controller
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/clients/add", method = RequestMethod.GET)
	public ModelAndView addClientPage() {
		ModelAndView modelAndView = new ModelAndView("/view/add-client-form.jsp");
		modelAndView.addObject("client", new Client());
		return modelAndView;
	}

	@RequestMapping(value = "/clients/add", method = RequestMethod.POST)
	public ModelAndView addingClient(@ModelAttribute Client client) {
		ModelAndView modelAndView = new ModelAndView("/view/index.jsp");
		clientService.save(client);
		String message = "Client was successfully added";
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/clients/list")
	public ModelAndView listOfClients() {
		ModelAndView modelAndView = new ModelAndView("/view/list-of-clients.jsp");
		List<Client> clients = clientService.findAll();
		modelAndView.addObject("clients", clients);
		return modelAndView;
	}

	@RequestMapping(value = "/clients/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editClientsPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("/view/edit-client-form.jsp");
		Client client = clientService.findOne(id);
		modelAndView.addObject("client", client);
		return modelAndView;
	}

	@RequestMapping(value = "/clients/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editingClient(@ModelAttribute Client client, @PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("/view/index.jsp");
		clientService.save(client);
		String message = "Client was successfully edited";
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/clients/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteClient(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("/view/index.jsp");
		clientService.delete(id);
		String message = "Client was successfully deleted";
		modelAndView.addObject("message", message);
		return modelAndView;
	}
}
