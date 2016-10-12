package com.wei.greenleaflibrary.service.impl;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wei.greenleaflibrary.service.UserAuthenticationProviderService;


@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserAuthenticationProviderServiceImpl 
		implements UserAuthenticationProviderService {
	
	private AuthenticationManager authenticationManager;

	@Override
	public String processUserAuthentication(String userName, String password) {
		String loggedInStatus;

		try {
			Authentication request = new UsernamePasswordAuthenticationToken(userName, password);
			Authentication result = authenticationManager.authenticate(request);
			
			Set<String> roles = AuthorityUtils.authorityListToSet(result.getAuthorities());
			if (roles.contains("ROLE_ADMIN")) {
				loggedInStatus = "admin_loggedIn";
			}
			else {
				loggedInStatus = "user_loggedIn";
			}
			
			SecurityContextHolder.getContext().setAuthentication(result);
			
			return loggedInStatus;
			
		} catch (AuthenticationException exc) {
			FacesMessage message = constructErrorMessage(exc.getMessage(), null); 
			getFacesContext().addMessage(null, message);

			return null;
		}
	}

	@Override
	public void cleanSecurityContext() {
		SecurityContextHolder.clearContext();	
	}

	// getter and setter
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	// message constructor
	protected FacesMessage constructErrorMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}

	// wrap static method call
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
}
