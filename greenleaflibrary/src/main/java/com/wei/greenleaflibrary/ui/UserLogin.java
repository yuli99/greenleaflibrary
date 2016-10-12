package com.wei.greenleaflibrary.ui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.wei.greenleaflibrary.service.UserAuthenticationProviderService;


@ManagedBean
@SessionScoped
public class UserLogin implements Serializable {
	private static final long serialVersionUID = 2711058279186947309L;
	
	private String userName;
    private String password;
    
   @ManagedProperty("#{userAuthenticationProviderService}")
   private UserAuthenticationProviderService userAuthenticationProviderService;
    
    public String doLogin() {
       
        String loggedInStatus = userAuthenticationProviderService.processUserAuthentication(userName, password);
        
        if (loggedInStatus == null) {
        	return null;
        }
        else if (loggedInStatus.equalsIgnoreCase("admin_loggedIn")) {
	        return "/pages/adminHome.xhtml?faces-redirect=true";
	    }
        else {   
	        return "/pages/userAccount.xhtml?faces-redirect=true";
        }
    }
    
    public String doLogout() {
    	userAuthenticationProviderService.cleanSecurityContext();
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	userName = "";
    	return "/pages/login.xhtml?faces-redirect=true";
    }
    
    // getters and setters
   	public String getUserName() {
   		return userName;
   	}

   	public void setUserName(String userName) {
   		this.userName = userName;
   	}

   	public String getPassword() {
   		return password;
   	}

   	public void setPassword(String password) {
   		this.password = password;
   	}

	public UserAuthenticationProviderService getUserAuthenticationProviderService() {
		return userAuthenticationProviderService;
	}

	public void setUserAuthenticationProviderService(
			UserAuthenticationProviderService userAuthenticationProviderService) {
		this.userAuthenticationProviderService = userAuthenticationProviderService;
	}

}
