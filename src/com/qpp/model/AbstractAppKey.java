package com.qpp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.type.EnumType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by SZ_it123 on 2014/9/25.
 */
public class AbstractAppKey  implements Serializable{
    private static final long serialVersionUID = -2034602288483190966L;
    private long oid;
    //@NotNull
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
}
