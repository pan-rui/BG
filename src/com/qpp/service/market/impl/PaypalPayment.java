package com.qpp.service.market.impl;

import com.qpp.service.market.MessageInfo;
import com.qpp.service.market.Payment;
import com.qpp.service.market.PaymentRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Paypal支付类
 * Created by qpp on 8/13/2014.
 */
@Component
public class PaypalPayment implements Payment {
    public static PaypalConfig paypalConfig= new PaypalConfig();
    private Logger logger = Logger.getLogger(this.getClass());
    static {
        paypalConfig.setEndPoint(MessageInfo.getMessage("Union.endPoint"));
        paypalConfig.setSignature(MessageInfo.getMessage("Union.Signature"));
        paypalConfig.setSignMethod(MessageInfo.getMessage("Union.SignMethod"));
        paypalConfig.setVersion(MessageInfo.getMessage("Union.version"));
        paypalConfig.setCharset(MessageInfo.getMessage("Union.charset"));
    }
    @Override
    public Map<String, String> paypal(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public Map<String, String> findTransaction(PaymentRequest findRequest) {
        return null;
    }

    @Override
    public Map<String, String> cancelPay(PaymentRequest cancelRequest) {
        return null;
    }

    @Override
    public Map<String, String> preAuth(PaymentRequest preAuthRequest) {
        return null;
    }

    @Override
    public Map<String, String> authComplate(PaymentRequest authRequest) {
        return null;
    }

    @Override
    public Map<String, String> cancelAuth(PaymentRequest cancelAuth) {
        return null;
    }

    @Override
    public Map<String, String> refund(PaymentRequest refundRequest) {
        return null;
    }
}
