package com.qpp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("deprecation")
public class HttpClientTool {
	public static int Connection_Time_Out=60000;

	public static StringBuilder addStringBuilderParaToUrl(StringBuilder paraUrl,String key,Object value){
		if (StringUtils.isEmpty(key) || value==null)
			return paraUrl;
		if (paraUrl==null || paraUrl.length()==0){
			paraUrl=new StringBuilder("?");
		}else
			paraUrl.append("&");
		paraUrl.append(MessageFormat.format("{0}={1}",URLEncoder.encode(key),URLEncoder.encode(value.toString())));
		//paraUrl.append(MessageFormat.format("{0}={1}",key,URLEncoder.encode(value.toString())));
		return paraUrl;
	}
	public static StringBuilder addStringBuilderArrayParaToUrl(StringBuilder paraUrl,String key,List list){
		if (StringUtils.isEmpty(key) || list==null || list.size()==0)
			return paraUrl;
		int index=0;
		for(Object value:list){
			String listKey=MessageFormat.format("{0}[{1}]",key,index++);
			paraUrl=addStringBuilderParaToUrl(paraUrl,listKey,value);
		}
		return paraUrl;
	}
	public static StringBuilder addStringBuilderObjectArrayToUrl(StringBuilder paraUrl,String key,List list){
		if (StringUtils.isEmpty(key) || list==null || list.size()==0)
			return paraUrl;
		int index=0;
		for(Object value:list){
			for(Field field:value.getClass().getDeclaredFields()){
				try{
					String listKey=MessageFormat.format("{0}[{1}].{2}",key,index,field.getName());
					paraUrl=addStringBuilderParaToUrl(paraUrl,listKey,field.get(value));
				}catch(Exception e){					
				}
			}
			index++;
		}
		return paraUrl;
	}
	
	public static String getParaFromUrl(String url,String para){
        return getParaFromUrl(url,para,"utf-8");
    }
    public static String getParaFromUrl(String url,String para,String encoding){
        if (url==null) return "";
        String[] arr=url.split("&");
        for(int i=0;i<arr.length;i++){
            if (arr[i].startsWith(para))
                try{
                    String ret=URLDecoder.decode(arr[i].substring(para.length() + 1, arr[i].length()),encoding);
                    return ret;
                }catch (Exception e){
                    return "";
                }
        }
        return "";
    }
    public static String getDocumentAt(String urlString){
        return getDocumentAt(urlString,null,null);
    }
    public static String getDocumentAt(String urlString,String ReqStr){
        return getDocumentAt(urlString,ReqStr,null);
    }
    
    public static String getDocumentAt(String urlString,String ReqStr,String encoding){
		String resStr="";
		Date dt=new Date();
        URL url =null;
        HttpURLConnection connection =null;
		try{
            url = new URL(urlString);
            connection =(HttpURLConnection)url.openConnection();
            /*System.setProperty("javax.net.ssl.keyStore","d:\\cfcakeystore_client.jks");
            System.setProperty("javax.net.ssl.keyStorePassword","***xx");
            System.setProperty( "javax.net.ssl.trustStore","E:\\Green\\Java\\jdk1.6.0_35\\jre\\lib\\security\\jssecacerts");
            System.setProperty( "javax.net.ssl.trustStorePassword","officepro");*/
            //System.setProperty("sun.net.client.defaultConnectTimeout",String.valueOf(Connection_Time_Out));
            //System.setProperty("sun.net.client.defaultReadTimeout",String.valueOf(Connection_Time_Out));
            //connection.setRequestMethod("POST");
           	connection.setRequestProperty("content-type", "application/json;chartset=utf-8");
            //connection.setRequestProperty("content-type", "text/html");//不设置，有些情况下可能无法接收结果
            connection.setConnectTimeout(Connection_Time_Out);
            connection.setReadTimeout(Connection_Time_Out);
            connection.setDoInput(true);
            if (!StringUtils.isEmpty(ReqStr)){
                connection.setDoOutput(true);
                OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
                out.write(ReqStr);            
                out.write("\r\n");
                out.flush();   
                out.close();               	
            }else{
            	connection.setDoOutput(false);
            }

            //connection.setRequestProperty("content-type","application/x-www-form-urlencoded;charset=utf-8");
            connection.connect();
            BufferedReader reader;
            if (encoding==null)
                reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            else
                reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
            String line="";
            while((line=reader.readLine())!=null){
            	resStr=resStr+line;   
            }
            reader.close();
        }catch(MalformedURLException e){
              System.out.println("URL Error------------------------------"+urlString);
              e.printStackTrace();
              resStr="ConnectError";
        }catch(IOException e){
             System.out.println("IOException occured while connecting----------------"+urlString);
             e.printStackTrace();
             resStr="ConnectError";
        }finally{
        	Date dt2=new Date();
        	System.out.println("Last---------------------"+(dt2.getTime()-dt.getTime())/1000);
        	if (connection!=null)
        		connection.disconnect();        	
            url =null;
            connection =null;
        }
		return resStr;
    }
    
