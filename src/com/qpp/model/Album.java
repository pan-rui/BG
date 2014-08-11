package com.qpp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * photo entity
 * @author kevin liu
 *
 */
public class Album {
	
	private Long id;
	
	private String name;
	
	private Long userId;
	
	private Date createDate;
	
	private Date modifyDate;
	
	private String albumRemark;
	
	private String type;
	
	private String status;
	
	private Set<AlbumFolder> folders = new HashSet<AlbumFolder>();

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getAlbumRemark() {
		return albumRemark;
	}

	public void setAlbumRemark(String albumRemark) {
		this.albumRemark = albumRemark;
	}

	public Set<AlbumFolder> getFolders() {
		return folders;
	}

	public void setFolders(Set<AlbumFolder> folders) {
		this.folders = folders;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
