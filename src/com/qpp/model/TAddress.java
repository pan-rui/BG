package com.qpp.model;

// Generated Oct 8, 2014 12:26:51 PM by Hibernate Tools 3.4.0.CR1

import javax.validation.constraints.NotNull;

/**
 * TAddress generated by hbm2java
 */
public class TAddress implements java.io.Serializable {

	private long oid;
    @NotNull
	private long userId;
    @NotNull
	private String countryCode;
    @NotNull
	private String stateCode;
	private String city;
	private String area;
    @NotNull
	private String street;

	public TAddress() {
	}

	public TAddress(String countryCode, String stateCode, String street) {
		this.countryCode = countryCode;
		this.stateCode = stateCode;
		this.street = street;
	}

	public TAddress(String countryCode, String stateCode, String city,
			String area, String street) {
		this.countryCode = countryCode;
		this.stateCode = stateCode;
		this.city = city;
		this.area = area;
		this.street = street;
	}

	public long getOid() {
		return this.oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
