package com.qpp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by admin on 2014/7/30.
 */
public class TAppInfo implements Serializable{
    private static final long serialVersionUID = -6307364007685813685L;
    private String appid;
    private String appkey;
    private Short role;
    private Date startTime;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String key) {
        this.appkey = key;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TAppInfo tAppInfo = (TAppInfo) o;

        if (appid != null ? !appid.equals(tAppInfo.appid) : tAppInfo.appid != null) return false;
        if (appkey != null ? !appkey.equals(tAppInfo.appkey) : tAppInfo.appkey != null) return false;
        if (role != null ? !role.equals(tAppInfo.role) : tAppInfo.role != null) return false;
        if (startTime != null ? !startTime.equals(tAppInfo.startTime) : tAppInfo.startTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appid != null ? appid.hashCode() : 0;
        result = 31 * result + (appkey != null ? appkey.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
    }
}
