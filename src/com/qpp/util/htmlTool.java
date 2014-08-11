package com.qpp.util;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class htmlTool {
	public static final int timeOut=20000;
	protected final Logger loger= Logger.getLogger(htmlTool.class.getName());
	public String ReadFile(String contentPath)  {
		String content = "";
		String str = null;
		BufferedReader br = null;
		try {
			FileInputStream fis = new FileInputStream(contentPath);
			InputStreamReader isr = new InputStreamReader(fis,"utf-8");
			br = new BufferedReader(isr);
			while ((str = br.readLine()) != null) {
				content = content + str + "\n";
			}
		}catch(Exception e){
			loger.fatal("File Not found -------------------  "+contentPath);
		}finally {
			try{
				br.close();	
			}catch(Exception ee){				
			}		
		}
		return content;
	}
	public String ReadInputURL(String contentPath,Map<String,String> map)  {
		String retStr=null;
		try{
			Document doc = Jsoup.connect(contentPath)
								.data(map) 
								.timeout(timeOut)
								.post();
			//Document doc = Jsoup.connect(contentPath).get();
			retStr=doc.html();
		}catch(Exception e){			
		}
		return retStr;
	}
	public String removeNode(String html,String node){
		//#note,.class,p,div[title],[attr=tt]
		Document doc=Jsoup.parse(html);
		doc.select(node).remove(); 
		return doc.html();
	}
	public String readNode(String html,String node){
		//#note,.class,p,div[title],[attr=tt]
		Document doc=Jsoup.parse(html);
		Elements elements=doc.select(node); 
		if (elements.size()>0)
			return elements.text();
		else
			return null;
	}
	public String repNode(String html,String node,String txt){
		//#note,.class,p,div[title],[attr=tt]
		Document doc=Jsoup.parse(html);
		doc.select(node).html(txt);
		return doc.html();
	}
	public String repAttribute(String html,String node,String attr,String txt){
		//#note,.class,p,div[title],[attr=tt]
		Document doc=Jsoup.parse(html);
		doc.select(node).attr(attr,txt);
		return doc.html();
	}
	public String readAttr(String html,String tag,String value){
		Document doc=Jsoup.parse(html);
		Elements elements=doc.select(tag);
		if (elements.size()>0 && elements.hasAttr(value))
			return elements.attr(value);
		else
			return null;
	}	
	public static void main(String[] args) {
		htmlTool tool=new htmlTool();
		String content=tool.ReadFile("d:\\temp\\Lettering.xml");		
		String aa=tool.readNode(content,"NumStitches");
		//String aa=tool.repAttribute(content,"#txt","href","test html");
		System.out.println("aa="+aa);
		
		
	}
}
