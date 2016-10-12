package com.wei.greenleaflibrary.dao;

import java.util.List;

import com.wei.greenleaflibrary.commons.dao.GenericDao;
import com.wei.greenleaflibrary.domain.Book;


public interface BookDao extends GenericDao<Book, Long> {
	
	Book findBookByBarcode(String barcode);
	
	boolean isBarcodeAvailable(String barcode);
	
	List<Book> findBooksByTitle(String title);
	
	List<Book> findBooksByKeyword(String keyword);
	
	List<Book> findBooksByAuthor(String author);
	
}
