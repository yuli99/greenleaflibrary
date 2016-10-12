package com.wei.greenleaflibrary.ui;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.model.UploadedFile;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.service.BookService;


@ManagedBean
@SessionScoped
public class BookManage implements Serializable {
	private static final long serialVersionUID = -7584230892154939683L;

	private String barcode;
	private String title;
	private String description;
	private String edition;
	private String authors;
	private String publisherName;
	private UploadedFile file;
	
	private Book book = new Book();
	private List<Book> books;
	
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	public String addBook() {	
		if (!bookService.isBarcodeAvailable(barcode)) {
			FacesMessage message = constructErrorMessage(String.format(
					getMessageBundle().getString("barcodeExistsMsg"), barcode), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		book.setBarcode(barcode);
		book.setTitle(title);
		book.setDescription(description);
		book.setEdition(edition);
		book.setAuthors(authors);
		
		try {
			book.setPublisher(bookService.getPublisher(publisherName));
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		book.setImageUri("nocover.jpg");
		
		try {	
			bookService.saveBook(book);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		return "/pages/adminAddBook2.xhtml?faces-redirct=true";
	}
	
	public void upload() {
		if (file == null) {
			FacesMessage message = constructErrorMessage(String.format(
					getMessageBundle().getString("selectImageMsg")), null);
			getFacesContext().addMessage(null, message);
			return;
		}
		
		if (book == null || book.getId() == null) {
			FacesMessage message = constructErrorMessage(String.format(
					getMessageBundle().getString("selectBookMsg")), null);
			getFacesContext().addMessage(null, message);
			return;
		}
		
		try {
			InputStream inputStream = file.getInputstream();
			bookService.saveCoverImage(book, inputStream);
			FacesMessage message = constructInfoMessage(String.format(
					getMessageBundle().getString("addImageSuccessMsg")), null);
			getFacesContext().addMessage(null, message);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return;
		}
	}
	
	public void cleanData() {
		barcode = "";
		title = "";
		description = "";
		edition = "";
		authors = "";
		publisherName = "";
		file = null; 
		book = new Book();
	}
	
	public String editBook() {
		if (book.getBarcode().isEmpty()) {
			FacesMessage message = constructInfoMessage(
					String.format(getMessageBundle().getString("noBookLoadedMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		try {
			bookService.editBook(book);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public String deleteBook() {
		if (book.getBarcode().isEmpty()) {
			FacesMessage message = constructInfoMessage(
					String.format(getMessageBundle().getString("noBookLoadedMsg")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		try {
			bookService.removeBook(book);
		} catch (Exception exc) {
			FacesMessage message = constructFatalMessage(exc.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		
		return "/pages/adminHome.xhtml?faces-redirct=true";
	}
	
	public void loadBook() {
		book = bookService.loadBookByBarcode(barcode);
		
		if (book == null) {
			FacesMessage message = constructInfoMessage(
				String.format(getMessageBundle().getString("incorrectBarcodeMsg")), null);
			getFacesContext().addMessage(null, message);
		}
	}
	
	public String loadAll() {
		books = bookService.loadAllBooks();
		return "/pages/adminBookList.xhtml?faces-redirect=true";
	}
	
	// for ajax use
	public boolean checkBarcode(AjaxBehaviorEvent event) {
		InputText inputText = (InputText) event.getSource();
		String barcode = (String) inputText.getValue();

		boolean available = bookService.isBarcodeAvailable(barcode);

		if (!available) {
			FacesMessage message = constructErrorMessage(
				String.format(getMessageBundle().getString("barcodeExistsMsg"), barcode), null);
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage( 
				String.format(getMessageBundle().getString("barcodeAvailableMsg"), barcode), null);
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;
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

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
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
