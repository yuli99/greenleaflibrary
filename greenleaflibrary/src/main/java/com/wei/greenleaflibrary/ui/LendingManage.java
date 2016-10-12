package com.wei.greenleaflibrary.ui;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.BookService;
import com.wei.greenleaflibrary.service.LendingRecordService;
import com.wei.greenleaflibrary.service.UserService;


@ManagedBean
@SessionScoped
public class LendingManage implements Serializable {
	private static final long serialVersionUID = 2114549372559936224L;
	
	private String cardNumber;
	private String barcode;
	
	private LendingRecord record = new LendingRecord();
	private List<LendingRecord> records;

	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	@ManagedProperty("#{lendingRecordService}")
	private LendingRecordService lendingRecordService;
	
	public String addRecord() {	
		User user = userService.loadUserByCardNumber(cardNumber);
		Book book = bookService.loadBookByBarcode(barcode);
		
		if (user == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("incorrectUserIdMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		if (book == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("incorrectBookIdMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		LendingRecord lastRecord = lendingRecordService.loadLatestLendingRecord(book);
		if (lastRecord != null && lastRecord.getReturnDate() == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("bookLentOutMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		record.setUser(user);
		record.setBook(book);
		record.setIssueDate(new Timestamp(Calendar.getInstance()
			.getTime().getTime()));	
		
		try {
			lendingRecordService.saveLendingRecord(record);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
	
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public void cleanData() {
		cardNumber = "";
		barcode = "";
		record = new LendingRecord();
	}
	
	public String updateRecord() {
		if (record == null || record.getBook() == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("noRecordLoadedMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		if (record.getReturnDate() != null) {
			FacesMessage message = constructInfoMessage(
					String.format(getMessageBundle().getString("bookReturnedMsg")), null);
				getFacesContext().addMessage(null, message);
				return null;
		}
			
		record.setReturnDate(new Timestamp(Calendar.getInstance()
				.getTime().getTime()));
	
		try {
			lendingRecordService.editLendingRecord(record);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
			
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public void loadBookLatestRec() {		
		Book book = bookService.loadBookByBarcode(barcode);
		if (book == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("incorrectBarcodeMsg")), null);
			getFacesContext().addMessage(null, message);
			return;
		}
		
		record = lendingRecordService.loadLatestLendingRecord(book);
		
		if (record == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("noRecFoundMsg")), null);
			getFacesContext().addMessage(null, message);
		}
	}
	
	public String loadAll() {
		records = lendingRecordService.loadAllLendingRecords();
		return "/pages/adminRecordList.xhtml?faces-redirct=true";
	}
	
	// for text auto-complete
	public List<String> completeCardNumber(String query) {
		List<String> filteredCardNumbers = new ArrayList<>();
			
		for (User u : userService.loadAllUsers()) {
			if (u.getCardNumber().startsWith(query)) {
				filteredCardNumbers.add(u.getCardNumber());
			}
		}
			
		return filteredCardNumbers;
	}
		
	// for text auto-complete
	public List<String> completeBarcode(String query) {
		List<String> filteredBarcodes = new ArrayList<>();
			
		for (Book b : bookService.loadAllBooks()) {
			if (b.getBarcode().startsWith(query)) {
				filteredBarcodes.add(b.getBarcode());
			}
		}
			
		return filteredBarcodes;
	}
	
	// getters and setters
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public LendingRecord getRecord() {
		return record;
	}

	public void setRecord(LendingRecord record) {
		this.record = record;
	}

	public List<LendingRecord> getRecords() {
		return records;
	}

	public void setRecords(List<LendingRecord> records) {
		this.records = records;
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

	// message constructors
	protected FacesMessage constructErrorMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}
	
	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}
		
	// wrap static method calls
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ResourceBundle getMessageBundle() {
		return ResourceBundle.getBundle("messages");
	}	
	
}
