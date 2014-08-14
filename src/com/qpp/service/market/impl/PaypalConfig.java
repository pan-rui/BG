package com.qpp.service.market.impl;

import com.qpp.service.market.PaymentConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qpp on 8/13/2014.
 */
public class PaypalConfig implements PaymentConfig {
    private String signMethod;
    private String signature;
    private String charset = "UTF-8";
    private String version;
    private String endPoint;

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

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Map<String, String> getConfigMap() {
        Map<String, String> configMap = new HashMap<String, String>();
        configMap.put("signMethod", signMethod);
        configMap.put("signature", signature);
        configMap.put("version", version);
        return configMap;
    }

    @Override
    public String getCharSet() {
        return null;
    }

    @Override
    public String getEndPoint() {
        return null;
    }
}
