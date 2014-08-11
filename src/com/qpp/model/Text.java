package com.qpp.model;

/**
 * Text entity class
 * @author Kevin Liu
 *
 */
public class Text {
	
	/**text**/
	private String text;
	
	/**text color**/
	private String color;
	
	/**text font name**/
	private String fontCode;
	
	/**text alignment**/
	private String align;
	
	/**text bold**/
	private boolean isBold; 
	
	/**text italic**/
	private boolean isItalic; 
	
	/**text x coordinate**/
	private int x;
	
	/**text y coordinate**/
	private int y;
	
	/**text angle**/
	private int angle;
	
	/**text font size**/
	private int fontSize;
	
	/**
 	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * set the text to text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * set the color to color
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

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
	 * @return align
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * set the align to align
	 * @param align
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * @return isBold
	 */
	public boolean isBold() {
		return isBold;
	}

	/**
	 * set the isBold to isBold
	 * @param isBold
	 */
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	/**
	 * @return isItalic
	 */
	public boolean isItalic() {
		return isItalic;
	}

	/**
	 * set the isItalic to isItalic
	 * @param isItalic
	 */
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
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
	 * @return fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * set the fontSize to fontSize 
	 * @param fontSize
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
}
