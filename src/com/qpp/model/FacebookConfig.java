package com.qpp.model;

import java.text.MessageFormat;

import com.qpp.util.HttpClientTool;

public class FacebookConfig {
	private String loginUrl;
	private String authUrl;
	private String userUrl;
	private String permission;
	private String client_id;
	private String secret;
	private String localUri;
	private String shareUrl;
	private String sharedialog;
	
	
	public String getSharedialog() {
		return sharedialog;
	}
	public void setSharedialog(String sharedialog) {
		this.sharedialog = sharedialog;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getLocalUri() {
		return localUri;
	}
	public void setLocalUri(String localUri) {
		this.localUri = localUri;
	}
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
	
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getServerLoginUrl(){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"client_id",client_id);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"redirect_uri",localUri);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"scope",permission);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(loginUrl);
		urlAll.append(url);
		return urlAll.toString();
	}
	public String getServerAuthUrl(String code){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"client_id",client_id);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"redirect_uri",localUri);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"client_secret",secret);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"code",code);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(authUrl);
		urlAll.append(url);
		return urlAll.toString();
	}
	public String getServerUserInfo(String token){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"access_token",token);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(userUrl);
		urlAll.append(url);
		return urlAll.toString();
	}
	public String getServerShareLink(String href,String redirect_uri){
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"app_id",client_id);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"display","popup");
		url=HttpClientTool.addStringBuilderParaToUrl(url,"href",href);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"redirect_uri",redirect_uri);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(sharedialog);
		urlAll.append(url);
		return urlAll.toString();
	}
	public String setServerShareMessage(String uid,String message,String token){
		//?message={1}&access_token={2}
		StringBuilder url=new StringBuilder();
		url=HttpClientTool.addStringBuilderParaToUrl(url,"message",message);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"access_token",token);
		//url=HttpClientTool.addStringBuilderParaToUrl(url,"privacy","ALL_FRIENDS");
		StringBuilder urlAll=new StringBuilder();
		String locshareUrl=MessageFormat.format(shareUrl,uid);
		urlAll.append(locshareUrl);
		urlAll.append(url);
		return urlAll.toString();
	}
	

}
