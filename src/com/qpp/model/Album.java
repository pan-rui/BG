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
	
	/**album id**/
	private Long id;
	/**album name**/
	private String name;
	/**album creator**/
	private Long userId;
	/**album create date**/
	private Date createDate;
	/**album modify date latest**/
	private Date modifyDate;
	/**album remark**/
	private String albumRemark;
	/**album type**/
	private String type;
	/**album status**/
	private String status;
	/**album folders**/
	private Set<AlbumFolder> folders = new HashSet<AlbumFolder>();

	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * set the id to id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set the name to name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * set the userId to userId
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * set the createDate to createDate
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * set the modifyDate to modifyDate
	 * @param modifyDate
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return albumRemark
	 */
	public String getAlbumRemark() {
		return albumRemark;
	}

	/**
	 * set the albumRemark to albumRemark
	 * @param albumRemark
	 */
	public void setAlbumRemark(String albumRemark) {
		this.albumRemark = albumRemark;
	}

	/**
	 * @return folders
	 */
	public Set<AlbumFolder> getFolders() {
		return folders;
	}

	/**
	 * set the folders to folders
	 * @param folders
	 */
	public void setFolders(Set<AlbumFolder> folders) {
		this.folders = folders;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * set the type to type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * set the status to status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
