package com.onboarding.pos.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@RequestMapping( value = "/", method = RequestMethod.GET )
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("/view/index.jsp");
		return mav;
	}
	
}
