package com.qpp.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedString;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class GenerateWordPicture {

	BufferedImage image;

	void createImage(String fileLocation) {
		try {
			FileOutputStream fos = new FileOutputStream(fileLocation);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void graphicsGeneration(TextInfo textInfo, String imageUrl) {
		File file = new File(imageUrl);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
		int width = Integer.parseInt(textInfo.getWidth());
		int height = Integer.parseInt(textInfo.getHeight());
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(String2Color(textInfo.getColor()));
		int style = 0;
		if (textInfo.getIsBold()) {
			style = 1;
			if (textInfo.getIsItalic()) {
				style = 1|2;
			} 
		} else {
			if (textInfo.getIsItalic()) {
				style = 2;
			} 
		}
		
		int fontSize = Integer.parseInt(textInfo.getFontSize());
		Font font = new Font(textInfo.getFontFamily(), style, fontSize);
		graphics.setFont(font);
		AttributedString as = new AttributedString(textInfo.getText());
		as.addAttribute(TextAttribute.FONT, font);
		if (textInfo.getTextDecoration() != null && !"".equals(textInfo.getTextDecoration())) {
			as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		}
		
		FontMetrics metrics = graphics.getFontMetrics(); 
		
		int textX = 0;
		int textY = metrics.getHeight();
		System.out.println(metrics.stringWidth(textInfo.getText()));
		
		if (textInfo.getTextAlign() != null) {
			if ("left".equalsIgnoreCase(textInfo.getTextAlign())) {
				textX = 0;
			} else if ("right".equalsIgnoreCase(textInfo.getTextAlign())) {
				textX = Integer.parseInt(textInfo.getWidth()) -  metrics.stringWidth(textInfo.getText());
			} else {
				textX = (Integer.parseInt(textInfo.getWidth()) -  metrics.stringWidth(textInfo.getText())) / 2;
			}
		}
		
		
		graphics.drawString(as.getIterator(), textX, textY);
		graphics.dispose();
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Color String2Color(String str) {  
        int i = Integer.parseInt(str.substring(1), 16);  
        return new Color(i);  
    }  

	public static void main(String[] args) {
		GenerateWordPicture gwp = new GenerateWordPicture();
		TextInfo textInfo = gwp.new TextInfo();
		textInfo.setText("hello world!");
		textInfo.setFontFamily("宋体");
		textInfo.setFontSize("20");
		textInfo.setColor("#00eeff");
		textInfo.setIsBold(false);
		textInfo.setIsItalic(true);
		textInfo.setTextAlign("right");
		textInfo.setTextDecoration("underline");
		textInfo.setWidth("200");
		textInfo.setHeight("100");
		
		try {
			gwp.graphicsGeneration(textInfo, "D:\\2.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class TextInfo {
		private String text;
		private String fontFamily;
		private String fontSize;
		private String color;
		private String textAlign;
		private boolean isItalic;
		private boolean isBold;
		private String textDecoration;
		private String width;
		private String height;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getFontFamily() {
			return fontFamily;
		}
		public void setFontFamily(String fontFamily) {
			this.fontFamily = fontFamily;
		}
		public String getFontSize() {
			return fontSize;
		}
		public void setFontSize(String fontSize) {
			this.fontSize = fontSize;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getTextAlign() {
			return textAlign;
		}
		public void setTextAlign(String textAlign) {
			this.textAlign = textAlign;
		}
		public boolean getIsItalic() {
			return isItalic;
		}
		public void setIsItalic(boolean isItalic) {
			this.isItalic = isItalic;
		}
		public boolean getIsBold() {
			return isBold;
		}
		public void setIsBold(boolean isBold) {
			this.isBold = isBold;
		}
		public String getTextDecoration() {
			return textDecoration;
		}
		public void setTextDecoration(String textDecoration) {
			this.textDecoration = textDecoration;
		}
		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		
	}
}
