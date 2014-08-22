package com.qpp.service.market.impl;

import com.qpp.service.market.MessageInfo;
import com.qpp.service.market.Payment;
import com.qpp.service.market.PaymentRequest;
import com.qpp.service.market.PaymentSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 银联交易类
 * transType
 01	消费
 31	消费撤销
 02	预授权
 32	预授权撤销
 03	预授权完成
 33	预授权完成撤销
 04	退货
 *queryResult
 0	成功	承兑交易
 1	失败	拒绝交易
 2	处理中	稍后查询或者拒绝交易
 3	无此交易	拒绝交易
 * Created by qpp on 8/12/2014.
 */
@Component
public class UnionPayment implements Payment {
    public static UnionPayConfig unionPayConfig= new UnionPayConfig();
    //    private HttpClient httpClient;
    private String queryEndPoint = MessageInfo.getMessage("Union.queryEndpoint");
    private Logger logger = Logger.getLogger(this.getClass());
    static {
        unionPayConfig.setEndPoint(MessageInfo.getMessage("Union.endPoint"));
        unionPayConfig.setMerAbbr(MessageInfo.getMessage("Union.merAbbr"));
        unionPayConfig.setMerId(MessageInfo.getMessage("Union.merId"));
        unionPayConfig.setMerCode(MessageInfo.getMessage("Union.merCode"));
        unionPayConfig.setSignature(MessageInfo.getMessage("Union.signature"));
        unionPayConfig.setSignMethod(MessageInfo.getMessage("Union.signMethod"));
        unionPayConfig.setVersion(MessageInfo.getMessage("Union.version"));
        unionPayConfig.setCharset(MessageInfo.getMessage("Union.charset"));
    }

    public UnionPayment(){
//        httpClient= PaymentSupport.initHttpClient();
    }

    @Override
    public Map<String,String> paypal(PaymentRequest paymentRequest) {
        UnionpayRequest unionpayRequest=(UnionpayRequest)paymentRequest;
        unionpayRequest.setTransType("01");
//        unionpayRequest.setBackEndUrl("通知URL");
//        unionpayRequest.setFrontEndUrl("返回URL");
//        unionpayRequest.setOrderTime(formatDate(new Date()));
//        unionpayRequest.setTransferFee("运输费用");//订单提交前页面计算....
        unionpayRequest.setTransTimeout(String.valueOf(3 * 60 * 1000));
//        unionpayRequest.setOrderNumber(RandomSymbol.getAllSymbol(16));
//        unionpayRequest.setDefaultPayType("ExpressPay"); //储值卡支付
//          unionpayRequest.setCustomerIp("持卡人IP");
//        unionpayRequest.setOrigQid("原始交易流水号");
        Map<String, String> reqMap = unionpayRequest.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
//        reqMap.put("transType", "01");
//        reqMap.put("backEndUrl", "通知URL");
//        reqMap.put("frontEndUrl", "返回URL");

        return PaymentSupport.execute(reqMap, unionPayConfig);
    }

    @Override
    public Map<String,String> findTransaction(PaymentRequest findRequest) {
        Map<String, String> reqMap = findRequest.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
        reqMap.put("transType", "71");
        UnionPayConfig unionPayConfig1=new UnionPayConfig();
        BeanUtils.copyProperties(unionPayConfig,unionPayConfig1,new String[]{"endPoint"});
        unionPayConfig1.setEndPoint(queryEndPoint);
        return PaymentSupport.execute(reqMap, unionPayConfig1);
    }

    @Override
    public Map<String,String> cancelPay(PaymentRequest cancelRequest) {
        Map<String, String> reqMap = cancelRequest.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
        reqMap.put("transType", "31");
        return PaymentSupport.execute(reqMap, unionPayConfig);
    }

    @Override
    public Map<String,String> preAuth(PaymentRequest preAuthRequest) {
        Map<String, String> reqMap = preAuthRequest.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
        reqMap.put("transType", "02");
        return PaymentSupport.execute(reqMap, unionPayConfig);
    }

    @Override
    public Map<String,String> authComplate(PaymentRequest authRequest) {
        Map<String, String> reqMap = authRequest.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
        reqMap.put("transType", "03");
        return PaymentSupport.execute(reqMap, unionPayConfig);
    }

    @Override
    public Map<String,String> cancelAuth(PaymentRequest cancelAuth) {
        Map<String, String> reqMap = cancelAuth.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
        reqMap.put("transType", "32");
        return PaymentSupport.execute(reqMap, unionPayConfig);
    }

    @Override
    public Map<String,String> refund(PaymentRequest refundRequest) {
        Map<String, String> reqMap = refundRequest.getNVPRequest();
        reqMap.putAll(unionPayConfig.getConfigMap());
        reqMap.put("transType", "05");
        return PaymentSupport.execute(reqMap, unionPayConfig);
    }

public static String formatDate(Date date) {
    return new SimpleDateFormat("yyyyMMddhhmmss").format(date);
}
}
