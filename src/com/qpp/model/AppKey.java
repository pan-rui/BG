package com.qpp.model;


import com.qpp.validate.KeyInTable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2014/7/30.
 */
public class AppKey extends AbstractAppKey implements Serializable{
    private static final long serialVersionUID = -6307364007685813685L;
    private String appkey;
    @NotNull
    @KeyInTable(TUser.class)
    private long userid;
    @NotNull
    private String domain;
    private Short status;
    private Short isbuildin;
    private Date create_date;
    private String token;
    private Date startTime;

    public Date getCreate_date() {
        return create_date;
    }
    @Override
    public AppKeyType getType() {
        return AppKeyType.APP_KEY.APP_KEY;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String key) {
        this.appkey = key;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getIsbuildin() {
        return isbuildin;
    }

    public void setIsbuildin(Short isbuildin) {
        this.isbuildin = isbuildin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
