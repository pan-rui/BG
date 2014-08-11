package com.qpp.util;

import java.util.Random;

public class RandomSymbol {
	private static final String[] allSymbolArray = new String[] { "1", "2", "3", "4",
		"5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g",
		"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
		"u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
		"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z" };

private static final String[] partSymbolArray = new String[] { "1", "3", "4", "5",
		"6", "7", "8", "a", "b", "c", "d", "e", "f", "h", "i", "j", "k",
		"m", "n", "p", "r", "s", "t", "u", "v", "w", "x", "y" };

private static String getSymbol(int lenth, String[] symbolArray) {
	String tempStr = "";
	Random rand = new Random();
	for (int i = 0; i <= lenth; i++) {
		int j = rand.nextInt(symbolArray.length);
		tempStr += symbolArray[j];
	}
	return tempStr;
}

public static String getPartSymbol(int length) {
	return getSymbol(length, partSymbolArray);
}

public static String getAllSymbol(int length) {
	return getSymbol(length, allSymbolArray);
}
}
