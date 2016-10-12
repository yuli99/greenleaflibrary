package com.wei.greenleaflibrary.service;


public interface UserAuthenticationProviderService {

	String processUserAuthentication(String userName, String password);
	
	void cleanSecurityContext();

}