package com.qpp.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qpp.util.FastJsonUtils;

/**
 * fastjson test
 * @author SZ_it123
 *
 */
public class FastJsonTest {
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * java object to json
	 */
	public static void objectToJson () {
		Product product = new Product();
		product.setProductId(RandomUtils.nextInt());
		product.setProductName("picture");
		product.setCreateDate(new Date());
		String json = FastJsonUtils.object2json(product);
		System.out.println("json:" + json);
	}
	
	/**
	 * json to object
	 */
	public static void jsonToObject() {
		String json = "{'createDate':1404894548899,'productId':1775542454,'productName':'picture'}";
		Product product = FastJsonUtils.json2Object(json, Product.class);
		System.out.println(String.format("id:%s,name:%s,date:%s",product.getProductId(),product.getProductName(), format.format(product.getCreateDate())));
	}
	
	/**
	 * list to json
	 */
	public static void listToJson() {
		Product product = new Product();
		product.setProductId(RandomUtils.nextInt());
		product.setProductName("picture");
		product.setCreateDate(new Date());
		
		Product product2 = new Product();
		product2.setProductId(RandomUtils.nextInt());
		product2.setProductName("phone");
		product2.setCreateDate(new Date());
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		productList.add(product2);
		String json = FastJsonUtils.object2json(productList);
		System.out.println(json);
		
		System.out.println("---------------------------------------------------");
		List<String> list = new ArrayList<String>();
		list.add("windows");
		list.add("linux");
		
		String json2 = FastJsonUtils.object2json(list);
		System.out.println(json2);
	}
	
	/**
	 * json to list
	 */
	public static void jsonToList() {
		String json = "[{'createDate':1404895921553,'productId':1061054616,'productName':'picture'},{'createDate':1404895921553,'productId':242429475,'productName':'phone'}]";
		List<Product> proList = FastJsonUtils.json2List(json, Product.class);
		for (Product pro: proList) {
			System.out.println("id:" + pro.getProductId());
			System.out.println("name:" + pro.getProductName());
			System.out.println("date:" + format.format(pro.getCreateDate()));
		}
		System.out.println("-----------------------------------------");
		
		String json2 = "['windows','linux']";
		List<String> list = FastJsonUtils.json2List(json2, String.class);
		for (String str : list) {
			System.out.println(str);
		}
	}
	
	/**
	 * any object to json
	 */
	public static void anyToJson() {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("a", "abcdefg");
		map1.put("b", "lksdflddfsf");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		Product product = new Product();
		product.setProductId(RandomUtils.nextInt());
		product.setProductName("picture");
		product.setCreateDate(new Date());
		
		Product product2 = new Product();
		product2.setProductId(RandomUtils.nextInt());
		product2.setProductName("phone");
		product2.setCreateDate(new Date());
		
		map2.put("key1", product);
		map2.put("key2", product2);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list.add(map1);
		list.add(map2);
		String json = FastJsonUtils.object2json(list);
		System.out.println(json);
	}
	
	/**
	 * json to any object
	 */
	public static void jsonToAny() {
		String json = "[{'a':'abcdefg','b':'lksdflddfsf'},{'key1':{'createDate':1404897700543,'productId':464478810,'productName':'picture'},'key2':{'createDate':1404897700543,'productId':1021465453,'productName':'phone'}}]";
		List<HashMap<String, Object>> list = FastJsonUtils.json2Any(json, new TypeReference<List<HashMap<String, Object>>>(){});
		for (HashMap<String, Object> map : list) {
			for (Map.Entry<String, Object> entry: map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof JSONObject) {
					JSONObject obj = (JSONObject) value;
					String json2 = FastJsonUtils.object2json(obj);
					Product pro = FastJsonUtils.json2Object(json2, Product.class);
					System.out.println(String.format("Key:%s,Value:[id:%s,name:%s,date:%s]", key,pro.getProductId(), pro.getProductName(), format.format(pro.getCreateDate())));
				} else {
					System.out.println(String.format("Key:%s,Value:%s", key,value));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		objectToJson();
		System.out.println("=====jsonToObject start=========================================");
		jsonToObject();
		System.out.println("=====listToJson start=========================================");
		
		listToJson();
		System.out.println("=====jsonToList start=========================================");
		jsonToList();
		System.out.println("=====anyToJson start=========================================");
		
		anyToJson();
		System.out.println("=====jsonToAny start=========================================");
		jsonToAny();
	}
}

/**
 * product entity
 * @author Kevin Liu
 *
 */
class Product {
	
	/**product Id**/
	private int productId;
	
	/**product name**/
	private String productName;
	
	/**product create date**/
	private Date createDate;

	/**
	 * @return productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * set the productId to productId
	 * @param productId
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * set the productName to productName
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * set the createDate to createDate
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}