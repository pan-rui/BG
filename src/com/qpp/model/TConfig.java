package com.qpp.model;

// Generated Aug 12, 2014 11:53:34 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TConfig generated by hbm2java
 */
public class TConfig implements java.io.Serializable {

	private String configName;
	private String configValue;
	private String module;
	private Date ctime;
	private Date utime;
	private String comment;
	private String state;

	public TConfig() {
	}

	public TConfig(String configName, String configValue, String module,
			Date ctime, Date utime, String comment, String state) {
		this.configName = configName;
		this.configValue = configValue;
		this.module = module;
		this.ctime = ctime;
		this.utime = utime;
		this.comment = comment;
		this.state = state;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return this.configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
