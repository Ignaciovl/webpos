package com.onboarding.pos.spring.webconfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.onboarding.pos.spring.config.BasicConfig;
import com.onboarding.pos.spring.config.MvcConfig;

public class WebInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

		context.register(BasicConfig.class, MvcConfig.class);
		servletContext.addListener(new ContextLoaderListener(context));

		AnnotationConfigWebApplicationContext dispatcherContext =
				new AnnotationConfigWebApplicationContext();
		ServletRegistration.Dynamic dispatcher;

		dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}