    public static String getUrlReturn(String urlString){
        String resStr="";
        URL url =null;
        HttpURLConnection connection =null;
//        System.getProperties().put("proxySet", "true");        
//        System.getProperties().put("proxyHost", "127.0.0.1");
//        System.getProperties().put("proxyPort", "8580");        
        try{
            url = new URL(urlString);
            connection =(HttpURLConnection)url.openConnection();
            connection.setRequestProperty("content-type", "text/xml");
            connection.setConnectTimeout(Connection_Time_Out);
            connection.setReadTimeout(Connection_Time_Out);
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.connect();
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            while((line=reader.readLine())!=null){
                resStr=resStr+line;
            }
            reader.close();
        }catch(Exception e){
            System.out.println("IOException occured while connecting----------------"+urlString);
            e.printStackTrace();
            resStr="";
        }finally{
            if (connection!=null)
                connection.disconnect();
            url =null;
            connection =null;
        }
        return resStr;
    }

    public static String getHttpsReturn(String urlString,String ReqStr){
        String resStr="";
        URL url =null;
        HttpsURLConnection connection=null;
        try{
            url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session){
                    return true;
                }
            });
            //connection.setRequestProperty("content-type", "text/xml");
            connection.setRequestProperty("content-type","application/x-www-form-urlencoded;charset=GBK");
            connection.setConnectTimeout(Connection_Time_Out);
            connection.setReadTimeout(Connection_Time_Out);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
            out.write(ReqStr);
            //out.write("\r\n");
            out.flush();
            out.close();
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            while((line=reader.readLine())!=null){
                resStr=resStr+line;
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception occured while connecting----------------"+urlString);
            resStr="fail";
        }finally{
            if (connection!=null)
                connection.disconnect();
            url =null;
            connection =null;
        }
        return resStr;
    }
	public static String getXmlByPost (String url,Map<String,String> map) {
		HttpClient client = new HttpClient();
        /*List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("content-type", "application/x-www-form-urlencode"));
        client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);*/
        client.setTimeout(Connection_Time_Out);
        PostMethod post = new PostMethod(url);
        //post.getParams().setContentCharset("GBK");
        //post.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        NameValuePair[] pair=new NameValuePair[map.size()];
        int i=0;
        for(Entry<String,String> entry:map.entrySet()){
        	String key =(String)entry.getKey();
        	String val = (String)entry.getValue();
        	pair[i++]=new NameValuePair(key,val);
        }
        post.setRequestBody(pair);
        String result="";
        try{
        	int statusCode=client.executeMethod(post);
            System.out.println("status:"+post.getStatusCode()+",Text:"+post.getResponseBodyAsString());
        	if (statusCode != HttpStatus.SC_OK && statusCode !=400 && statusCode !=401) return "";
        	result =new String(post.getResponseBodyAsString());
        	return result;
        }catch(Exception e){
        	e.printStackTrace();
        	return "";
        }finally{
        	post.releaseConnection();
        }
    }
	public static InputStream getStreamByPost(String url,HashMap<String,String> map) {
		HttpClient client = new HttpClient();
        /*List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("content-type", "application/x-www-form-urlencode"));
        client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);*/
        PostMethod post = new PostMethod(url);
        NameValuePair[] pair=new NameValuePair[map.size()];
        int i=0;
        for(Entry<String,String> entry:map.entrySet()){
        	String key =(String)entry.getKey();
        	String val = (String)entry.getValue();
        	pair[i++]=new NameValuePair(key,val);
        }
        post.setRequestBody(pair);
        try{
        	int statusCode=client.executeMethod(post);
        	if (statusCode != HttpStatus.SC_OK && statusCode !=400 && statusCode !=401) return null;
        	return post.getResponseBodyAsStream();
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }finally{
        	post.releaseConnection();
        }
    }
	public static InputStream getStreamByGet(String url) {
		HttpClient client = new HttpClient();
        GetMethod post = new GetMethod(url);
        try{
        	int statusCode=client.executeMethod(post);
        	if (statusCode != HttpStatus.SC_OK && statusCode !=400 && statusCode !=401) return null;
        	return post.getResponseBodyAsStream();
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }finally{
        	//post.releaseConnection();
        }
    }	
