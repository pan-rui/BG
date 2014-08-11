package com.qpp.model;

import java.io.Serializable;

/**
 * Created by admin on 2014/7/29.
 */
public class TStatePK implements Serializable {
    private String countryCode;
    private String stateCode;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TStatePK tStatePK = (TStatePK) o;

        if (countryCode != null ? !countryCode.equals(tStatePK.countryCode) : tStatePK.countryCode != null)
            return false;
        if (stateCode != null ? !stateCode.equals(tStatePK.stateCode) : tStatePK.stateCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryCode != null ? countryCode.hashCode() : 0;
        result = 31 * result + (stateCode != null ? stateCode.hashCode() : 0);
        return result;
    }
}
