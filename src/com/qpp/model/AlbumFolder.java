package com.qpp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * album folder model
 * @author Kevin Liu
 *
 */
public class AlbumFolder implements Serializable {
	
	/**album folder id**/
	private Long id;
	
	/**album folder name**/
	private String name;
	
	/**album folder folderParentId**/
	private Long folderParentId;

	/**album folder creator**/
	private Long userId;
	
	/**album folder remark**/
	private String folderRemark;
	
	/**album folder create date**/
	private Date createDate;
	
	/**album folder modify date latest**/
	private Date modifyDate;
	
	/**album folder parent**/
	private AlbumFolder parent;

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
	 * @return folderParentId
	 */
	public Long getFolderParentId() {
		return folderParentId;
	}

	/**
	 * set the folderParentId to folderParentId
	 * @param folderParentId
	 */
	public void setFolderParentId(Long folderParentId) {
		this.folderParentId = folderParentId;
	}

	/**
	 * @return albumId
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
 	 * @return folderRemark
	 */
	public String getFolderRemark() {
		return folderRemark;
	}

	/**
	 * set the folderRemark to folderRemark
	 * @param folderRemark
	 */
	public void setFolderRemark(String folderRemark) {
		this.folderRemark = folderRemark;
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

	public void setParent(AlbumFolder parent) {
		this.parent = parent;
	}

	/**
	 * @return children
	 */
}
