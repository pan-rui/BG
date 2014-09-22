package com.qpp.model;

import java.io.Serializable;

/**
 * Created by admin on 2014/8/5.
 */
public class TAppRight implements Serializable{
    private static final long serialVersionUID = -7357650302701042766L;
    private Short role;
    private String url;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TAppRight tAppRight = (TAppRight) o;

        if (role != null ? !role.equals(tAppRight.role) : tAppRight.role != null) return false;
        if (url != null ? !url.equals(tAppRight.url) : tAppRight.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
