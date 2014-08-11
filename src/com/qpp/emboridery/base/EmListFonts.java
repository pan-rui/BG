package com.qpp.emboridery.base;

import com.qpp.util.HttpClientTool;

public class EmListFonts extends EmBase {
	private String format;
	private String type;
	private boolean isSorted;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isSorted() {
		return isSorted;
	}
	public void setSorted(boolean isSorted) {
		this.isSorted = isSorted;
	}	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getRequestUrl(String urlPath){
		StringBuilder url=new StringBuilder();
		if ("XML".equalsIgnoreCase(format))
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Format",format);
		else
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Format","json");
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Type",this.getType());
		//url=getRequestUrlFromBuilder(url);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(urlPath);
		urlAll.append(url.toString());		
		return urlAll.toString();
	}		
	
}
