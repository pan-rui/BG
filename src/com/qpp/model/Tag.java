package com.qpp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * tag entity
 * @author kevin liu
 *
 */
public class Tag {
	
	private Long id;
	
	private String name;
	
	private Long createUserId;
	
	private Long modifyUserId;
	
	private Date createDate;
	
	private Date modifyDate;
	
	private Set<PictureInfo> pictures = new HashSet<PictureInfo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Set<PictureInfo> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureInfo> pictures) {
		this.pictures = pictures;
	}
	
}
