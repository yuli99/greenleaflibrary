package com.wei.greenleaflibrary.service;

import java.util.List;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Rating;
import com.wei.greenleaflibrary.domain.User;


public interface RatingService {
	
	void addRating(Rating rating) throws Exception;
	
	void updateRating(Rating rating) throws Exception;
	
	Rating loadRatingByBookAndUser(Book book, String userName);
	
	List<Rating> loadRatingsByBook(Book book);
	
	List<Rating> loadRatingsByUser(User user);
	
	List<Rating> loadAllRatings();

}
