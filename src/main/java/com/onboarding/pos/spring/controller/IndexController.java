package com.onboarding.pos.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	private static final String INDEX_VIEW = "/view/index.jsp";

	@RequestMapping(value = { "/", "/index" })
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
		return modelAndView;
	}

}
