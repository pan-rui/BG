package com.qpp.ps;

import java.util.ArrayList;

import com.qpp.util.JsonTool;


public class ReqText extends PsReqBase{
	public String OrderNo;
	public String Text;	
	public String Color;
	public String FontFamily;
	public ArrayList<String> Palette;
	public short FontSize;
	public String Decoration;
	public String TextAlign;
	//public int BoxLeft=0;
	//public int BoxTop=0;
	public String Type;//http://trial3.stitchport.com/stitchportwebapi/Documentation/Lettering
	public String Envelope;
	public short Needle;
	public int WidthCompression; 
	public int X1;
	public int Y1;
	public int X2;
	public int Y2;
	
	public static void main(String[] args){
		ReqText req=new ReqText();
		ArrayList<String> Palette=new ArrayList<String>();
		Palette.add("A");
		Palette.add("B");
		req.Palette=Palette;
		req.Text="Hello World";
		req.Color="#000000";
		try{
			String retJson=JsonTool.getJsonString(req);
			System.out.println(retJson);
			
//			req=obj.readValue(FileUtils.readFileToString(new File("d:\\temp\\req.json"),"gbk"),ReqText.class);			
//			String retJson=obj.writeValueAsString(req);
//			System.out.println(retJson);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
