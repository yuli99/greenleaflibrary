package com.wei.greenleaflibrary.service;

import java.io.InputStream;
import java.util.List;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Publisher;


public interface BookService {
	
	void saveBook(Book book) throws Exception;
	
	void saveCoverImage(Book book, InputStream inputStream) throws Exception;
	
	void editBook(Book book) throws Exception;
	
	void removeBook(Book book) throws Exception;
	
	Book loadBookByBarcode(String barcode);
	
	boolean isBarcodeAvailable(String barcode);
	
	Book loadBookWithRecordsAndRatings(String barcode);
	
	List<Book> loadBooksByTitle(String title);
	
	List<Book> loadBooksByAuthor(String author);
	
	List<Book> loadBooksByKeyword(String keyword);
	
	List<Book> loadAllBooks();
	
	Publisher getPublisher(String publisherName) throws Exception;
		
}
