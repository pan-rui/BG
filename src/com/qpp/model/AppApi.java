package com.qpp.model;

import com.qpp.validate.KeyInTable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by SZ_it123 on 2014/9/25.
 */
public class AppApi implements Serializable{
    private static final long serialVersionUID = -7614516684718998608L;
    @NotNull
    @KeyInTable(AppKey.class)
    private long oid;
    @NotNull
    @KeyInTable(ApiInfo.class)
    private int apiId;
    @NotNull
    private AppKeyType type;

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


    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

}
