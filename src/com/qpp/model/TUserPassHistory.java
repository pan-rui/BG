package com.qpp.model;

// Generated Sep 24, 2014 2:42:53 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TUserPassHistory generated by hbm2java
 */
public class TUserPassHistory implements java.io.Serializable {

	private long oid;
	private long userId;
	private Date ctime;
	private String password;

	public TUserPassHistory() {
	}

	public TUserPassHistory(long userId, Date ctime) {
		this.userId = userId;
		this.ctime = ctime;
	}

	public TUserPassHistory(long userId, Date ctime, String password) {
		this.userId = userId;
		this.ctime = ctime;
		this.password = password;
	}

	public long getOid() {
		return this.oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
