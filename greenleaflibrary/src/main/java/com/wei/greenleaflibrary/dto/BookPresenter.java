package com.wei.greenleaflibrary.dto;

import java.io.Serializable;
import java.util.List;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.Rating;


public class BookPresenter implements Serializable {
	private static final long serialVersionUID = -5963173856297600647L;
	
	private String barcode;
	private String title;
	private String edition;
	private String description;
	private String authors;
	private String publisher;
	private String ratingTimes;
	private double ratingScore;
	private String currentStatus;
	private String imageUri; 
	
	// constructor
	public BookPresenter(Book book) {
		barcode = book.getBarcode();
		title = book.getTitle();
		edition = book.getEdition();
		description = book.getDescription();
		authors = book.getAuthors();
		
		publisher = book.getPublisher().getPublisherName();
		imageUri = book.getImageUri();
		
		List<Rating> ratingList = book.getRatings();
		if (ratingList == null || ratingList.size() < 1) {
			ratingTimes = "0";
			ratingScore = 0;
		}
		else {
			ratingTimes = Integer.toString(ratingList.size());
			
			double sum = 0;
			for (Rating rating : ratingList) {
				sum += rating.getScore();
			}
			ratingScore = Math.round(10 * sum / ratingList.size()) / 10;
		}
		
		List<LendingRecord> recList = book.getRecords();
		if (recList == null || recList.size() < 1) {
			currentStatus = "In library now";
		}
		else {
			LendingRecord latestRec = recList.get(0);
			for (LendingRecord rec : recList) {
				if (rec.getIssueDate().after(latestRec.getIssueDate())) {
					latestRec = rec;
				}
			}
			
			if (latestRec == null || latestRec.getReturnDate() != null) {
				currentStatus = "In library now";
			}
			else {
				currentStatus = "Lent out at " + latestRec.getIssueDate().toString();
			}	
		}		
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getRatingTimes() {
		return ratingTimes;
	}

	public double getRatingScore() {
		return ratingScore;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

}
