package com.qpp.model;

// Generated Aug 22, 2014 1:22:34 PM by Hibernate Tools 3.4.0.CR1

/**
 * TUserEmail generated by hbm2java
 */
public class TUserEmail implements java.io.Serializable {

	private String id;
	private TUser TUser;
	private TMailPublish TMailPublish;

	public TUserEmail() {
	}

	public TUserEmail(String id, TUser TUser, TMailPublish TMailPublish) {
		this.id = id;
		this.TUser = TUser;
		this.TMailPublish = TMailPublish;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	public TMailPublish getTMailPublish() {
		return this.TMailPublish;
	}

	public void setTMailPublish(TMailPublish TMailPublish) {
		this.TMailPublish = TMailPublish;
	}

}