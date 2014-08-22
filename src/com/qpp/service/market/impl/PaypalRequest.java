package com.qpp.service.market.impl;

import com.qpp.model.TOrder;
import com.qpp.service.market.PaymentRequest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qpp on 8/13/2014.
 */
public class PaypalRequest implements PaymentRequest {
    private String method; //支付类型
    private String returnUrl;
    private String cancelUrl;
    private String amt;
    private String currencyCode; //默认为USD
    private String maxAmt; //最大金额,可忽略
    private String paymentAction = "Authorization";
    private String email;
    private String desc;
    private String invnum; //账单号或其它识别号
    private String noShipping = "1"; //在付款页面不上显示送货地址字段
    private String addroverride = "1"; //使用在Set里设置的送货地址,而非Paypal账户里的地址
    private String token;
    private String localeCode;
    private String hdrImg; //付款页面左上角显示图片的URL
    private String custom; //自定义字段
    private String shiptoName; //送货人姓名
    private String phoneNum;
    private String shipToStreet; //送货街道地址
    private String shipToCity;// 城市名称
    private String shipToCountryCode;//国家代码
    private String shipToZip;//邮编
    private String transactionId; //交易号
    private String authorizAtionId;//捕获付款的授权ID,从DoExpressCheckoutPayment或DoDirectPayment返回的交易号
    private String shippingAmt;// 运输费用
    private String itemAmt; //商品总费用
    private String insuranceAmt; //保险费用
    private String taxAmt;//税收费用

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(String maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getPaymentAction() {
        return paymentAction;
    }

    public void setPaymentAction(String paymentAction) {
        this.paymentAction = paymentAction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInvnum() {
        return invnum;
    }

    public void setInvnum(String invnum) {
        this.invnum = invnum;
    }

    public String getNoShipping() {
        return noShipping;
    }

    public void setNoShipping(String noShipping) {
        this.noShipping = noShipping;
    }

    public String getAddroverride() {
        return addroverride;
    }

    public void setAddroverride(String addroverride) {
        this.addroverride = addroverride;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getHdrImg() {
        return hdrImg;
    }

    public void setHdrImg(String hdrImg) {
        this.hdrImg = hdrImg;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getShiptoName() {
        return shiptoName;
    }

    public void setShiptoName(String shiptoName) {
        this.shiptoName = shiptoName;
    }

    public String getShipToStreet() {
        return shipToStreet;
    }

    public void setShipToStreet(String shipToStreet) {
        this.shipToStreet = shipToStreet;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToCountryCode() {
        return shipToCountryCode;
    }

    public void setShipToCountryCode(String shipToCountryCode) {
        this.shipToCountryCode = shipToCountryCode;
    }

    public String getShipToZip() {
        return shipToZip;
    }

    public void setShipToZip(String shipToZip) {
        this.shipToZip = shipToZip;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAuthorizAtionId() {
        return authorizAtionId;
    }

    public void setAuthorizAtionId(String authorizAtionId) {
        this.authorizAtionId = authorizAtionId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getShippingAmt() {
        return shippingAmt;
    }

    public void setShippingAmt(String shippingAmt) {
        this.shippingAmt = shippingAmt;
    }

    public String getItemAmt() {
        return itemAmt;
    }

    public void setItemAmt(String itemAmt) {
        this.itemAmt = itemAmt;
    }

    public String getInsuranceAmt() {
        return insuranceAmt;
    }

    public void setInsuranceAmt(String insuranceAmt) {
        this.insuranceAmt = insuranceAmt;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
    }

    @Override
    public Map<String, String> getNVPRequest() {
        Map<String, String> nvpRequest = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object obj = field.get(this);
                if (obj != null && obj instanceof String && !"".equals(obj))
                    nvpRequest.put(field.getName().toUpperCase(), (String) obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return nvpRequest;
    }

    @Override
    public void setNVPResponse(Map<String, String> nvpResponse) {

    }

    @Override
    public Map<String, String> getResponse() {
        return null;
    }

    @Override
    public void setOrderParam(TOrder order) {
        setAmt(String.valueOf(getAmt()));
        setAuthorizAtionId(order.getTransactionId());
        setCurrencyCode(order.getCurrencyCode());
        setEmail(order.getEmail());
        setLocaleCode(order.getLocalCode());
        setDesc(order.getDesc());
        setPaymentAction(order.getPayMentAction());
        setShipToZip(order.getZipCode());
//        setShipToCity();
        setShipToCountryCode(order.getLocalCode());
//        setShipToStreet();
//        setShiptoName(order.getTUser().getName());
        setPhoneNum(order.getPhone());
        setItemAmt(String.valueOf(order.getItemAmt()));
        setInsuranceAmt(String.valueOf(order.getInsuranceAmt()));
        setShippingAmt(String.valueOf(order.getShippingAmt()));
        setTaxAmt(String.valueOf(order.getTaxAmt()));
        setAmt(String.valueOf(order.getAmt()));

    }


}
