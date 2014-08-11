package com.qpp.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Created with IntelliJ IDEA.
 * User: gary,
 * Date: 12-7-19
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"file:D:/work/java/BGM/WebContent/WEB-INF/spring/applicationContext*.xml"})  
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)  
@Transactional  
public class MyTest {
    protected final Logger loger = Logger.getLogger(this.getClass().getName());
    public void freeTest(){
        //request.getSession().getServletContext().getRealPath("/"),Struts=this.getServletContect().getRealPath("/");
        String path=System.getProperty("user.dir");
        Configuration cfg=new   Configuration();
        try{
            cfg.setDirectoryForTemplateLoading(new File(path+"\\WebContent\\template"));
            Map root=new HashMap();
            root.put("user","Gary");
            Template plate=cfg.getTemplate("register.html");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //Writer out1 = new OutputStreamWriter(baos);
            plate.process(root, new OutputStreamWriter(out));
            //out1.flush();
            String aa=out.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void jsonTest(){
    	try{
    		
    		String test=FileUtils.readFileToString(new File("d:\\temp\\ret.json"));
    		ObjectMapper mapper =new ObjectMapper();
    		JsonNode json=mapper.readTree(test);
    		ArrayNode nameNode =(ArrayNode) json.path("Data");
    		//JSONObject object =JSON.parseObject(test);
    		System.out.println(nameNode.get(1));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    @Test  
    public void test(){  
        System.out.println ("test");   
    }  
   
    public static void main(String[] args) {    	
    	//System.out.println(URLEncoder.encode("http://localhost:8080/login.hyml"));
    	//System.out.println(URLDecoder.decode("%7B%22object%22%3A%22https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F%22%7D"));
		//System.out.println(MessageFormat.format("How are you,{0}","gary"));
        System.getProperties().put("proxySet", "true");        
        System.getProperties().put("proxyHost", "127.0.0.1");
        System.getProperties().put("proxyPort", "8580");        
    	
    	
    }
}
