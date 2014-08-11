package com.qpp.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.qpp.model.FacebookConfig;

public class facebookTool {
    private FacebookConfig config;
    private Logger logger= Logger.getLogger(this.getClass().getName());
    public facebookTool(){
        
    }

    public FacebookConfig getConfig() {
        return config;
    }

    public void setConfig(FacebookConfig config) {
        this.config = config;
    }
    public String getUserInfo(String code){
    	String urltoken=config.getServerAuthUrl(code);
    	String token=HttpClientTool.getUrlReturn(urltoken);
    	String access_token=HttpClientTool.getParaFromUrl(token,"access_token");
    	logger.debug("token return:"+access_token);
    	if (StringUtils.isEmpty(access_token)) return null;
    	String userUrl=config.getServerUserInfo(access_token);
    	String userInfo=HttpClientTool.getUrlReturn(userUrl);
    	logger.debug("Facebook user:"+userInfo);
    	//JsonTool.getJsonPathNode(userInfo,"email");//id,email,first_name,last_name
    	return userInfo;
    }
    
    public static void main(String[] args) {
        System.getProperties().put("proxySet", "true");        
        System.getProperties().put("proxyHost", "127.0.0.1");
        System.getProperties().put("proxyPort", "8580");        
        String path=System.getProperty("user.dir");
        FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(path+"\\WebContent\\WEB-INF\\spring\\applicationContext-baseSet.xml");
        FacebookConfig config=(FacebookConfig)ctx.getBean("FacebookSet");
        facebookTool qqTool=new facebookTool();
        qqTool.setConfig(config);
        //System.out.println(config.getServerLoginUrl());
        qqTool.getUserInfo("AQAvP9a0vNL3FFsexKvLgnd1w523AzuOwC7MIRjeOcL8xJe5pTu7__ecZMHZuRFT2oJBkpCmlUFj799fIAT8rlvD33w-7tF3pdaN8qwesLX5qZbC5SJozK3Y00ldXY7SsDSkv9b4mcy_N4lXb65u77Ud2rUQmuVZm7BJ3lSa3eo-Alq2_H1bMMrifoQP2KCARS7bCqfW7vpYPx4qZ5AlJowc9VMDQa9IhNS7VwmoybvjNyS5BcBw1NC4rz3zWoAc0wU6ECMGXw3AXrnS001VbXm-x8-u6Sv3EqGTnKTY-KSohzb82DyaiyPHUgegZNlrFjHkTNizXMUnK1GRa0VuiH94#_=_");
        //qqTool.getToken2Url("222F8C00D1BF2FF65E4CA06A736E3948");
        //CommonRet ret=qqTool.getUserInfo("051BCCCBE09FBC94E3C9FDDB7439F917");
        //System.out.println(config.getServerShareLink("http://www.163.com",""));
    }
}