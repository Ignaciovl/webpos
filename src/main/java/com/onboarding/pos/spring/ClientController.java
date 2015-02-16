package com.onboarding.pos.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.model.Client;
import com.onboarding.pos.spring.service.ClientService;
import com.onboarding.pos.spring.service.EntityService;

@Controller
public class ClientController implements EntityController<Client> {
	
	protected final Logger logger = Logger.getLogger(this.getClass());
	private ClientService clientService;
	
	public ClientService getService() {
		return clientService;
	}
	
	@Override
	@Autowired(required = true)
	@Qualifier(value = "clientService")
	public void setService(EntityService<Client> clientService) {
		this.clientService = (ClientService) clientService;
	}

	@Override
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("client", new Client());
		model.addAttribute("findAll", getService().findAll());
		return "client";
	}

	@Override
	@RequestMapping(value= "/client/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("client") Client client) {
		try {
			getService().create(client);
		} catch (EntityException e) {
			logger.error("Couldn't add client", e);
		}
		return "redirect:/clients";
	}

	@Override
	@RequestMapping(value= "/client/add", method = RequestMethod.POST)
	public String update(@ModelAttribute("client") Client client) {
		try {
			getService().update(client);
		} catch (EntityException e) {
			logger.error("Couldn't update client", e);
		}
		return "redirect:/clients";
	}

	@Override
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") int id) {
		Client client = new Client();
		client = getService().findById(id);
		try {
			getService().delete(client);
		} catch (EntityException e) {
			logger.error("Couldn't delete client", e);
		}
		return "redirect:/clients";
	}
	
	@RequestMapping("/remove/{idNumber}")
	public String remove(@PathVariable("idNumber") String idNumber) {
		Client client = new Client(idNumber);
		try {
			getService().delete(client);
		} catch (EntityException e) {
			logger.error("Couldn't delete client", e);
		}
		return "redirect:/clients";
	}

	@Override
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("client", getService().findById(id));
		model.addAttribute("findAll", getService().findAll());
		return "client";
	}
	
	@RequestMapping("/edit/{idNumber}")
	public String edit(@PathVariable("idNumber") String idNumber, Model model) {
		model.addAttribute("client", getService().findByIdNumber(idNumber));
		model.addAttribute("findAll", getService().findAll());
		return "client";
	}
}
