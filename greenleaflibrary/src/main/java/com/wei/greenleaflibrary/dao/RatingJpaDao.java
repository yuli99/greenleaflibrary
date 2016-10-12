package com.wei.greenleaflibrary.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wei.greenleaflibrary.commons.dao.GenericJpaDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Rating;
import com.wei.greenleaflibrary.domain.User;

public class RatingJpaDao extends GenericJpaDao<Rating, Long> implements RatingDao{

	public RatingJpaDao() {
		super(Rating.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Rating> findRatingsByBook(Book book) {
		Assert.notNull(book);

		Query query = getEntityManager().createQuery(
				"select r from " + getPersistentClass().getSimpleName()
						+ " r where r.book = :book").setParameter(
				"book", book);
		
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Rating> findRatingsByUser(User user) {
		Assert.notNull(user);

		Query query = getEntityManager().createQuery(
				"select r from " + getPersistentClass().getSimpleName()
						+ " r where r.user = :user").setParameter(
				"user", user);
		
		return query.getResultList();
	}
	
}
