package com.onboarding.pos.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping( value = "/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("/view/index.jsp");
		return mav;
	}
	
	@RequestMapping( value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/view/index.jsp");
		return mav;
	}
	
}
