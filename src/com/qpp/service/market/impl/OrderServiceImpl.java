package com.qpp.service.market.impl;

import com.qpp.dao.OrderDao;
import com.qpp.model.TOrder;
import com.qpp.service.market.OrderService;
import com.qpp.service.market.PaymentRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by qpp on 7/14/2014.
 */
@Service
public class OrderServiceImpl implements OrderService {
    public enum PayType{
        Union,
        Aws,
        Paypal,
        Alipay
    }
    @Resource
    private OrderDao OrderDao;

    public enum OrderStatus {
        no_Pay("no_Pay"),
        pay("pay"),
        send("send"),
        confirm("confirm");

        private String value;

        OrderStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return new String(this.value.getBytes(Charset.defaultCharset()), Charset.forName("UTF-8"));
        }
    }
    @Override
    public boolean save(TOrder TOrder) {
        try {
            OrderDao.save(TOrder);
        }catch (Exception expection){
            return OrderDao.insert(TOrder);
        }
        return true;
    }
    public boolean insert(TOrder TOrder){
        return OrderDao.insert(TOrder);
    }

    @Override
    public TOrder getById(String id) {
        return OrderDao.getById(id);
    }

    @Override
    public boolean update(TOrder TOrder) {
        OrderDao.update(TOrder);
        return true;
    }

    @Override
    public TOrder getBySql(String sql) {
        return OrderDao.getBySQL(sql);
    }

    @Override
    public Map<String, String> paypal(PaymentRequest paymentRequest,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> findTransaction(PaymentRequest findRequest,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> cancelPay(PaymentRequest cancelRequest,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> preAuth(PaymentRequest preAuthRequest,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> authComplate(PaymentRequest authRequest,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> cancelAuth(PaymentRequest cancelAuth,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> refund(PaymentRequest refundRequest,PayType type) {
        return null;
    }

    public void update(String tableName, Map newData, String cond) {
         OrderDao.update(tableName,newData,cond);
    }

}
