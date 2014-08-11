package com.qpp.model;

/**
 * Created by admin on 2014/7/29.
 */
public class TCountry {
    private String countryCode;
    private String countryDesc;
    private String cnCountryDesc;
    private String areaCode;
    private String areaDesc;
    private Integer timeDiff;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryDesc() {
        return countryDesc;
    }

    public void setCountryDesc(String countryDesc) {
        this.countryDesc = countryDesc;
    }

    public String getCnCountryDesc() {
        return cnCountryDesc;
    }

    public void setCnCountryDesc(String cnCountryDesc) {
        this.cnCountryDesc = cnCountryDesc;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }

    public Integer getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(Integer timeDiff) {
        this.timeDiff = timeDiff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCountry tCountry = (TCountry) o;

        if (areaCode != null ? !areaCode.equals(tCountry.areaCode) : tCountry.areaCode != null) return false;
        if (areaDesc != null ? !areaDesc.equals(tCountry.areaDesc) : tCountry.areaDesc != null) return false;
        if (cnCountryDesc != null ? !cnCountryDesc.equals(tCountry.cnCountryDesc) : tCountry.cnCountryDesc != null)
            return false;
        if (countryCode != null ? !countryCode.equals(tCountry.countryCode) : tCountry.countryCode != null)
            return false;
        if (countryDesc != null ? !countryDesc.equals(tCountry.countryDesc) : tCountry.countryDesc != null)
            return false;
        if (timeDiff != null ? !timeDiff.equals(tCountry.timeDiff) : tCountry.timeDiff != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryCode != null ? countryCode.hashCode() : 0;
        result = 31 * result + (countryDesc != null ? countryDesc.hashCode() : 0);
        result = 31 * result + (cnCountryDesc != null ? cnCountryDesc.hashCode() : 0);
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (areaDesc != null ? areaDesc.hashCode() : 0);
        result = 31 * result + (timeDiff != null ? timeDiff.hashCode() : 0);
        return result;
    }
}
