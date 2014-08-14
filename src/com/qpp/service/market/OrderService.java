package com.qpp.service.market;

import com.qpp.model.TOrder;
import com.qpp.service.market.impl.OrderServiceImpl;

import java.util.Map;

/**
 * Created by qpp on 7/14/2014.
 */

public interface OrderService {
    boolean save(TOrder TOrder);
    TOrder getById(String id);
    boolean update(TOrder TOrder);

    boolean update(String tableName, Map data, String id);
    TOrder getBySql(String sql);
//    boolean updateOnMap(String tableName,Map<String,Object> newData,String cond);
//消费
public Map<String,String> paypal(PaymentRequest paymentRequest,OrderServiceImpl.PayType type,TOrder order);

    //查询
    public Map<String,String> findTransaction(PaymentRequest findRequest,OrderServiceImpl.PayType type,TOrder order);

    //消费撤消
    public Map<String,String> cancelPay(PaymentRequest cancelRequest,OrderServiceImpl.PayType type);

    //预授权
    public Map<String,String> preAuth(PaymentRequest preAuthRequest,OrderServiceImpl.PayType type,TOrder order);

    //预授权完成
    public Map<String,String> authComplate(PaymentRequest authRequest,OrderServiceImpl.PayType type);

    //预授权撤消
    public Map<String,String> cancelAuth(PaymentRequest cancelAuth,OrderServiceImpl.PayType type);

    //退款
    public Map<String,String> refund(PaymentRequest refundRequest,OrderServiceImpl.PayType type,TOrder order);
}
