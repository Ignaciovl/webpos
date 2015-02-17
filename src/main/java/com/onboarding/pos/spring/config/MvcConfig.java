package com.onboarding.pos.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration  
@EnableWebMvc  
@ComponentScan(basePackages = { "com.onboarding.pos.spring.controller" })
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/index.htm").setViewName("/view/index.jsp");
    }
	
	@Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}