package com.wei.greenleaflibrary.dao;

import java.util.List;

import com.wei.greenleaflibrary.commons.dao.GenericDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.User;


public interface LendingRecordDao extends GenericDao<LendingRecord, Long> {
	
	List<LendingRecord> findLendingRecordsByBook(Book book);
	
	List<LendingRecord> findLendingRecordsByUser(User user);

}
