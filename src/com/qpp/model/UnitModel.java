package com.qpp.model;

import java.util.List;

/**
 * Unit Model entity class
 * @author kevin liu
 *
 */
public class UnitModel {
	
	/**unit code**/
	private String unitCode;
	
	/**unit sum**/
	private int unitNumber;
	
	/**unit price**/
	private int unitPrice;
	
	/**unit background picture**/
	private String backPic;
	
	/**unit text list**/
	private List<Text> textList;
	
	/**unit picture list**/
	private List<Picture> picList;
	
	/**
	 * @return unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * set the unitCode to unitCode
	 * @param unitCode
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return unitNumber
	 */
	public int getUnitNumber() {
		return unitNumber;
	}

	/**
	 * set the unitNumber to unitNumber
	 * @param unitNumber
	 */
	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * @return unitPrice
	 */
	public int getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * set the unitPrice to unitPrice
	 * @param unitPrice
	 */
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return backPic
	 */
	public String getBackPic() {
		return backPic;
	}

	/**
	 * set backPic to backPic
	 * @param backPic
	 */
	public void setBackPic(String backPic) {
		this.backPic = backPic;
	}

	/**
	 * @return textList
	 */
	public List<Text> getTextList() {
		return textList;
	}

	/**
	 * set the textList to textList
	 * @param textList
	 */
	public void setTextList(List<Text> textList) {
		this.textList = textList;
	}

	/**
	 * @return picList
	 */
	public List<Picture> getPicList() {
		return picList;
	}

	/**
	 * set the picList to picList
	 * @param picList
	 */
	public void setPicList(List<Picture> picList) {
		this.picList = picList;
	}
	
	
	
}
