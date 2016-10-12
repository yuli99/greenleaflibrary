package com.wei.greenleaflibrary.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.wei.greenleaflibrary.commons.domain.BaseEntity;

@Entity
@Table(name = "publishers")
@AttributeOverride(name = "id", column = @Column(name = "publisherId"))
public class Publisher extends BaseEntity {
	private static final long serialVersionUID = -7233391870279681585L;
	
	@Size(min = 2)
	private String publisherName;
	
	@OneToMany(mappedBy = "publisher")
	private List<Book> books;
	
	public Publisher() {}
	
	public Publisher(String publisherName) {
		this.publisherName = publisherName;
	}

	// setters and getters
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return publisherName;
	}

}
