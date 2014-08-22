package com.qpp.service.market.impl;

import com.qpp.service.market.MessageInfo;
import com.qpp.service.market.Payment;
import com.qpp.service.market.PaymentRequest;
import com.qpp.service.market.PaymentSupport;
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
        paypalConfig.setEndPoint(MessageInfo.getMessage("Paypal.endPoint"));
        paypalConfig.setSignature(MessageInfo.getMessage("Paypal.Signature"));
        paypalConfig.setSignMethod(MessageInfo.getMessage("Paypal.SignMethod"));
        paypalConfig.setVersion(MessageInfo.getMessage("Paypal.version"));
        paypalConfig.setCharset(MessageInfo.getMessage("Paypal.charset"));
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
        Map<String,String> reqMap=preAuthRequest.getNVPRequest();
        Map<String, String> resultMap=null;
        reqMap.putAll(paypalConfig.getConfigMap());
        if(reqMap.get("TOKEN")==null) {

            reqMap.put("METHOD", "SetExpressCheckout");
            reqMap.put("PAYMENTACTION", "Authorization");
             resultMap= PaymentSupport.execute(reqMap, paypalConfig);
        }else {
            reqMap.put("METHOD", "GetExpressCheckoutDetails");
            resultMap=PaymentSupport.execute(reqMap, paypalConfig);
            String strAck = resultMap.get("ACK");
            if(strAck !=null && (strAck.equals("Success") || strAck.equals("SuccessWithWarning"))) {
                reqMap.put("METHOD", "DoExpressCheckoutPayment");
                reqMap.put("PAYERID", resultMap.get("PayerID"));
                reqMap.put("PAYMENTACTION", "Authorization");
//                reqMap.put("AMT", reqMap.get("AMT"));
//                reqMap.put("CURRENCYCODE",reqMap.get("CURRENCYCODE"));
                resultMap = PaymentSupport.execute(reqMap, paypalConfig);
            }

        }


        return resultMap;
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

    public Map<String,String> getExpress(PaymentRequest gRequest){
        return null;
    }
}
