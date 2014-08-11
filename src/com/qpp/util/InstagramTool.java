package com.qpp.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.qpp.model.InstagramConfig;

public class InstagramTool {

	    private InstagramConfig config;
	    private Logger logger= Logger.getLogger(this.getClass().getName());
	    public InstagramTool(){
	    }
	    	   
		public InstagramConfig getConfig() {
			return config;
		}
		public void setConfig(InstagramConfig config) {
			this.config = config;
		}
		//photolist:
		//https://api.instagram.com/v1/users/1429350798/media/recent?access_token=1429350798.f59def8.3721bd6e18734e0f95505927ca4d1f3f
		 public String getUserInfo(String code){
		    	String urltoken=config.getAuthUrl();
		    	Map<String,String> map=config.getAuthMap(code);
		    	System.out.println(urltoken);
		    	String userInfo=HttpClientTool.getXmlByPost(urltoken, map);
		    	logger.debug("Facebook user:"+userInfo);
		    	//JsonTool.getJsonPathNode(userInfo,"email");//access_token,user.id,user.username,full_name,profile_picture//1429350798
		    	return userInfo;
		    	//return ""; 
		    }		
		public static void main(String[] args) {
	        String path=System.getProperty("user.dir");
	        FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(path+"\\WebContent\\WEB-INF\\spring\\applicationContext-baseSet.xml");
	        InstagramConfig config=(InstagramConfig)ctx.getBean("InstagramSet");
	        InstagramTool Tool=new InstagramTool();
	        Tool.setConfig(config);
	        //System.out.println(config.getServerLoginUrl());
	        Tool.getUserInfo("fb13c90b129b456abe19c12f795ec01c");

		}

}
