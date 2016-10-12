package com.wei.greenleaflibrary.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.wei.greenleaflibrary.commons.domain.BaseEntity;


@Entity
@Table(name = "books")
@AttributeOverride(name = "id", column = @Column(name = "bookId"))
public class Book extends BaseEntity {
	private static final long serialVersionUID = 8459925260252436183L;
	
	@Pattern(regexp = "^[0-9]{13}$")
	private String barcode;
	
	@Size(min = 2)
	private String title;
	
	@Size(max = 1000)
	private String description;
	private String edition;
    private String imageUri;
	
    @Size(min = 1)
	private String authors;
	
	@ManyToOne
	@JoinColumn(name = "publisherId")
	private Publisher publisher;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
	private List<LendingRecord> records;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
	private List<Rating> ratings;
	
	public Book() {}
	
	public Book(String barcode, String title, String edition, String imageUri) {
		this.barcode = barcode;
		this.title = title;
		this.edition = edition;
		this.imageUri = imageUri;
	}
	
	// getters and setters
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<LendingRecord> getRecords() {
		return records;
	}
	
	public void setRecords(List<LendingRecord> records) {
		this.records = records;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	@Override
	public String toString() {
		return "book[" + barcode + "]";
	}

}
