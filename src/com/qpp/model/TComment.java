package com.qpp.model;

// Generated Sep 24, 2014 2:42:53 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TComment generated by hbm2java
 */
public class TComment implements java.io.Serializable {

	private long id;
	private long userId;
	private String message;
	private String image;
	private String super_;
	private Date ctime;
	private String type;

	public TComment() {
	}

	public TComment(String message, Date ctime) {
		this.message = message;
		this.ctime = ctime;
	}

	public TComment(long userId, String message, String image,
			String super_, Date ctime, String type) {
		this.userId = userId;
		this.message = message;
		this.image = image;
		this.super_ = super_;
		this.ctime = ctime;
		this.type = type;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSuper_() {
		return this.super_;
	}

	public void setSuper_(String super_) {
		this.super_ = super_;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
