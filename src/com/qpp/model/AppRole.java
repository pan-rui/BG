package com.qpp.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by SZ_it123 on 2014/9/25.
 */
public class AppRole implements Serializable{
    private static final long serialVersionUID = -3711454377270071816L;
    @NotNull
    private int appid;
    @NotNull
    private int levelid;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppRole appRole = (AppRole) o;

        if (appid != appRole.appid) return false;
        if (levelid != appRole.levelid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appid;
        result = 31 * result + levelid;
        return result;
    }
}
