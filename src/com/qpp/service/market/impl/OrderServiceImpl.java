package com.qpp.service.market.impl;

import com.qpp.dao.OrderDao;
import com.qpp.dao.UserDao;
import com.qpp.model.TOrder;
import com.qpp.model.TUser;
import com.qpp.service.market.MessageInfo;
import com.qpp.service.market.OrderService;
import com.qpp.service.market.Payment;
import com.qpp.service.market.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by qpp on 7/14/2014.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    public enum PayType{
        Union,
        Aws,
        Paypal,
        Alipay
    }
    @Resource
    private OrderDao OrderDao;
    @Resource
    private Payment unionPayment;
    @Resource
    private Payment paypalPayment;
    @Resource
    private Payment awsPayment;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        }catch (Exception e){
            return false;
        }
        return true;
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
    public Map<String, String> paypal(PaymentRequest paymentRequest,PayType type,TOrder order) {
        return null;
    }

    @Override
    public Map<String, String> findTransaction(PaymentRequest findRequest,PayType type,TOrder order) {
        return null;
    }

    @Override
    public Map<String, String> cancelPay(PaymentRequest cancelRequest,PayType type) {
        return null;
    }

    @Override
    public Map<String, String> preAuth(PaymentRequest preAuthRequest,PayType type,TOrder order) {
        Map<String, String> resultMap =null;
        switch (type) {
            case Union:
                resultMap=unionPayment.preAuth(preAuthRequest);
                break;
            case Paypal:
                resultMap = paypalPayment.preAuth(preAuthRequest);
//                String strAck=resultMap.get("ACK");
//                if(strAck !=null && !(strAck.equals("Success") || strAck.equals("SuccessWithWarning"))){
//
//                }

                break;
            case Aws:
                resultMap = awsPayment.preAuth(preAuthRequest);
                break;
        }
        return resultMap;
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
    public Map<String, String> refund(PaymentRequest refundRequest,PayType type,TOrder order) {
        return null;
    }

    @Override
    public void updtaOrder(TOrder order,String qid) {
        TUser user = userDao.getById(order.getBuyer());
//            user.setScore(Integer.parseInt(String.valueOf(Math.round(order.getAmt()))) * Integer.parseInt(MessageInfo.getMessage("交易金额兑换积分系数")));
        Map<String, Object> orderData = new LinkedHashMap<String, Object>();
        Map<String, Object> userData = new LinkedHashMap<String, Object>();
        orderData.put("status", "订单已支付");
        orderData.put("invnum", qid);
        orderData.put("id", order.getId());
        orderDao.update("t_order", orderData);
        userData.put("score", Integer.parseInt(String.valueOf(Math.round(order.getAmt()))) * Integer.parseInt(MessageInfo.getMessage("key"))); //TODO:key为交易金额兑换积分系数
        userData.put("id",order.getBuyer());
        userDao.update("t_user", userData);

    }



//    public Map<String,String> getExpress(PaypalRequest request) {
////        Map<String,String> reqMap=request.getNVPRequest();
//        paypalPayment.preAuth(request);
//    }
}
