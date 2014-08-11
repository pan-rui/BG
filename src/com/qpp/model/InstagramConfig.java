package com.qpp.model;

import java.util.HashMap;
import java.util.Map;

import com.qpp.util.HttpClientTool;

public class InstagramConfig {
	private String loginUrl;
	private String authUrl;
	private String client_id;
	private String secret;
	private String localUri;

	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getLocalUri() {
		return localUri;
	}
	public void setLocalUri(String localUri) {
		this.localUri = localUri;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public String getServerLoginUrl(){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"client_id",client_id);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"redirect_uri",localUri);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"response_type","code");
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(loginUrl);
		urlAll.append(url);
		return urlAll.toString();
	}
/*	
 * public String getServerAuthUrl(String code){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"client_id",client_id);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"client_secret",secret);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"grant_type","authorization_code");
		url=HttpClientTool.addStringBuilderParaToUrl(url,"redirect_uri",localUri);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"code",code);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(authUrl);
		urlAll.append(url);
		return urlAll.toString();
	}
*/	
	public Map<String,String> getAuthMap(String code){
		Map<String,String> map=new HashMap<String,String>();
		map.put("client_id",client_id);
		map.put("client_secret",secret);
		map.put("grant_type","authorization_code");
		map.put("redirect_uri",localUri);
		map.put("code",code);
		return map;
	}

	
}
