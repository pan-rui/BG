package com.qpp.ps;

import java.util.ArrayList;
import java.util.List;

import com.qpp.util.JsonTool;

public class ComplexReqText extends PsReqBase{
	public ArrayList<ReqText> Elements;
	public List<String> Palette;
	public List<Short> Needles;
	public String Layout;
	public String StackedAlignment;
	public int StackedSpacing;

	public static void main(String[] args){
		ArrayList<ReqText> list=new ArrayList<ReqText>();
		
		ReqText person=new ReqText();
		person.Text="gary";
		list.add(person);
		
		ReqText person1=new ReqText();
		person1.Text="gary";
		list.add(person1);
		
		ComplexReqText comp=new ComplexReqText();
		comp.Elements=list;
		System.out.println(JsonTool.getJsonString(comp));
		
	}	
}
