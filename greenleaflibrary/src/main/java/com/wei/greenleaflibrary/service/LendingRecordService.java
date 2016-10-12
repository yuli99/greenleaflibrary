package com.wei.greenleaflibrary.service;

import java.util.List;

import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.User;


public interface LendingRecordService {
	
	void saveLendingRecord(LendingRecord record) throws Exception;
	
	void editLendingRecord(LendingRecord record) throws Exception;
	
	LendingRecord loadLatestLendingRecord(Book book);
	
	List<LendingRecord> loadLendingRecordsByUser(User user);
	
	List<LendingRecord> loadAllLendingRecords();

}
