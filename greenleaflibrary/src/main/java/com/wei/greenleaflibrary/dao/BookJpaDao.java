package com.wei.greenleaflibrary.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wei.greenleaflibrary.commons.dao.GenericJpaDao;
import com.wei.greenleaflibrary.domain.Book;


public class BookJpaDao extends GenericJpaDao<Book, Long> implements BookDao {

	public BookJpaDao() {
		super(Book.class);
	}
	
	@Override
	public Book findBookByBarcode(String barcode) {
		Assert.notNull(barcode);

		Book book = null;

		Query query = getEntityManager().createQuery(
				"select b from " + getPersistentClass().getSimpleName()
						+ " b where b.barcode = :barcode").setParameter(
				"barcode", barcode);
		try {
			book = (Book) query.getSingleResult();
		} catch (NoResultException exc) {
			exc.printStackTrace();
		}

		return book;
	}

	@Override
	public boolean isBarcodeAvailable(String barcode) {
		Assert.notNull(barcode);

		Query query = getEntityManager().createQuery(
				"select count(*) from " + getPersistentClass().getSimpleName()
						+ " b where b.barcode = :barcode").setParameter(
				"barcode", barcode);

		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Book> findBooksByTitle(String title) {
		Assert.notNull(title);

		Query query = getEntityManager().createQuery(
				"select b from " + getPersistentClass().getSimpleName()
						+ " b where b.title like :title").setParameter(
				"title", "%" + title + "%");

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Book> findBooksByKeyword(String keyword) {
		Assert.notNull(keyword);

		Query query = getEntityManager().createQuery(
				"select b from " + getPersistentClass().getSimpleName()
						+ " b where b.description like :keyword").setParameter(
				"keyword", "%" + keyword + "%");

		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Book> findBooksByAuthor(String author) {
		Assert.notNull(author);

		Query query = getEntityManager().createQuery(
				"select b from " + getPersistentClass().getSimpleName()
						+ " b where b.authors like :author").setParameter(
				"author", "%" + author + "%");

		return query.getResultList();
	}

}
