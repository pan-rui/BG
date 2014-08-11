package com.qpp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AlbumFolder {
	
	private Long id;
	
	private String name;
	
	private Long folderParentId;
	
	private Long albumId;
	
	private Long userId;
	
	private String folderRemark;
	
	private Date createDate;
	
	private Date modifyDate;
	
	private AlbumFolder parent;
	
	private Set<AlbumFolder> children = new HashSet<AlbumFolder>();
	
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

	public Long getFolderParentId() {
		return folderParentId;
	}

	public void setFolderParentId(Long folderParentId) {
		this.folderParentId = folderParentId;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFolderRemark() {
		return folderRemark;
	}

	public void setFolderRemark(String folderRemark) {
		this.folderRemark = folderRemark;
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

	public AlbumFolder getParent() {
		return parent;
	}

	public void setParent(AlbumFolder parent) {
		this.parent = parent;
	}

	public Set<AlbumFolder> getChildren() {
		return children;
	}

	public void setChildren(Set<AlbumFolder> children) {
		this.children = children;
	}

	public Set<PictureInfo> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureInfo> pictures) {
		this.pictures = pictures;
	}

}
