package com.qpp.action.order;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.TCartItem;
import com.qpp.model.TOrder;
import com.qpp.model.TOrderItem;
import com.qpp.service.market.OrderItemService;
import com.qpp.service.market.OrderService;
import com.qpp.service.market.impl.*;
import com.qpp.util.RandomSymbol;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;

/**
 * 订单处理
 * Created by qpp on 8/7/2014.
 */
@Controller
public class OrderAction extends BaseAction {
@Resource
private OrderService orderServiceImpl;
    @Resource
    private OrderItemService orderItemServiceImpl;

    @RequestMapping("/order/testt")
//    @ResponseBody
    public String testt(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        System.out.println("in httpClient.");
        ServletInputStream sis = request.getInputStream();
        StringBuffer sb = new StringBuffer();
        byte[] bytes = new byte[4096];
        int len=-1;
        while ((len = sis.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, "utf-8"));
        }
        System.out.println(URLDecoder.decode(sb.toString(), "UTF-8"));
        Map<String, String[]> reqmap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = (Set<Map.Entry<String, String[]>>) reqmap.entrySet();

        for (Iterator<Map.Entry<String, String[]>> it = entry.iterator(); it.hasNext(); ) {
            Map.Entry<String, String[]> en = it.next();
            System.out.println(en.getKey() + "\t" + en.getValue()[0]);
        }
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write("response form bgm..d ..");
            pw.print("response from  bgm..................");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "testt";
    }

    /**
     * 订单提交处理..
     * @param order
     * @param request
     * @return
     */
    @RequestMapping("/order/commit")
    @ResponseBody
    public BaseReturn orderCommit(TOrder order,HttpServletRequest request) {
        BaseReturn result = new BaseReturn();
        Date date = new Date();
        order.setTUser((com.qpp.model.TUser) request.getSession().getAttribute("user"));
        order.setCtime(date);
        String orderId = RandomSymbol.getAllSymbol(16);
        order.setId(orderId);
        order.setStatus("下单待付款");
//        order.setAmt();前台已计算
        String invnum = RandomSymbol.getAllSymbol(18);//流水号/跟踪号
        order.setInvnum(invnum);
        String transId = RandomSymbol.getAllSymbol(16);
        order.setTransactionId(transId);
//        order.setType();  支付方式
        List<TCartItem> cartList = (List<TCartItem>) request.getSession().getAttribute("cartList");
        Set<TOrderItem> orderItems = new HashSet<TOrderItem>();
        try {
            for (TCartItem cartItem : cartList) {
                TOrderItem orderItem = new TOrderItem();
                BeanUtils.copyProperties(cartItem, orderItem, new String[]{"id", "userId", "ctime", "url", "imgUrl"});
                orderItem.setTOrder(order);
                orderItem.setId(RandomSymbol.getAllSymbol(16));
                orderItem.setCtime(date);
                orderItem.setUtime(date);
                orderItemServiceImpl.save(orderItem);
                orderItems.add(orderItem);
            }
            order.setTOrderItems(orderItems);
            orderServiceImpl.save(order);
            memcachedClient.set("order",order,24*60*60*1000);
            result.setResult(1);
            result.setData(order);
        }catch (Exception e){
            result.setResult(0);
            result.setErrMessage("数据保存出错,订单提交失败..");
        }
        return result;
    }

    /**
     * 选择订单支付类型支付
     * @param type
     * @return
     */
    @RequestMapping("/order/type")
    @ResponseBody
    public void typeToPay(String type) {
        BaseReturn result = new BaseReturn();
            OrderServiceImpl.PayType payType=null;
        try {
            payType = OrderServiceImpl.PayType.valueOf(type);
        } catch (Exception e) {
            result.setResult(0);
            result.setErrMessage("支付类型不正确....");
        }
        TOrder order = (TOrder) memcachedClient.get("order");
        switch (payType){
            case Union:
                UnionpayRequest req = new UnionpayRequest();
                req.setBackEndUrl("返回URL");
                req.setFrontEndUrl("http://localhost:8080/order/notify.hyml");
                req.setOrderTime(UnionPayment.formatDate(order.getCtime()));
                req.setOrderNumber(order.getId());
                req.setOrderAmount(String.valueOf(order.getAmt()));
                req.setOrderCurrency(order.getCurrencyCode());
                req.setOrigQid(order.getInvnum());
                req.setCustomerIp("持卡人IP");
//                result.setResult(1);
//                result.setData(UnionPayment.unionPayConfig.getEndPoint());
                orderServiceImpl.preAuth(req, OrderServiceImpl.PayType.Union);
                break;
            case Paypal:
                result.setResult(1);
                result.setData(PaypalPayment.paypalConfig.getEndPoint());
                break;
            case Aws:
                result.setResult(1);
                result.setData(AwsPayment.awsConfig.getEndPoint());
                break;
            default:
              //..........
        }
    }

    @RequestMapping("/order/notify")
    public BaseReturn pay(String type, HttpServletRequest request) {

    }

}
