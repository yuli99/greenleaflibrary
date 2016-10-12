package com.wei.greenleaflibrary.service.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.wei.greenleaflibrary.dao.BookDao;
import com.wei.greenleaflibrary.dao.LendingRecordDao;
import com.wei.greenleaflibrary.dao.PublisherDao;
import com.wei.greenleaflibrary.dao.RatingDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.Publisher;
import com.wei.greenleaflibrary.service.BookService;


@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class BookServiceImpl implements BookService {
	
	private BookDao bookDao;
	private PublisherDao publisherDao;
	private LendingRecordDao lendingRecordDao;
	private RatingDao ratingDao;

	@Override
	public void saveBook(Book book) throws Exception {
		bookDao.save(book);	
	}

	@Override
	public void saveCoverImage(Book book, InputStream inputStream)
			throws Exception {	
		File directory = new File("workspace/greenleaflibrary/src/main/webapp/resources/bookcovers");
		String imageName = book.getBarcode() + ".jpg";
		File file = new File(directory.getAbsolutePath(), imageName);
		Files.copy(inputStream, file.toPath());
		
		book.setImageUri(imageName);
		bookDao.update(book);
	}

	@Override
	public void editBook(Book book) throws Exception {
		bookDao.update(book);
	}

	@Override
	public void removeBook(Book book) throws Exception {
		if (!book.getImageUri().equalsIgnoreCase("nocover.jpg")) {
			File directory = new File("workspace/greenleaflibrary/src/main/webapp/resources/bookcovers");
			File file = new File(directory.getAbsolutePath(), book.getImageUri());
			Path path = file.toPath();
        
			if(Files.exists(path)) {
				Files.delete(path);	
			}
			else {
				System.out.println("Invalid Cover Image Path!");
				System.out.println(path.toString());
				return;
			}
		}
		
		bookDao.delete(book);
	}

	@Override
	public Book loadBookByBarcode(String barcode) {
		return bookDao.findBookByBarcode(barcode);
	}
	
	@Override
	public boolean isBarcodeAvailable(String barcode) {
		return bookDao.isBarcodeAvailable(barcode);
	}

	@Override
	public Book loadBookWithRecordsAndRatings(String barcode) {
		Book book = bookDao.findBookByBarcode(barcode);
		if (book != null) {
			book.setRecords(lendingRecordDao.findLendingRecordsByBook(book));
			book.setRatings(ratingDao.findRatingsByBook(book));
		}
		return book;
	}

	@Override
	public List<Book> loadBooksByTitle(String title) {
		return bookDao.findBooksByTitle(title);
	}

	@Override
	public List<Book> loadBooksByAuthor(String author) {
		return bookDao.findBooksByAuthor(author);
	}

	@Override
	public List<Book> loadBooksByKeyword(String keyword) {
		return bookDao.findBooksByKeyword(keyword);
	}

	@Override
	public List<Book> loadAllBooks() {
		return bookDao.findAll();
	}

	@Override
	public Publisher getPublisher(String publisherName) throws Exception {
		
		Publisher publisher = publisherDao.findPublisherByName(publisherName);
		
		if (publisher == null) {
			publisher = new Publisher(publisherName);
			publisherDao.save(publisher);
		}

		return publisher;
	}

	// getters and setters
	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
	public PublisherDao getPublisherDao() {
		return publisherDao;
	}

	public void setPublisherDao(PublisherDao publisherDao) {
		this.publisherDao = publisherDao;
	}

	public LendingRecordDao getLendingRecordDao() {
		return lendingRecordDao;
	}

	public void setLendingRecordDao(LendingRecordDao lendingRecordDao) {
		this.lendingRecordDao = lendingRecordDao;
	}

	public RatingDao getRatingDao() {
		return ratingDao;
	}

	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}
				
}
