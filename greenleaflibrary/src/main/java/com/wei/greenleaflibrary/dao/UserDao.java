package com.wei.greenleaflibrary.dao;

import com.wei.greenleaflibrary.commons.dao.GenericDao;
import com.wei.greenleaflibrary.domain.User;


public interface UserDao extends GenericDao<User, Long> {
	
	User findUserByUserName(String userName);
	
	User findUserByCardNumber(String cardNumber);
	
	boolean isUserNameAvailable(String userName);
	
	boolean isCardNumberAvailable(String cardNumber);

}
