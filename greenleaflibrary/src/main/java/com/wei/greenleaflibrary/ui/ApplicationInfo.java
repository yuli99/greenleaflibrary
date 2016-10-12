package com.wei.greenleaflibrary.ui;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean
@ApplicationScoped
public class ApplicationInfo implements Serializable {
	private static final long serialVersionUID = -1446319414042367922L;
	
	private String slogan = "A book is a dream that you hold in your hand.  -- by Neil Gaiman";

	// getter and setter
	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

}
