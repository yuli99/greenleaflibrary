package com.wei.greenleaflibrary.domain;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wei.greenleaflibrary.commons.domain.BaseEntity;


@Entity
@Table(name = "lending_records")
@AttributeOverride(name = "id", column = @Column(name = "recordId"))
public class LendingRecord extends BaseEntity {
	private static final long serialVersionUID = 447269770388537216L;
	
	private Timestamp issueDate;
	private Timestamp returnDate;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "bookId", nullable = false)
	private Book book;

	// getters and setters
	public Timestamp getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Timestamp issueDate) {
		this.issueDate = issueDate;
	}

	public Timestamp getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
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
