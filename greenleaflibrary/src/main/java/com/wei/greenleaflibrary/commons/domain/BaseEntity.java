package com.wei.greenleaflibrary.commons.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 8637019877868994598L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && id != null) ? id
				.equals(((BaseEntity) other).id) : (other == this);
	}

	@Override
	public int hashCode() {
		return (id != null) ? (getClass().hashCode() + id.hashCode()) : super
				.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s - ID %d", getClass().getSimpleName(), id);
	}

	public Long getId() {
		return id;
	}

}
