package com.qpp.model;

/**
 * Picture entity class
 * @author Kevin Liu
 *
 */
public class Picture {
	
	/**image no**/
	private String fileCode;
	
	/**image x coordinate**/
	private int x;
	
	/**image y coordinate**/
	private int y;
	
	/**image width**/
	private int width;
	
	/**image height**/
	private int height;
	
	/**image angle**/
	private int angle;
	
	/**image url**/
	private String picUrl;

	/**
	 * @return fileCode
	 */
	public String getFileCode() {
		return fileCode;
	}

	/**
	 * set the fileCode to fileCode
	 * @param fileCode
	 */
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * set the x to x
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * set the y to y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * set the width to width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * set the height to height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * set the angle to angle
	 * @param angle
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}

	/**
	 * @return picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * set the picUrl to picUrl
	 * @param picUrl
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
