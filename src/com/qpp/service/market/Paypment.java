package com.qpp.service.market;

import com.qpp.model.BaseReturn;

/**
 * Created by qpp on 8/11/2014.
 */
public interface Paypment {

    //消费
    public BaseReturn paypal(PaypmentRequest paypmentRequest);

    //查询
    public BaseReturn findTransaction(PaypmentRequest findRequest);

    //消费撤消
    public BaseReturn cancelPay(PaypmentRequest cancelRequest);

    //预授权
    public BaseReturn preAuth(PaypmentRequest preAuthRequest);

    //预授权完成
    public BaseReturn authComplate(PaypmentRequest authRequest);

    //预授权撤消
    public BaseReturn cancelAuth(PaypmentRequest cancelAuth);

    //退款
    public BaseReturn refund(PaypmentRequest refundRequest);

    //构造请求
    public String getRequest(PaymentConfig paymentConfig);

}
