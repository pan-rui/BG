package com.qpp.model;

/**
 * Created by admin on 2014/8/5.
 */
public class TAppRight {
    private Short role;
    private String url;

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
