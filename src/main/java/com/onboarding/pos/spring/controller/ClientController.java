package com.onboarding.pos.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.onboarding.pos.spring.config.model.Client;
import com.onboarding.pos.spring.service.ClientService;

@Controller
@RequestMapping(value = "/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView clients() {
		ModelAndView modelAndView = new ModelAndView("/view/clients.jsp");
		modelAndView.addObject("client", new Client());
		modelAndView.addObject("findAll", clientService.findAll());
		return modelAndView;
	}
	
}
