package com.qpp.model;

/**
 * Created by admin on 2014/7/31.
 */
public class TLocinfo {
    private String locCode;
    private String locDesc;

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TLocinfo tLocinfo = (TLocinfo) o;

        if (locCode != null ? !locCode.equals(tLocinfo.locCode) : tLocinfo.locCode != null) return false;
        if (locDesc != null ? !locDesc.equals(tLocinfo.locDesc) : tLocinfo.locDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locCode != null ? locCode.hashCode() : 0;
        result = 31 * result + (locDesc != null ? locDesc.hashCode() : 0);
        return result;
    }
}
