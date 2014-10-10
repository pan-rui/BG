package com.qpp.model;

import java.io.Serializable;

/**
 * Created by qpp on 9/24/2014.
 */
public abstract class Priciple implements Serializable {
    public enum AuthType implements Serializable{
        USER,USER_ROLE,ROLE
    }
//    @NotNull
    protected long oid;
    protected abstract long getOid();
    protected abstract void setOid(long oid);
    protected AuthType authType;

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }
}
