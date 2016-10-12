package com.wei.greenleaflibrary.ui;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.Rating;
import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.BookService;
import com.wei.greenleaflibrary.service.LendingRecordService;
import com.wei.greenleaflibrary.service.RatingService;
import com.wei.greenleaflibrary.service.UserService;


@ManagedBean
@SessionScoped
public class AccountManage implements Serializable{
	private static final long serialVersionUID = 9117139507858763136L;
	
	private String barcode;
	private Integer score;
	
	private List<LendingRecord> records;
	private List<Rating> ratings;
	private List<String> barcodes = new ArrayList<>();
	
	@ManagedProperty("#{userLogin.userName}")
	private String userName;

	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	@ManagedProperty("#{lendingRecordService}")
	private LendingRecordService lendingRecordService;
	
	@ManagedProperty("#{ratingService}")
	private RatingService ratingService;
	
	public void loadLendingRecords() {
		User user = userService.loadUserByUserName(userName);
		records = lendingRecordService.loadLendingRecordsByUser(user);
	}
	
	public void loadBookBarcodes() {
		User user = userService.loadUserByUserName(userName);
		for (LendingRecord r : lendingRecordService.loadLendingRecordsByUser(user)) {
			String b = r.getBook().getBarcode();
			if (!barcodes.contains(b)) {
				barcodes.add(b);
			}
		}
	}
	
	public String loadRatings() {
		User user = userService.loadUserByUserName(userName);
		ratings = ratingService.loadRatingsByUser(user);
		return "/pages/userRatingList.xhtml?faces-redirect=true";
	}
	
	public String rateBook() {
		Book book = bookService.loadBookByBarcode(barcode);
		User user = userService.loadUserByUserName(userName);
		
		Rating rating = ratingService.loadRatingByBookAndUser(book, userName);
		
		if (rating == null) {
			// create new rating
			rating = new Rating();
			rating.setBook(book);
			rating.setUser(user);
			rating.setScore(score.intValue());
			rating.setRateDate(new Timestamp(Calendar.getInstance()
				.getTime().getTime()));
			
			try {
				ratingService.addRating(rating);
			} catch (Exception exc) {
				FacesMessage message = constructFatalMessage(exc.getMessage(), null);
				getFacesContext().addMessage(null, message);
				return null;
			}	
		}
		else {
			// update existing rating
			rating.setScore(score);
			rating.setRateDate(new Timestamp(Calendar.getInstance()
				.getTime().getTime()));
			
			try {
				ratingService.updateRating(rating);
			} catch (Exception exc) {
				FacesMessage message = constructFatalMessage(exc.getMessage(), null);
				getFacesContext().addMessage(null, message);
				return null;
			}		
		}
		
		ratings = ratingService.loadRatingsByUser(user);
		cleanData();	
		return "/pages/userRatingList.xhtml?faces-redirct=true";
	}
	
	private void cleanData() {
		score = 0;
		barcode = "";
	}
	
	// getters and setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public List<String> getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(List<String> barcodes) {
		this.barcodes = barcodes;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public LendingRecordService getLendingRecordService() {
		return lendingRecordService;
	}

	public void setLendingRecordService(LendingRecordService lendingRecordService) {
		this.lendingRecordService = lendingRecordService;
	}

	public RatingService getRatingService() {
		return ratingService;
	}

	public void setRatingService(RatingService ratingService) {
		this.ratingService = ratingService;
	}

	// message constructors	
	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}
		
	// wrap static method calls
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
}
