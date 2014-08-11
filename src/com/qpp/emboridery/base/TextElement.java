package com.qpp.emboridery.base;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.qpp.ps.ReqText;
import com.qpp.util.HttpClientTool;

public class TextElement extends ReqUrlBase{
	public TextElement(){
		super();
		this.setUrlPath("Lettering");
	}

	private int widthCompression;
	
	public void setWidthCompression(int widthCompression) {
		this.widthCompression= widthCompression;
	}

	public int getWidthCompression() {
		return widthCompression;
	}

	private String type;	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public enum JustifyTypes
	{
		jtLeft,jtCenter,jtRight,jtFitToCurve,jtFillBox
	}
	public enum EnvelopeTypes
	{
		etRectangle,
		etBridgeConcaveTop,
		etBridgeConcaveBottom,
		etDoubleConcaveBridges,
		etBridgeConvexTop,
		etBridgeConvexBottom,
		etDoubleConvexBridges,
		etConcaveTopConvexBottom,
		etConvexTopConcaveBottom,
		etPennantRight,
		etPennantLeft
	}
	public enum StartStopLocations
	{
		sslFirstStitch, 
		sslLastStitch, 
		sslUserDefined,
		sslTopLeft, 
		sslTopCentre, 
		sslTopRight,
		sslMiddleLeft, 
		sslCentre, 
		sslMiddleRight,
		sslBottomLeft, 
		sslBottomCentre, 
		sslBottomRight
	}
	public enum StartStopAlignmentTypes
	{
		ssatNone, 
		ssatAlignStartWithEnd, 
		ssatAlignEndWithStart 	
	}

