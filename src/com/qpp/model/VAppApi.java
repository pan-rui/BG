package com.qpp.model;

import java.io.Serializable;

/**
 * Created by SZ_it123 on 2014/9/26.
 */
public class VAppApi implements Serializable {
    private static final long serialVersionUID = -8013727948271890920L;
    private long oid;
    private AppKeyType type;
    private long apiid;
    private String apiurl;
    private String apidesc;

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public AppKeyType getType() {
        return type;
    }

    public void setType(AppKeyType type) {
        this.type = type;
    }

    public long getApiid() {
        return apiid;
    }

    public void setApiid(long apiid) {
        this.apiid = apiid;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getApidesc() {
        return apidesc;
    }

    public void setApidesc(String apidesc) {
        this.apidesc = apidesc;
    }


}
