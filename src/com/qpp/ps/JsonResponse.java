package com.qpp.ps;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qpp.util.JsonTool;

@JsonInclude(Include.NON_NULL)
public class JsonResponse {
	public boolean Success;
	public String ErrMsg;
	public Object data;
	
	public String toString(){
		return JsonTool.getJsonString(this);
	}
	public static void main(String[] args) {
    	JsonResponse json=new JsonResponse();
    	try{
        	String data=FileUtils.readFileToString(new File("d:\\temp\\req.json"));
        	
    		ReqText req=new ReqText();
   			req=JsonTool.getJsonObject(data,ReqText.class);
   			
        	json.data=req;
        	System.out.println(JsonTool.getJsonString(json));        	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
	}
}
