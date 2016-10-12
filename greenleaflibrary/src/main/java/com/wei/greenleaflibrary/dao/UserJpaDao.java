package com.wei.greenleaflibrary.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.wei.greenleaflibrary.commons.dao.GenericJpaDao;
import com.wei.greenleaflibrary.domain.User;


public class UserJpaDao extends GenericJpaDao<User, Long> implements UserDao {

	public UserJpaDao() {
		super(User.class);
	}
	
	@Override
	public User findUserByUserName(String userName) {
		Assert.notNull(userName);

		User user = null;

		Query query = getEntityManager().createQuery(
				"select u from " + getPersistentClass().getSimpleName()
						+ " u where u.userName = :userName").setParameter(
				"userName", userName);
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException exc) {
			exc.printStackTrace();
		}

		return user;
	}
	
	@Override
	public User findUserByCardNumber(String cardNumber) {
		Assert.notNull(cardNumber);

		User user = null;

		Query query = getEntityManager().createQuery(
				"select u from " + getPersistentClass().getSimpleName()
						+ " u where u.cardNumber = :cardNumber").setParameter(
				"cardNumber", cardNumber);
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException exc) {
			exc.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean isUserNameAvailable(String userName) {
		Assert.notNull(userName);

		Query query = getEntityManager().createQuery(
				"select count(*) from " + getPersistentClass().getSimpleName()
						+ " u where u.userName = :userName").setParameter(
				"userName", userName);

		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

	@Override
	public boolean isCardNumberAvailable(String cardNumber) {
		Assert.notNull(cardNumber);

		Query query = getEntityManager().createQuery(
				"select count(*) from " + getPersistentClass().getSimpleName()
						+ " u where u.cardNumber = :cardNumber").setParameter(
				"cardNumber", cardNumber);

		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

}