/*	public static InputStream getStreamByGet(String urlString,String encoding) {
        URL url =null;
        HttpURLConnection connection =null;
		try{
            url = new URL(urlString);
            connection =(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(Connection_Time_Out);
           	connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.connect();
            return connection.getInputStream();
        }catch(Exception e){
             e.printStackTrace();
             return null;
        }finally{
//        	if (connection!=null)
//        		connection.disconnect();        	
//            url =null;
//            connection =null;
        }
    }	*/

    public static String getRemoteIp(HttpServletRequest request){
        String clientIp = request.getHeader("x-forwarded-for");
        if ((clientIp == null) || (clientIp.length() == 0) || ("unknown".equalsIgnoreCase(clientIp))) {
            clientIp = request.getHeader("X-Real-IP");
        }
        if ((clientIp == null) || (clientIp.length() == 0) || ("unknown".equalsIgnoreCase(clientIp))) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if ((clientIp == null) || (clientIp.length() == 0) || ("unknown".equalsIgnoreCase(clientIp))) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((clientIp == null) || (clientIp.length() == 0) || ("unknown".equalsIgnoreCase(clientIp))) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    public static void main(String[] args) {
    	try{
        	String data=FileUtils.readFileToString(new File("d:\\work\\doc\\json\\psDataCurve.json"));
        	String ret=HttpClientTool.getDocumentAt("http://192.168.1.60:8080/embroideryAction/generate_text_image.hyml",data);
    		
//        	String data=FileUtils.readFileToString(new File("d:\\work\\doc\\json\\psDataPadding.json"));
//        	String ret=HttpClientTool.getDocumentAt("http://localhost:8080/embroideryAction/generate_text_image.hyml",data);
//        	String data=FileUtils.readFileToString(new File("d:\\work\\doc\\json\\psDataComplexSpacing.json"));
//        	String ret=HttpClientTool.getDocumentAt("http://localhost:8080/embroideryAction/generate_complex_text_image.hyml",data);
//        	String data=FileUtils.readFileToString(new File("d:\\work\\doc\\json\\psDataTemplate.json"));
//        	String ret=HttpClientTool.getDocumentAt("http://localhost:8080/embroideryAction/generate_template_text_image.hyml",data);
    		System.out.println(ret);        	
    		
    		data=JsonTool.getJsonNodeDeep(ret,"data.url");
    		System.out.println("ReturnUrl:\n"+data);
    		System.out.println("DecodeUrl:\n"+URLDecoder.decode(data));
    		
    		    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
//    	

//    	HashMap<String,String> map=new HashMap<String,String>();
//    	try{
//        	String data=FileUtils.readFileToString(new File("d:\\temp\\regUser.json"));
//        	map.put("data", data);
//        	String result=HttpClientTool.getXmlByPost("http://api.printerstudio.com/user/add_user",map);
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
    	
    	//System.out.println(HttpClientTool.getUrlReturn("http://api.pulsemicro.com/1/List/Fonts?Type=ftEmbroidery"));
    	
    }

}
