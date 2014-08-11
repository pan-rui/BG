package com.qpp.model;

import java.io.Serializable;

/**
 * Created by admin on 2014/8/5.
 */
public class TAppRightPK implements Serializable {
    private Short role;
    private String url;

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

        TAppRightPK that = (TAppRightPK) o;

        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
