package com.qpp.model;

// Generated Sep 24, 2014 2:42:53 PM by Hibernate Tools 3.4.0.CR1

/**
 * TOpenidInfo generated by hbm2java
 */
public class TOpenidInfo implements java.io.Serializable {

	private long oid;

    private String openId;
	private String siteId;
	private long userId;

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

	public TOpenidInfo() {
	}

	public TOpenidInfo(String openId, String siteId,long userId) {
		this.siteId = siteId;
		this.openId = openId;
        this.userId=userId;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
