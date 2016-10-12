package com.wei.greenleaflibrary.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.dto.BookPresenter;
import com.wei.greenleaflibrary.service.BookService;


@ManagedBean
@SessionScoped
public class BookSearch implements Serializable {
	private static final long serialVersionUID = -4837633818386064724L;
	
	private String inputText;
	private List<BookPresenter> books;
	private String numberOfResults;
	private BookPresenter selectedBook;
	
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	public String byKeyword() {
		List<Book> bookList = bookService.loadBooksByKeyword(inputText);
		
		if (bookList == null || bookList.size() == 0) {
			numberOfResults = "0";
			return "/pages/noResultFound.xhtml?faces-redirect=true";
		}
		else {
			numberOfResults = Integer.toString(bookList.size());
			books = new ArrayList<BookPresenter>(bookList.size());
			for (Book b : bookList) {
				Book bookDetail = bookService.loadBookWithRecordsAndRatings(b.getBarcode());
				books.add(new BookPresenter(bookDetail));
			}
			return "/pages/foundResults.xhtml?faces-redirect=true";
		}
	}
	
	public String byTitle() {
		List<Book> bookList = bookService.loadBooksByTitle(inputText);
		
		if (bookList == null || bookList.size() == 0) {
			numberOfResults = "0";
			return "/pages/noResultFound.xhtml?faces-redirect=true";
		}
		else {
			numberOfResults = Integer.toString(bookList.size());
			books = new ArrayList<BookPresenter>(bookList.size());
			for (Book b : bookList) {
				Book bookDetail = bookService.loadBookWithRecordsAndRatings(b.getBarcode());
				books.add(new BookPresenter(bookDetail));
			}
			return "/pages/foundResults.xhtml?faces-redirect=true";
		}
	}
	
	public String byAuthor() {
		List<Book> bookList = bookService.loadBooksByAuthor(inputText);
		
		if (bookList == null || bookList.size() == 0) {
			numberOfResults = "0";
			return "/pages/noResultFound.xhtml?faces-redirect=true";
		}
		else {
			numberOfResults = Integer.toString(bookList.size());
			books = new ArrayList<BookPresenter>(bookList.size());
			for (Book b : bookList) {
				Book bookDetail = bookService.loadBookWithRecordsAndRatings(b.getBarcode());
				books.add(new BookPresenter(bookDetail));
			}
			return "/pages/foundResults.xhtml?faces-redirect=true";
		}
	}
	
	// getters and setters
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public List<BookPresenter> getBooks() {
		return books;
	}

	public void setBooks(List<BookPresenter> books) {
		this.books = books;
	}

	public String getNumberOfResults() {
		return numberOfResults;
	}
	
	public void setNumberOfResults(String numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	public BookPresenter getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(BookPresenter selectedBook) {
		this.selectedBook = selectedBook;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

}
