package com.qpp.model;

import java.io.Serializable;

/**
 * Created by admin on 2014/8/21.
 */
public class TRoleInfo implements Serializable{
    private static final long serialVersionUID = -8655089772418943339L;
    private int role;
    private String roleDesc;
    private String defaultPage;

    public TRoleInfo(){

    }
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TRoleInfo tRoleInfo = (TRoleInfo) o;

        if (role != tRoleInfo.role) return false;
        if (defaultPage != null ? !defaultPage.equals(tRoleInfo.defaultPage) : tRoleInfo.defaultPage != null)
            return false;
        if (roleDesc != null ? !roleDesc.equals(tRoleInfo.roleDesc) : tRoleInfo.roleDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = role;
        result = 31 * result + (roleDesc != null ? roleDesc.hashCode() : 0);
        result = 31 * result + (defaultPage != null ? defaultPage.hashCode() : 0);
        return result;
    }
}
