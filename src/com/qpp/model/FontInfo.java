package com.qpp.model;

/**
 * font model
 * @author Kevin Liu 2014/8/21
 *
 */
public class FontInfo {

	/**font code**/
	private String fontCode;
	
	/**font name**/
	private String fontName;
	
	/**Whether the font can be centered?**/
	private boolean hasAlignCenter;
	
	/**Whether the font can be right-aligned?**/
	private boolean hasAlignRight;
	
	/**Is there a normal font?**/
	private boolean hasNormal;
	
	/**Is there a bold font?**/
	private boolean hasBold;
	
	/**Is there a bold font?**/
	private boolean hasItalic;
	
	/**the actual font italic**/
	private String realItalic;
	
	/**the font italic rotate**/
	private String italicRotate;
	
	/**the italic space**/
	private String italicSpace;
	
	/**allow char**/
	private String allowChar;
	
	/**the font status**/
	private String status;

	/**
	 * @return fontCode
	 */
	public String getFontCode() {
		return fontCode;
	}

	/**
	 * set the fontCode to fontCode
	 * @param fontCode
	 */
	public void setFontCode(String fontCode) {
		this.fontCode = fontCode;
	}

	/**
	 * @return fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * set the fontName to fontName
	 * @param fontName
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @return hasAlignCenter
	 */
	public boolean isHasAlignCenter() {
		return hasAlignCenter;
	}

	/**
	 * set the hasAlignCenter to hasAlignCenter
	 * @param hasAlignCenter
	 */
	public void setHasAlignCenter(boolean hasAlignCenter) {
		this.hasAlignCenter = hasAlignCenter;
	}

	/**
	 * @return hasAlignRight
	 */
	public boolean isHasAlignRight() {
		return hasAlignRight;
	}

	/**
	 * set the hasAlignRight to hasAlignRight
	 * @param hasAlignRight
	 */
	public void setHasAlignRight(boolean hasAlignRight) {
		this.hasAlignRight = hasAlignRight;
	}

	/**
	 * @return hasNormal
	 */
	public boolean isHasNormal() {
		return hasNormal;
	}

	/**
	 * set the hasNormal to hasNormal
	 * @param hasNormal
	 */
	public void setHasNormal(boolean hasNormal) {
		this.hasNormal = hasNormal;
	}

	/**
	 * @return hasBold
	 */
	public boolean isHasBold() {
		return hasBold;
	}

	/**
	 * set the hasBold to hasBold
	 * @param hasBold
	 */
	public void setHasBold(boolean hasBold) {
		this.hasBold = hasBold;
	}

	/**
	 * @return hasItalic
	 */
	public boolean isHasItalic() {
		return hasItalic;
	}

	/**
	 * set the hasItalic to hasItalic
	 * @param hasItalic
	 */
	public void setHasItalic(boolean hasItalic) {
		this.hasItalic = hasItalic;
	}

	/**
	 * @return realItalic
	 */
	public String getRealItalic() {
		return realItalic;
	}

	/**
	 * set the realItalic to realItalic
	 * @param realItalic
	 */
	public void setRealItalic(String realItalic) {
		this.realItalic = realItalic;
	}

	/**
	 * @return italicRotate
	 */
	public String getItalicRotate() {
		return italicRotate;
	}

	/**
	 * set the italicRotate to italicRotate
	 * @param italicRotate
	 */
	public void setItalicRotate(String italicRotate) {
		this.italicRotate = italicRotate;
	}

	/**
	 * @return italicSpace
	 */
	public String getItalicSpace() {
		return italicSpace;
	}

	/**
	 * set the italicSpace to italicSpace
	 * @param italicSpace
	 */
	public void setItalicSpace(String italicSpace) {
		this.italicSpace = italicSpace;
	}

	/**
	 * @return allowChar
	 */
	public String getAllowChar() {
		return allowChar;
	}

	/**
	 * set the allowChar to allowChar
	 * @param allowChar
	 */
	public void setAllowChar(String allowChar) {
		this.allowChar = allowChar;
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
