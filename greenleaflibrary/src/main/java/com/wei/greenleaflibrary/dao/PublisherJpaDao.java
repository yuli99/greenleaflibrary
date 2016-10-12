package com.wei.greenleaflibrary.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.wei.greenleaflibrary.commons.dao.GenericJpaDao;
import com.wei.greenleaflibrary.domain.Publisher;


public class PublisherJpaDao extends GenericJpaDao<Publisher, Long> implements PublisherDao {
	
	public PublisherJpaDao() {
		super(Publisher.class);
	}

	@Override
	public Publisher findPublisherByName(String publisherName) {
		Assert.notNull(publisherName);

		Publisher publisher = null;

		Query query = getEntityManager().createQuery(
				"select p from " + getPersistentClass().getSimpleName()
						+ " p where p.publisherName = :publisherName").setParameter(
				"publisherName", publisherName);
		try {
			publisher = (Publisher) query.getSingleResult();
		} catch (NoResultException exc) {
			exc.printStackTrace();
		}

		return publisher;
	}

}
