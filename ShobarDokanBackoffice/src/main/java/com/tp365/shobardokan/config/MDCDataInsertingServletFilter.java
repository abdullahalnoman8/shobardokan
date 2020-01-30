package com.tp365.shobardokan.config;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import com.tp365.shobardokan.model.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/**"})
@Slf4j
public class MDCDataInsertingServletFilter extends MDCInsertingServletFilter {
	private String [] excluded;
	@Override
	public void init(FilterConfig filterConfig) {
		excluded = new String[]{"/vendor/*", "/css/*", "/js/*", "/custom/*"};
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
				AuthenticatedUser user = (AuthenticatedUser) auth.getPrincipal();
				Integer authenticatedUserId = user != null ? user.getId() : null;
				MDC.put("userId", String.valueOf(authenticatedUserId));
			} else {
//				log.error("No authentication found in context");
			}
			super.doFilter(request, response, chain);
		} finally {
			MDC.remove("userId");
		}
	}
}
