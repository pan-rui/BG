package com.qpp.action.order;

import com.alibaba.fastjson.JSON;
import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.core.nvp.NVPEncoder;
import com.qpp.action.BaseAction;
import com.qpp.dao.OrderDao;
import com.qpp.model.TOrder;
import com.qpp.model.TOrderItem;
import com.qpp.service.market.MessageInfo;
import com.qpp.service.market.PaypalUtil;
import com.qpp.service.market.impl.OrderItemServiceImpl;
import com.qpp.service.market.impl.OrderServiceImpl;
import com.qpp.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qpp on 7/14/2014.
 */

@Controller
public class PaypalAction extends BaseAction {

    @Resource
    private PaypalUtil paypalUtil;
    @Resource
    private OrderServiceImpl orderServiceImpl;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserService userServiceImpl;
    @Resource
    private OrderItemServiceImpl OrderItemServiceImpl;

    @RequestMapping(value = "/order/paypal_set.hyml")
    public String toPaypal(TOrder order,ModelMap map,HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); //+request.getContextPath();
        NVPEncoder encoder = new NVPEncoder();
        String invnum = PaypalUtil.getUuid(16);
        String returnUrl = baseUrl + "/order/payRetur.hyml";
        encoder.add("RETURNURL", returnUrl);
        encoder.add("CANCELURL", baseUrl + "/jsp/order.jsp");
        encoder.add("CURRENCYCODE", order.getCurrencyCode()); //货币类型
        encoder.add("PAYMENTACTION", order.getPayMentAction() == null || "".equals(order.getPayMentAction()) ? "Sale" : order.getPayMentAction());
        encoder.add("EMAIL", order.getEmail()); //买家Email
        encoder.add("DESC", order.getDesc());   //订单描述
        encoder.add("INVNUM", invnum);
        encoder.add("LOCALECODE", order.getLocalCode()); //国家地区代码
        encoder.add("SHIPTOCOUNTRYCODE", order.getLocalCode()); //国家地区代码
//        encoder.add("SHIPTOSTREET",order.getAddress()); //街道地址
//        encoder.add("SHIPTOCITY",request.getParameter("city")); //城市
//        encoder.add("SHIPTOSTATE",request.getParameter("province")); //州或省
        encoder.add("SHIPTOSTREET", "111 Bliss Ave"); //街道地址
        encoder.add("SHIPTOCITY", "San Jose"); //城市
        encoder.add("SHIPTOSTATE", "CA"); //州或省

