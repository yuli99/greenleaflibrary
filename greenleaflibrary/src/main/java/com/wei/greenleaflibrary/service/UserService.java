package com.wei.greenleaflibrary.service;

import java.util.List;

import com.wei.greenleaflibrary.domain.User;


public interface UserService {
	
	void saveUser(User user) throws Exception;
	
	void updateUser(User user) throws Exception;
	
	void deleteUser(String cardNumber) throws Exception;
	
	User loadUserByUserName(String userName);
	
	User loadUserByCardNumber(String cardNumber);
	
	List<User> loadAllUsers();
	
	boolean isUserNameAvailable(String userName);
	
	boolean isCardNumberAvailable(String cardNumber);
	
}
