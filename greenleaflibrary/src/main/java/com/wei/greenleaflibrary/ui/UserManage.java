package com.wei.greenleaflibrary.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.inputtext.InputText;

import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.UserService;


@ManagedBean
@SessionScoped
public class UserManage implements Serializable {
	private static final long serialVersionUID = 3212810459836921998L;
	
	private String userName;
	private String password;
	private String cardNumber;
	private String email;
	private String fullName;
	private Date dateOfBirth;
	
	private User user = new User();
	private List<User> users;

	@ManagedProperty("#{userService}")
    private UserService userService;
		
	public String addUser() {
		if (!userService.isUserNameAvailable(userName)) {
			FacesMessage message = constructErrorMessage(String.format(
					getMessageBundle().getString("userNameExistsMsg"), userName), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		if (!userService.isCardNumberAvailable(cardNumber)) {
			FacesMessage message = constructErrorMessage(String.format(
					getMessageBundle().getString("cardNumberExistsMsg"), cardNumber), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		user.setUserName(userName);
		user.setPassword(password);
		user.setCardNumber(cardNumber);
		user.setEmail(email);
		user.setFullName(fullName);
		user.setDateOfBirth(dateOfBirth);
		
		try {
			userService.saveUser(user);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
			
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public void cleanData() {
		userName = "";
		password = "";
		cardNumber = "";
		email = "";
		fullName = "";
		dateOfBirth = null;
		user = new User();
	}
	
	public String updateUserInfo() {
		if (cardNumber == null || cardNumber.isEmpty()) {
			FacesMessage message = constructInfoMessage( 
				String.format(getMessageBundle().getString("noUserLoadedMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		try {
			userService.updateUser(user);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public String deleteUser() {
		if (cardNumber == null || cardNumber.isEmpty()) {
			FacesMessage message = constructInfoMessage( 
				String.format(getMessageBundle().getString("noUserLoadedMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		try {
			userService.deleteUser(cardNumber);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public void loadUser() {
		user = userService.loadUserByCardNumber(cardNumber);
		
		if (user == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("incorrectCardNumberMsg")), null);
			getFacesContext().addMessage(null, message);
			return;
		}	
	}
	
	public String loadAll() {
		users = userService.loadAllUsers();
		return "/pages/adminUserList.xhtml?faces-redirect=true";
	}
	
	// for text auto-complete
	public List<String> completeCardNumber(String query) {
		List<String> filteredCardNumbers = new ArrayList<>();
		
		for (User u : userService.loadAllUsers()) {
			if (u.getCardNumber().startsWith(query)) {
				filteredCardNumbers.add(u.getCardNumber());
			}
		}
		
		return filteredCardNumbers;
	}
	
	// for ajax use
	public boolean checkUserName(AjaxBehaviorEvent event) {
		InputText inputText = (InputText) event.getSource();
		String userName = (String) inputText.getValue();

		boolean available = userService.isUserNameAvailable(userName);

		if (!available) {
			FacesMessage message = constructErrorMessage( 
					String.format(getMessageBundle().getString("userNameExistsMsg"), userName), null);
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage( 
				String.format(getMessageBundle().getString("userNameAvailableMsg"), userName), null);
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;
	}
	
	// for ajax use
	public boolean checkCardNumber(AjaxBehaviorEvent event) {
		InputText inputText = (InputText) event.getSource();
		String cardNumber = (String) inputText.getValue();

		boolean available = userService.isCardNumberAvailable(cardNumber);

		if (!available) {
			FacesMessage message = constructErrorMessage(
				String.format(getMessageBundle().getString("cardNumberExistsMsg"), cardNumber), null);
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage( 
				String.format(getMessageBundle().getString("cardNumberAvailableMsg"), cardNumber), null);
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// message constructors
	protected FacesMessage constructErrorMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}
	
	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}
	
	// wrap static method calls
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ResourceBundle getMessageBundle() {
		return ResourceBundle.getBundle("messages");
	}	
	
}