        encoder.add("HDRIMG", MessageInfo.getMessage("Company_Img_Url")); //付款页面显示图片URL
        encoder.add("SHIPTOZIP", order.getZipCode());
        encoder.add("PHONENUM", order.getPhone());
//        encoder.add("REQCONFIRMSHIPPING","1");
        encoder.add("ADDROVERRIDE", "1");
        encoder.add("ITEMAMT", String.valueOf(order.getItemAmt()));
//        encoder.add("SHIPTONAME", String.valueOf(order.getBuyer().getId()));//TODO:待确定...
        encoder.add("SHIPTONAME", String.valueOf(order.getBuyer()));//TODO:待确定...
        double taxAmt = order.getItemAmt() * Double.parseDouble(MessageInfo.getMessage(order.getLocalCode() + "_Tax"));
        encoder.add("TAXAMT", String.valueOf(taxAmt));
        order.setTaxAmt(taxAmt);
        order.setAmt(order.getItemAmt() + order.getShippingAmt() + taxAmt);
        encoder.add("AMT", String.valueOf(order.getAmt())); //总额
        encoder.add("MAXAMT", String.valueOf(Math.round(order.getAmt() + 50d)));
//        Cookie[] cookies=request.getCookies();
        List<TOrderItem> list = request.getParameter("cart") != null ? JSON.parseArray(request.getParameter("cart"), TOrderItem.class) : new ArrayList<TOrderItem>();
        String orderId = PaypalUtil.getUuid(16);
        Date date = new Date();
        //        order.setTOrderItems(list);
//        order.setId(orderId);
        order.setCtime(date);
        order.setUtime(date);
        order.setTaxAmt(taxAmt);
        order.setInvnum(invnum);
        order.setMaxAmt(order.getAmt() + 50);
        order.setStatus(OrderServiceImpl.OrderStatus.no_Pay.getValue());
        orderServiceImpl.save(order);
        long oid = Long.parseLong(userServiceImpl.exists(orderDao, "select oid from t_order where invnum='" + invnum + "'"));
        for (int i = 0; i < list.size(); i++) {
            TOrderItem TOrderItem = list.get(i);
            encoder.add("L_NAME" + i, TOrderItem.getProductName());
            encoder.add("L_NUMBER" + i, TOrderItem.getIsbn());
            encoder.add("L_DESC" + i, TOrderItem.getComment());
            encoder.add("L_AMT" + i, String.valueOf(TOrderItem.getPrice()));
            encoder.add("QTY" + i, String.valueOf(TOrderItem.getProductCount()));
//            TOrderItem.setId(PaypalUtil.getUuid(16));
            TOrderItem.setOrderId(oid);
            TOrderItem.setCtime(date);
            TOrderItem.setUtime(date);
            OrderItemServiceImpl.save(TOrderItem);
        }
        encoder.add("SHIPPINGAMT", String.valueOf(order.getShippingAmt()));
        encoder.add("L_SHIPPINGOPTIONISDEFAULT0", "true");
        encoder.add("L_SHIPPINGOPTIONNAME0", order.getShippingType() != null ? order.getShippingType() : "Ups");
        encoder.add("L_SHIPPINGOPTIONAMOUNT0", String.valueOf(order.getShippingAmt()));

        encoder.add("CALLBACK", "https://www.ppcallback.com/callback.pl");
        encoder.add("CALLBACKTIMEOUT", "4");


        Map<String, Object> sMap = paypalUtil.setExpress(encoder, PaypalUtil.LogType.SET_EXPRESS_CHECKOUT.getValue());
        Map<String, Object> verifyy = new HashMap<String, Object>();
        verifyy.put("SHIPTOSTREET", order.getAddress());
        verifyy.put("PHONENUM", order.getPhone());
        verifyy.put("INVNUM", order.getInvnum());
//        verifyy.put("EMAIL", order.getEmail());
        PaypalUtil.verifys.put(String.valueOf(sMap.get("TOKEN")), verifyy);
        if (!(Boolean) sMap.get("flag")) {
//            return request.getRequestURI().contains("?")?request.getRequestURI()+"&error="+sMap.get("errorInfo"):request.getRequestURI()+"?errorInfo="+sMap.get("errorInfo");
            map.addAttribute("error", sMap.get("errorInfo"));
            return "error";
        } else
            return "redirect:" + String.valueOf(sMap.get("url"));
    }

    @RequestMapping(value="/order/payRetur.hyml")
    public String doPaypal(ModelMap map, HttpServletRequest request) {
        NVPEncoder encoder=new NVPEncoder();
        Map<String, Object> verify = new HashMap<String, Object>();
        encoder.add("TOKEN",request.getParameter("token"));
       Map<String,Object> result=paypalUtil.doExpress(encoder, PaypalUtil.LogType.DO_EXPRESS_CHECKOUT.getValue());
        map.addAllAttributes(((NVPDecoder)result.get("decoder")).getMap());
        return "payNotifyUrl";
    }

    @RequestMapping(value ="/order/Ttransaction.hyml")
    public String transaction(String startDate,String endDate,ModelMap model){
//        System.out.println("进入.....");
            Map<String,String> resMap=new HashMap<String, String>();
        Map<String,Object> result=null;
//        reMap.put("startDate", startDate);
//        reMap.put("endDate", endDate);
        try {
           result= paypalUtil.TransactionSearch(resMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
            model.addAttribute("data",result);
        return "transactionDetail";
    }

}