	private String decoration;
	
	
	public String getDecoration() {
		return decoration;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	private String privateText;
	public final String getText()
	{
		return privateText;
	}
	public final void setText(String value)
	{
		privateText = value;
	}
	private String privateFont;
	public final String getFont()
	{
		return privateFont;
	}
	public final void setFont(String value)
	{
		privateFont = value;
	}
	private Short privateHeight;
	public final Short getHeight()
	{
		return privateHeight;
	}
	public final void setHeight(Short value)
	{
		privateHeight = value;
	}
	private Short privateNeedle;
	public final Short getNeedle()
	{
		return privateNeedle;
	}
	public final void setNeedle(Short value)
	{
		privateNeedle = value;
	}
	private String privateRecipe;
	public final String getRecipe()
	{
		return privateRecipe;
	}
	public final void setRecipe(String value)
	{
		privateRecipe = value;
	}
	private String privateMachineFormat;
	public final String getMachineFormat()
	{
		return privateMachineFormat;
	}
	public final void setMachineFormat(String value)
	{
		privateMachineFormat = value;
	}
	private JustifyTypes privateJustification;
	public final JustifyTypes getJustification()
	{
		return privateJustification;
	}
	public final void setJustification(JustifyTypes value)
	{
		privateJustification = value;
	}
	private String envelope;
	public final String getEnvelope()
	{
		return envelope;
	}
	public final void setEnvelope(String value)
	{
		envelope = value;
	}
	
	private java.util.ArrayList<String> privatePalette;
	public final java.util.ArrayList<String> getPalette()
	{
		return privatePalette;
	}
	public final void setPalette(java.util.ArrayList<String> value)
	{
		privatePalette = value;
	}

	private Integer privateX1;
	public final Integer getX1()
	{
		return privateX1;
	}
	public final void setX1(Integer value)
	{
		privateX1 = value;
	}
	private Integer privateY1;
	public final Integer getY1()
	{
		return privateY1;
	}
	public final void setY1(Integer value)
	{
		privateY1 = value;
	}
	private Integer privateX2;
	public final Integer getX2()
	{
		return privateX2;
	}
	public final void setX2(Integer value)
	{
		privateX2 = value;
	}
	private Integer privateY2;
	public final Integer getY2()
	{
		return privateY2;
	}
	public final void setY2(Integer value)
	{
		privateY2 = value;
	}

	private StartStopLocations privateStartLocation;
	public final StartStopLocations getStartLocation()
	{
		return privateStartLocation;
	}
	public final void setStartLocation(StartStopLocations value)
	{
		privateStartLocation = value;
	}
	private StartStopLocations privateStopLocation;
	public final StartStopLocations getStopLocation()
	{
		return privateStopLocation;
	}
	public final void setStopLocation(StartStopLocations value)
	{
		privateStopLocation = value;
	}
	private Double privateUserDefinedStartPointX;
	public final Double getUserDefinedStartPointX()
	{
		return privateUserDefinedStartPointX;
	}
	public final void setUserDefinedStartPointX(Double value)
	{
		privateUserDefinedStartPointX = value;
	}
	private Double privateUserDefinedStartPointY;
	public final Double getUserDefinedStartPointY()
	{
		return privateUserDefinedStartPointY;
	}
	public final void setUserDefinedStartPointY(Double value)
	{
		privateUserDefinedStartPointY = value;
	}
	private Double privateUserDefinedStopPointX;
	public final Double getUserDefinedStopPointX()
	{
		return privateUserDefinedStopPointX;
	}
	public final void setUserDefinedStopPointX(Double value)
	{
		privateUserDefinedStopPointX = value;
	}
	private Double privateUserDefinedStopPointY;
	public final Double getUserDefinedStopPointY()
	{
		return privateUserDefinedStopPointY;
	}
	public final void setUserDefinedStopPointY(Double value)
	{
		privateUserDefinedStopPointY = value;
	}
	private Double privateStartOffsetX;
	public final Double getStartOffsetX()
	{
		return privateStartOffsetX;
	}
	public final void setStartOffsetX(Double value)
	{
		privateStartOffsetX = value;
	}
	private Double privateStartOffsetY;
	public final Double getStartOffsetY()
	{
		return privateStartOffsetY;
	}
	public final void setStartOffsetY(Double value)
	{
		privateStartOffsetY = value;
	}
	private Double privateStopOffsetX;
	public final Double getStopOffsetX()
	{
		return privateStopOffsetX;
	}
	public final void setStopOffsetX(Double value)
	{
		privateStopOffsetX = value;
	}
	private Double privateStopOffsetY;
	public final Double getStopOffsetY()
	{
		return privateStopOffsetY;
	}
	public final void setStopOffsetY(Double value)
	{
		privateStopOffsetY = value;
	}
	private StartStopAlignmentTypes privateStartStopAlignment;
	public final StartStopAlignmentTypes getStartStopAlignment()
	{
		return privateStartStopAlignment;
	}
	public final void setStartStopAlignment(StartStopAlignmentTypes value)
	{
		privateStartStopAlignment = value;
	}

	public final String getUrlPath()
	{
		return "Lettering";
	}
	public TextElement(ReqText req){
		super(req);
		this.setDecoration(req.Decoration);
		this.setNeedle(req.Needle);
		this.setText(req.Text);
		this.setEnvelope(req.Envelope);
		this.setWidthCompression(req.WidthCompression);
		if (!StringUtils.isEmpty(req.Color)){
			ArrayList<String> colorList=new ArrayList<String>();
			colorList.add(req.Color);
			this.setPalette(colorList);
		}
		if (req.Palette!=null && req.Palette.size()>0){
			this.setPalette(req.Palette);
		}
		this.setFont(req.FontFamily);
		this.setHeight(req.FontSize);
		this.setType(req.Type);
		if (!StringUtils.isEmpty(req.TextAlign)){
			if (req.TextAlign.equalsIgnoreCase("left"))
				this.setJustification(JustifyTypes.jtLeft);
			if (req.TextAlign.equalsIgnoreCase("right"))
				this.setJustification(JustifyTypes.jtRight);
			if (req.TextAlign.equalsIgnoreCase("center"))
				this.setJustification(JustifyTypes.jtCenter);
		}
		if (req.X1!=0)
			this.setX1(req.X1);
		if (req.Y1!=0)
			this.setY1(req.Y1);
		if (req.X2!=0)
			this.setX2(req.X2);
		if (req.Y2!=0)
			this.setY2(req.Y2);
	}
	public String getRequestUrl(String urlPath){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Text",this.getText());
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Decoration",this.getDecoration());
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Type",this.getType());
		url=HttpClientTool.addStringBuilderArrayParaToUrl(url,"Palette",this.getPalette());
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Envelope",this.getEnvelope());
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Font",this.getFont());
		if (this.getNeedle()!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Needle",this.getNeedle());
		if (this.getX1()!=null)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"X1",this.getX1());
		if (this.getY1()!=null)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Y1",this.getY1());
		if (this.getX2()!=null)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"X2",this.getX2());
		if (this.getY2()!=null)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Y2",this.getY2());
		if (this.getWidthCompression()!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"WidthCompression ",this.getWidthCompression());		
		if (this.getHeight()!=null && this.getHeight()>0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"Height",this.getHeight());
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Justification",this.getJustification());
		url=getRequestUrlFromBuilder(url);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(urlPath);
		urlAll.append(url.toString());		
		return urlAll.toString();
	}	
}
