package com.qpp.service.market;

import java.util.Map;

/**
 * Created by qpp on 8/11/2014.
 */
public interface Payment {

    //消费
    public Map<String,String> paypal(PaymentRequest paymentRequest);

    //查询
    public Map<String,String> findTransaction(PaymentRequest findRequest);

    //消费撤消
    public Map<String,String> cancelPay(PaymentRequest cancelRequest);

    //预授权
    public Map<String,String> preAuth(PaymentRequest preAuthRequest);

    //预授权完成
    public Map<String,String> authComplate(PaymentRequest authRequest);

    //预授权撤消
    public Map<String,String> cancelAuth(PaymentRequest cancelAuth);

    //退款
    public Map<String,String> refund(PaymentRequest refundRequest);


}
