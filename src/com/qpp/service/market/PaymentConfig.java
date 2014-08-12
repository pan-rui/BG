package com.qpp.service.market;

import java.util.Map;

/**
 * 支付配置接口
 * Created by qpp on 8/11/2014.
 */
public interface PaymentConfig {

    //配置信息
    public Map<String, String> getConfigMap();
    //读取字符编码
    public String getCharSet();

    //读取接入点
    public String getEndPoint();
}
