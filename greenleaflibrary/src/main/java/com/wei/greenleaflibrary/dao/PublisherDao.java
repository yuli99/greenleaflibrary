package com.wei.greenleaflibrary.dao;

import com.wei.greenleaflibrary.commons.dao.GenericDao;
import com.wei.greenleaflibrary.domain.Publisher;

public interface PublisherDao extends GenericDao<Publisher, Long> {

	Publisher findPublisherByName(String publisherName);
	
}
