package com.qpp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * picture entity
 * @author kevin liu
 *
 */
public class PictureInfo {
	
	private String photoId;
	
	private Long folderId;
	
	private Long userId;
	
	private String name;
	
	private String photoRemark;
	
	private String photoExp;
	
	private Integer width;
	
	private Integer height;
	
	private Date createDate;
	
	private Date modifyDate;
	
	private Set<Tag> tags = new HashSet<Tag>();

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoRemark() {
		return photoRemark;
	}

	public void setPhotoRemark(String photoRemark) {
		this.photoRemark = photoRemark;
	}

	public String getPhotoExp() {
		return photoExp;
	}

	public void setPhotoExp(String photoExp) {
		this.photoExp = photoExp;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
}
