package com.wei.greenleaflibrary.service.impl;

import java.util.List;

import com.wei.greenleaflibrary.dao.RatingDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Rating;
import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.RatingService;


public class RatingServiceImpl implements RatingService {
	
	private RatingDao ratingDao;

	@Override
	public void addRating(Rating rating) throws Exception {
		ratingDao.save(rating);
	}

	@Override
	public void updateRating(Rating rating) throws Exception {
		ratingDao.update(rating);
	}

	@Override
	public Rating loadRatingByBookAndUser(Book book, String userName) {
		for (Rating r : ratingDao.findRatingsByBook(book)) {
			if (r.getUser().getUserName().equals(userName)) {
				return r;
			}
		}
		
		return null;
	}

	@Override
	public List<Rating> loadRatingsByBook(Book book) {
		return ratingDao.findRatingsByBook(book);
	}

	@Override
	public List<Rating> loadRatingsByUser(User user) {
		return ratingDao.findRatingsByUser(user);
	}

	@Override
	public List<Rating> loadAllRatings() {
		return ratingDao.findAll();
	}

	// getters and setters
	public RatingDao getRatingDao() {
		return ratingDao;
	}

	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}

}
