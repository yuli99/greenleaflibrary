package com.wei.greenleaflibrary.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wei.greenleaflibrary.commons.dao.GenericJpaDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.User;


public class LendingRecordJpaDao extends GenericJpaDao<LendingRecord, Long> implements LendingRecordDao{

	public LendingRecordJpaDao() {
		super(LendingRecord.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<LendingRecord> findLendingRecordsByBook(Book book) {
		Assert.notNull(book);

		Query query = getEntityManager().createQuery(
				"select l from " + getPersistentClass().getSimpleName()
						+ " l where l.book = :book").setParameter(
				"book", book);

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<LendingRecord> findLendingRecordsByUser(User user) {
		Assert.notNull(user);

		Query query = getEntityManager().createQuery(
				"select l from " + getPersistentClass().getSimpleName()
						+ " l where l.user = :user").setParameter(
				"user", user);

		return query.getResultList();
	}
	
}
