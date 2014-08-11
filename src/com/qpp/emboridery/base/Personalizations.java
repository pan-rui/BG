package com.qpp.emboridery.base;

import com.qpp.util.JsonTool;

public class Personalizations {
	public String Name;
	public String Text;
	public String TextColour;	
	public String Design;

	public String toString(){
		return JsonTool.getJsonString(this);
	}
	
	public static void main(String[] args){
			
	}	
	
}
