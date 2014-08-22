package com.qpp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * album folder model
 * @author Kevin Liu
 *
 */
public class AlbumFolder {
	
	/**album folder id**/
	private Long id;
	
	/**album folder name**/
	private String name;
	
	/**album folder folderParentId**/
	private Long folderParentId;
	
	/**album folder albumId**/
	private Long albumId;
	
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
	
	/**album folder children**/
	private Set<AlbumFolder> children = new HashSet<AlbumFolder>();
	
	/**album folder pictures**/
	private Set<PictureInfo> pictures = new HashSet<PictureInfo>();
	
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
	public Long getAlbumId() {
		return albumId;
	}

	/**
	 * set the albumId to albumId
	 * @param albumId
	 */
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
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

	/**
	 * @return parent
	 */
	public AlbumFolder getParent() {
		return parent;
	}

	/**
	 * set the parent to parent
	 * @param parent
	 */
	public void setParent(AlbumFolder parent) {
		this.parent = parent;
	}

	/**
	 * @return children
	 */
	public Set<AlbumFolder> getChildren() {
		return children;
	}

	/**
	 * set the children to children
	 * @param children
	 */
	public void setChildren(Set<AlbumFolder> children) {
		this.children = children;
	}

	/**
	 * @return pictures
	 */
	public Set<PictureInfo> getPictures() {
		return pictures;
	}

	/**
	 * set the pictures to pictures
	 * @param pictures
	 */
	public void setPictures(Set<PictureInfo> pictures) {
		this.pictures = pictures;
	}

}
