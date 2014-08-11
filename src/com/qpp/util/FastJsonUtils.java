package com.qpp.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * The tool that processing Json and others Objects
 * 
 * @author Kevin Liu
 *
 */
public class FastJsonUtils {

	/**
	 * Converts java object to a json's string 
	 * @param object
	 * @return
	 */
	public static <T> String object2json(T object) {
		return JSON.toJSONString(object);
	}
	
	/**
	 * Converts json to java object
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T json2Object(String json, Class<T> clz) {
		return JSON.parseObject(json, clz);
	}
	
	/**
	 * Converts json to list
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> List<T> json2List(String json, Class<T> clz) {
         return JSON.parseArray(json, clz);
	}
	
	/**
	 * Converts json to any object
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T json2Any(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
	}
}
