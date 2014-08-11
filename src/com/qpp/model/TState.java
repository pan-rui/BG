package com.qpp.model;

import java.io.Serializable;

/**
 * Created by admin on 2014/7/29.
 */
public class TState implements Serializable {
    private String countryCode;
    private String stateCode;
    private String stateDesc;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TState tState = (TState) o;

        if (countryCode != null ? !countryCode.equals(tState.countryCode) : tState.countryCode != null) return false;
        if (stateCode != null ? !stateCode.equals(tState.stateCode) : tState.stateCode != null) return false;
        if (stateDesc != null ? !stateDesc.equals(tState.stateDesc) : tState.stateDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryCode != null ? countryCode.hashCode() : 0;
        result = 31 * result + (stateCode != null ? stateCode.hashCode() : 0);
        result = 31 * result + (stateDesc != null ? stateDesc.hashCode() : 0);
        return result;
    }
}
