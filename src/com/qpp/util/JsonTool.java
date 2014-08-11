package com.qpp.util;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTool {
	public static String getJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String ret = "";
		try {
			ret = mapper.writeValueAsString(obj);
		} catch (Exception e) {
		}
		return ret;
	}

	public static String getJsonPathNode(String allStr,String path) {
		ObjectMapper mapper=new ObjectMapper();
		try{
			JsonNode jsonNode=mapper.readTree(allStr);
			JsonNode data=jsonNode.path(path);
    		//JsonNode data=jsonNode.findPath(path);
    		return data.toString();
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}
	}
	public static String getJsonNodeDeep(String allStr,String path) {
		ObjectMapper mapper=new ObjectMapper();
		try{
			JsonNode jsonNode=mapper.readTree(allStr);
			String[] pathArr=path.split("\\.");
			for(String childPath:pathArr){
				jsonNode=jsonNode.path(childPath);
				if (jsonNode==null)
					return null;
			}			
    		return jsonNode.toString();
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T getJsonObject(String inputStr, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			T req = mapper.readValue(inputStr, clazz);
			return req;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Object jsonByObjectDirecdt(Object obj, String[] filterNames) {
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.setIgnoreDefaultExcludes(true);   
	    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
	    jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});  
	    jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
	    if (filterNames != null) {
	    	jsonConfig.setExcludes(filterNames);
	    }
	    
	    Object object = null;
	    
	    try {
	    	object = JSONObject.fromObject(obj, jsonConfig);
	    }catch(Exception e) {
	    	object = JSONArray.fromObject(obj, jsonConfig);
	    }
	    return object;
	}
	
	public static void main(String[] args) {
		System.out.println("value:"+Include.NON_NULL);
		String[] aa="url.aa".split("\\.");
		for(String l:aa){
			System.out.println(l);
		}
	}
}
