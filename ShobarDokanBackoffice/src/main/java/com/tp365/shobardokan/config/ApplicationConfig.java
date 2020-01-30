package com.tp365.shobardokan.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public FilterRegistrationBean registerMDCDataFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new MDCDataInsertingServletFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("MDCDataInsertingFilterBean");
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
}
