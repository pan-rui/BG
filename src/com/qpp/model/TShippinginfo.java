package com.qpp.model;

// Generated Aug 22, 2014 1:22:34 PM by Hibernate Tools 3.4.0.CR1

/**
 * TShippinginfo generated by hbm2java
 */
public class TShippinginfo implements java.io.Serializable {

	private String shippingCode;
	private String shippingDesc;
	private short priority;
	private Boolean valid;

	public TShippinginfo() {
	}

	public TShippinginfo(String shippingCode, String shippingDesc,
			short priority) {
		this.shippingCode = shippingCode;
		this.shippingDesc = shippingDesc;
		this.priority = priority;
	}

	public TShippinginfo(String shippingCode, String shippingDesc,
			short priority, Boolean valid) {
		this.shippingCode = shippingCode;
		this.shippingDesc = shippingDesc;
		this.priority = priority;
		this.valid = valid;
	}

	public String getShippingCode() {
		return this.shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getShippingDesc() {
		return this.shippingDesc;
	}

	public void setShippingDesc(String shippingDesc) {
		this.shippingDesc = shippingDesc;
	}

	public short getPriority() {
		return this.priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}
