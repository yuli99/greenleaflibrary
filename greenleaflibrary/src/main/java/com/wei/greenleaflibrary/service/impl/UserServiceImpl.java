package com.wei.greenleaflibrary.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wei.greenleaflibrary.dao.RoleDao;
import com.wei.greenleaflibrary.dao.UserDao;
import com.wei.greenleaflibrary.domain.Role;
import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.UserService;


@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private UserDao userDao;
	private RoleDao roleDao;

	@Override
	public void saveUser(User user) throws Exception {

		user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleDao.findRoleByName("ROLE_USER"));
		user.setRoles(roles);
		userDao.save(user);
	}

	@Override
	public void updateUser(User user) throws Exception {
		userDao.update(user);
	}

	@Override
	public void deleteUser(String cardNumber) throws Exception {		
		User user = userDao.findUserByCardNumber(cardNumber);		
		userDao.delete(user);	
	}

	@Override
	public User loadUserByUserName(String userName) {
		return userDao.findUserByUserName(userName);
	}

	@Override
	public User loadUserByCardNumber(String cardNumber) {
		return userDao.findUserByCardNumber(cardNumber);	
	}
	
	@Override
	public List<User> loadAllUsers() {
		return userDao.findAll();
	}

	@Override
	public boolean isUserNameAvailable(String userName) {	
		return userDao.isUserNameAvailable(userName);
	}
	
	@Override
	public boolean isCardNumberAvailable(String cardNumber) {
		return userDao.isCardNumberAvailable(cardNumber);
	}

	// create UserDetails instance required by spring security
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		User user = userDao.findUserByUserName(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("No such user with userName" + userName);
		}
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		
		org.springframework.security.core.userdetails.User userDetails = 
				new org.springframework.security.core.userdetails.User(user.getUserName(), 
						user.getPassword(), authorities);
		
		return userDetails;
	}

	// getters and setters
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
