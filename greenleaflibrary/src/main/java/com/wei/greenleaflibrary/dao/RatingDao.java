package com.wei.greenleaflibrary.dao;

import java.util.List;

import com.wei.greenleaflibrary.commons.dao.GenericDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Rating;
import com.wei.greenleaflibrary.domain.User;


public interface RatingDao extends GenericDao<Rating, Long> {

	List<Rating> findRatingsByBook(Book book);
	
	List<Rating> findRatingsByUser(User user);
	
}
