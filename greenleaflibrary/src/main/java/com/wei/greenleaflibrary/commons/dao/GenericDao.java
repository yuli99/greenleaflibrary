package com.wei.greenleaflibrary.commons.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<T, ID extends Serializable> {

	// save new record into database
	T save(T entity);

	// update existing record in the database
	T update(T entity);

	// delete record from database
	void delete(T entity);

	// load record by its id
	T findById(ID id);
	
	// load all records
	List<T> findAll();

}

