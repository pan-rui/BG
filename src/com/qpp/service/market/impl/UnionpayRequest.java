package com.qpp.service.market.impl;

import com.qpp.model.TOrder;
import com.qpp.service.market.PaymentRequest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 银联交易请求参数封装类
 * Created by qpp on 8/11/2014.
 */
public class UnionpayRequest implements PaymentRequest {
//    @Resource
//    private AwsUtil awsUtil;
//    private Map<String,String> nvpRequest;
//    private String version;
//    private String charset;

    private String transType; //交易类型

    private String backEndUrl; //通知URL
    private String frontEndUrl; //返回URL
    private String acqCode; //收单机构代码
    private String orderTime; //交易开始日期时间
    private String transTimeout;
//    private String respTime; //交易完成时间
    private String orderNumber; //商户订单号
    private String commodityName; //商品名称
    private String commodityUrl; //商品URL
    private String commodityUnitPrice; //商品价格
    private String commodityQuantity; //商品数量
    private String transferFee;  //运输费用
    private String commodityDiscount; //优惠信息
    private String orderAmount;  //交易金额
    private String orderCurrency; // backup TstateInfo
    private String customerName; //持卡人姓名
    private String defaultPayType; //默认支付方式 	LitePay（认证支付）,ProPay（快捷支付）,CommonPay（小额支付）,ExpressPay（储值卡支付）,CSPay（网银支付）,DirectPay（后台支付），ICPay（IC卡支付）
    private String defaultBankNumber; //默认银行编码
    private String customerIp; //持卡人IP
    private String qid; //交易流水号
    private String traceNumber; //系统跟踪号
    private String traceTime; //系统跟踪时间
    private String origQid; //原始交易流水号
    private String merReserved; //商户保留域
    private String cupReseverd; //系统保留域
    private String respCode; //响应码
    private String respTime; //响应时间
    private String respMsg; //响应信息
    private String settleAmount; //清算金额
    private String settleCurrency; //清算币种
    private String exchangeRate; //清算汇率

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    private String exchangeDate; //兑换日期
    private String settleDate; //清算日期
    private String queryResult; //查询结果

    public UnionpayRequest() {
//        this.nvpRequest = new HashMap<String, String>();
    }

//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getCharset() {
//        return charset;
//    }
//
//    public void setCharset(String charset) {
//        this.charset = charset;
//    }

      public String getBackEndUrl() {
        return backEndUrl;
    }

    public void setBackEndUrl(String backEndUrl) {
        this.backEndUrl = backEndUrl;
    }

    public String getFrontEndUrl() {
        return frontEndUrl;
    }

    public void setFrontEndUrl(String frontEndUrl) {
        this.frontEndUrl = frontEndUrl;
    }

    public String getAcqCode() {
        return acqCode;
    }

    public void setAcqCode(String acqCode) {
        this.acqCode = acqCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTransTimeout() {
        return transTimeout;
    }

    public void setTransTimeout(String transTimeout) {
        this.transTimeout = transTimeout;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityUrl() {
        return commodityUrl;
    }

    public void setCommodityUrl(String commodityUrl) {
        this.commodityUrl = commodityUrl;
    }

    public String getCommodityUnitPrice() {
        return commodityUnitPrice;
    }

    public void setCommodityUnitPrice(String commodityUnitPrice) {
        this.commodityUnitPrice = commodityUnitPrice;
    }

    public String getCommodityQuantity() {
        return commodityQuantity;
    }

    public void setCommodityQuantity(String commodityQuantity) {
        this.commodityQuantity = commodityQuantity;
    }

    public String getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(String transferFee) {
        this.transferFee = transferFee;
    }

    public String getCommodityDiscount() {
        return commodityDiscount;
    }

    public void setCommodityDiscount(String commodityDiscount) {
        this.commodityDiscount = commodityDiscount;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDefaultPayType() {
        return defaultPayType;
    }

    public void setDefaultPayType(String defaultPayType) {
        this.defaultPayType = defaultPayType;
    }

    public String getDefaultBankNumber() {
        return defaultBankNumber;
    }

    public void setDefaultBankNumber(String defaultBankNumber) {
        this.defaultBankNumber = defaultBankNumber;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getTraceNumber() {
        return traceNumber;
    }

    public void setTraceNumber(String traceNumber) {
        this.traceNumber = traceNumber;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getOrigQid() {
        return origQid;
    }

    public void setOrigQid(String origQid) {
        this.origQid = origQid;
    }

    public String getMerReserved() {
        return merReserved;
    }

    public void setMerReserved(String merReserved) {
        this.merReserved = merReserved;
    }

    public String getCupReseverd() {
        return cupReseverd;
    }

    public void setCupReseverd(String cupReseverd) {
        this.cupReseverd = cupReseverd;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getSettleCurrency() {
        return settleCurrency;
    }

    public void setSettleCurrency(String settleCurrency) {
        this.settleCurrency = settleCurrency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(String queryResult) {
        this.queryResult = queryResult;
    }

    @Override
    public Map<String, String> getNVPRequest() {
//        awsUtil.getMethods(this.getClass());
        Map<String, String> nvpRequest = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object obj = field.get(this);
                if (obj != null && obj instanceof String && !"".equals(obj))
                    nvpRequest.put(field.getName(), (String) obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
//            try {
//                Method method=this.getClass().getDeclaredMethod("set"+ StringUtils.capitalize(field.getName()),field.getType());
//                method.invoke(this, field.get(this));
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
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

    }

}
