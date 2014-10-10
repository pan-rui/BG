package com.qpp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by SZ_it123 on 2014/9/23.
 */
@Document
public class ApiLog {
    @Id
    private String id;
    private String apiUrl;
    private String ip;
    private Date createDate;
    private long appId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
