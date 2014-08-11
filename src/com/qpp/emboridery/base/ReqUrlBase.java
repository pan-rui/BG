package com.qpp.emboridery.base;

import org.apache.commons.lang.StringUtils;

import com.qpp.ps.PsReqBase;
import com.qpp.util.HttpClientTool;

public class ReqUrlBase extends EmBase{
	private String format;
	private String background;
	private int angle;
	private int scale;
	private int offsetX;
	private int offsetY;
	private int boxleft;
	private int boxtop;
	private int boxright;
	private int boxbottom;

	private int dpi;
	
	public int getDpi() {
		return dpi;
	}
	public void setDpi(int dpi) {
		this.dpi = dpi;
	}

	private int padding;
	
	private int imageHeight;
	private int imageWidth;
	
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public int getBoxright() {
		return boxright;
	}
	public void setBoxright(int boxright) {
		this.boxright = boxright;
	}
	public int getBoxbottom() {
		return boxbottom;
	}
	public void setBoxbottom(int boxbottom) {
		this.boxbottom = boxbottom;
	}
	public int getBoxleft() {
		return boxleft;
	}

	public void setBoxleft(int boxleft) {
		this.boxleft = boxleft;
	}

	public int getBoxtop() {
		return boxtop;
	}

	public void setBoxtop(int boxtop) {
		this.boxtop = boxtop;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}
	private boolean rResetOrigin;
	
	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public boolean isrResetOrigin() {
		return rResetOrigin;
	}

	public void setrResetOrigin(boolean rResetOrigin) {
		this.rResetOrigin = rResetOrigin;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
	public ReqUrlBase(){
		
	}
	public ReqUrlBase(PsReqBase req){
		this.setFormat(req.Format);
		this.setBackground(req.Background);
		this.setAngle(req.Angle);
		this.setScale(req.Scale);
		this.setPadding(req.Padding);
		if (req.DPI!=0)
			this.setDpi(req.DPI);
		if (req.Height>0)
			this.setImageHeight(req.Height+req.Y);
		if (req.Width>0)
			this.setImageWidth(req.Width+req.X);
		if (req.X!=0){
			this.setBoxleft(req.X);
		}	
		if (req.Y!=0){
			this.setBoxtop(req.Y);
		}
		this.setOffsetX(req.OffsetX);
		this.setOffsetY(req.OffsetY);
	}	
	public StringBuilder getRequestUrlFromBuilder(StringBuilder url){
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Format",this.getFormat());
		if (this.getAngle()!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Angle",this.getAngle());
		if (this.getScale()>0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Scale ",this.getScale());
		if (this.getOffsetX()!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"OffsetX",this.getOffsetX());
		if (this.getOffsetY()!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"OffsetY",this.getOffsetY());
//		if (this.getOffsetX()!=0 && this.getOffsetY()!=0)
//			url=HttpClientTool.addStringBuilderParaToUrl(url,"ResetOrigin","true");
		if (this.getPadding()!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Padding ",this.getPadding());
		if (imageHeight>0)
			HttpClientTool.addStringBuilderParaToUrl(url,"ImageHeight",imageHeight);
		if (imageWidth>0)
			HttpClientTool.addStringBuilderParaToUrl(url,"ImageWidth",imageWidth);		
		if (boxleft!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Box.Left",boxleft);
		if (boxtop!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Box.Top",boxtop);
		if (boxright!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Box.Right",boxright);
		if (boxbottom!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Box.Bottom ",boxbottom);
		if (dpi!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"DPI",dpi);
		if ((StringUtils.isEmpty(this.getFormat()) || "png".equalsIgnoreCase(this.getFormat())) && StringUtils.isEmpty(this.getBackground())){
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Background","00000000");
		}else
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Background",this.getBackground());
		return url;
	}
	public String getRequestUrl(String urlPath){
		return urlPath;
	}		
}
