package com.wei.greenleaflibrary.domain;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.wei.greenleaflibrary.commons.domain.BaseEntity;

@Entity
@Table(name = "ratings")
@AttributeOverride(name = "id", column = @Column(name = "ratingId"))
public class Rating extends BaseEntity{
	private static final long serialVersionUID = 6440142954523998947L;
	
	@Min(0)
	@Max(5)
	private int score;
	
	private Timestamp rateDate;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "bookId", nullable = false)
	private Book book;
	
	public Rating() {}
	
	public Rating(Book book, int score) {
		this.book = book;
		this.score = score;
	}

	// getters and setters
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Timestamp getRateDate() {
		return rateDate;
	}

	public void setRateDate(Timestamp rateDate) {
		this.rateDate = rateDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
