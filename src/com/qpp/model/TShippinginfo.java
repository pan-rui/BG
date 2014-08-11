package com.qpp.model;

/**
 * Created by admin on 2014/7/30.
 */
public class TShippinginfo {
    private String shippingCode;
    private String shippingDesc;
    private short priority;
    private Boolean valid;

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getShippingDesc() {
        return shippingDesc;
    }

    public void setShippingDesc(String shippingDesc) {
        this.shippingDesc = shippingDesc;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TShippinginfo that = (TShippinginfo) o;

        if (priority != that.priority) return false;
        if (shippingCode != null ? !shippingCode.equals(that.shippingCode) : that.shippingCode != null) return false;
        if (shippingDesc != null ? !shippingDesc.equals(that.shippingDesc) : that.shippingDesc != null) return false;
        if (valid != null ? !valid.equals(that.valid) : that.valid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shippingCode != null ? shippingCode.hashCode() : 0;
        result = 31 * result + (shippingDesc != null ? shippingDesc.hashCode() : 0);
        result = 31 * result + (int) priority;
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        return result;
    }
}
