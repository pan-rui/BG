package com.qpp.emboridery.base;

public class EmBase {
	public static final String renderPath="/1/Render/";
	public static final String generatePath="/1/Generate/";
	public static final String infoPath="/1/GetInfo/";
	public static final String listFonts="/1/List/Fonts";
	public static final String listRecipes="/1/List/Recipes";
	public static final String listMachineFormats="/1/List/MachineFormats/";
	public static final String listPath="/1/List/Designs/";
	public static final String downloadPath="/1/Download/Designs/";
	public static final String uploadPath="/1/Upload/Designs/";
	public static final String deletePath="/1/Delete/Designs/";
	public static final String existsPath="/1/Exists/Designs/";
	public static final String hashPath="/1/Hash/Designs/";
	private String urlPath;

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getRequestUrl(String urlPath){
		return urlPath;
	}		
}
