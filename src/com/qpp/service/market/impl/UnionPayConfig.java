package com.qpp.service.market.impl;

import com.qpp.service.market.PaymentConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联支付配置
 * Created by qpp on 8/12/2014.
 */
public class UnionPayConfig implements PaymentConfig {
    private String signMethod;
    private String signature;
    private String merAbbr; //商户名称
    private String merId; //商户代码
    private String merCode; //商户类型
    private String charset = "UTF-8";
    private String version;
    private String endPoint;

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMerAbbr() {
        return merAbbr;
    }

    public void setMerAbbr(String merAbbr) {
        this.merAbbr = merAbbr;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }


    @Override
    public Map<String, String> getConfigMap() {
        Map<String, String> configMap = new HashMap<String, String>();
        configMap.put("signMethod", signMethod);
        configMap.put("signature", signature);
        configMap.put("merAbbr", merAbbr);
        configMap.put("merId", merId);
        configMap.put("merCode", merCode);
        configMap.put("version", version);
        return configMap;
    }

    @Override
    public String getCharSet() {
        return charset;
    }

    @Override
    public String getEndPoint() {
        return endPoint;
    }
}
