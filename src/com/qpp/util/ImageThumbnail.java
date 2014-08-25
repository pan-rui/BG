package com.qpp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Image Thumbnail
 * @author Kevin Liu 2014/8/18
 *
 */
public enum ImageThumbnail {
	
	FIFTY("_50-50", 50, 50), EIGHTY("_80-80", 80, 80), HUNDRED("_100-100", 100, 100),
	TWO_HUNDRED("_200-200", 200,200), FOUR_HUNDRED("_400-400", 400, 400), SIX_HUNDRED("_600-600", 600, 600),
	EIGHT_HUNDRED("_800-800", 800, 800), ONE_THOUSAND_TWO_HANDRED("_1200-1200", 1200, 1200);
	
	String name;
	int width;
	int height;
	
	private ImageThumbnail(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public static List<ImageThumbnail> getList () {
		List<ImageThumbnail> list = new ArrayList<ImageThumbnail>();
		list.add(ImageThumbnail.FIFTY);
		list.add(ImageThumbnail.EIGHTY);
		list.add(ImageThumbnail.HUNDRED);
		list.add(ImageThumbnail.TWO_HUNDRED);
		list.add(ImageThumbnail.FOUR_HUNDRED);
		list.add(ImageThumbnail.SIX_HUNDRED);
		list.add(ImageThumbnail.EIGHT_HUNDRED);
		list.add(ImageThumbnail.ONE_THOUSAND_TWO_HANDRED);
		return list;
	}
	
}
