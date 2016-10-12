package com.wei.greenleaflibrary.service.impl;

import java.util.List;

import com.wei.greenleaflibrary.dao.LendingRecordDao;
import com.wei.greenleaflibrary.domain.Book;
import com.wei.greenleaflibrary.domain.LendingRecord;
import com.wei.greenleaflibrary.domain.User;
import com.wei.greenleaflibrary.service.LendingRecordService;


public class LendingRecordServiceImpl implements LendingRecordService {
	
	private LendingRecordDao lendingRecordDao;

	@Override
	public void saveLendingRecord(LendingRecord record) throws Exception {
		lendingRecordDao.save(record);
	}

	@Override
	public void editLendingRecord(LendingRecord record) throws Exception {
		lendingRecordDao.update(record);
	}

	@Override
	public LendingRecord loadLatestLendingRecord(Book book) {
		LendingRecord latestRec;
		List<LendingRecord> recList = lendingRecordDao.findLendingRecordsByBook(book);
		
		if (recList == null || recList.size() < 1) {
			return null;
		}
		
		latestRec = recList.get(0);
		for (LendingRecord rec : recList) {
			if (rec.getIssueDate().after(latestRec.getIssueDate())) {
				latestRec = rec;
			}
		}
		
		return latestRec;
	}

	@Override
	public List<LendingRecord> loadLendingRecordsByUser(User user) {
		return lendingRecordDao.findLendingRecordsByUser(user);
	}

	@Override
	public List<LendingRecord> loadAllLendingRecords() {
		return lendingRecordDao.findAll();
	}

	// getters and setters
	public LendingRecordDao getLendingRecordDao() {
		return lendingRecordDao;
	}

	public void setLendingRecordDao(LendingRecordDao lendingRecordDao) {
		this.lendingRecordDao = lendingRecordDao;
	}
	
}
