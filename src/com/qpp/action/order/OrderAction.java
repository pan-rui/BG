package com.qpp.action.order;

import com.paypal.sdk.core.nvp.NVPEncoder;
import com.qpp.action.BaseAction;
import com.qpp.dao.OrderDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TCartItem;
import com.qpp.model.TOrder;
import com.qpp.model.TOrderItem;
import com.qpp.service.market.OrderItemService;
import com.qpp.service.market.OrderService;
import com.qpp.service.market.impl.AwsRequest;
import com.qpp.service.market.impl.OrderServiceImpl;
import com.qpp.service.market.impl.PaypalRequest;
import com.qpp.service.market.impl.UnionpayRequest;
import com.qpp.service.user.UserService;
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
import java.text.MessageFormat;
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
    private OrderDao orderDao;
    @Resource
    private OrderItemService orderItemServiceImpl;
    @Resource
    private UserService userServiceImpl;

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
//        order.setTUser((com.qpp.model.TUser) request.getSession().getAttribute("user"));
        order.setCtime(date);
//        String orderId = RandomSymbol.getAllSymbol(16);
//        order.setId(orderId);
        order.setStatus("下单待付款");
//        order.setAmt();前台已计算
        String invnum = RandomSymbol.getAllSymbol(18);//流水号/跟踪号
        order.setInvnum(invnum);
        String transId = RandomSymbol.getAllSymbol(16);
        order.setTransactionId(transId);
//        order.setType();  支付方式
        List<TCartItem> cartList = (List<TCartItem>) request.getSession().getAttribute("cartList");
//        Set<TOrderItem> orderItems = new HashSet<TOrderItem>();
            orderServiceImpl.save(order);
        long oid = Long.parseLong(userServiceImpl.exists(orderDao, "select oid from t_order where invnum='" + invnum + "'"));
        try {
            for (TCartItem cartItem : cartList) {
                TOrderItem orderItem = new TOrderItem();
                BeanUtils.copyProperties(cartItem, orderItem, new String[]{"id", "userId", "ctime", "url", "imgUrl"});
                orderItem.setOrderId(oid);
//                orderItem.setId(RandomSymbol.getAllSymbol(16));
                orderItem.setCtime(date);
                orderItem.setUtime(date);
                orderItemServiceImpl.save(orderItem);
//                orderItems.add(orderItem);
            }
//            order.setTOrderItems(orderItems);
            request.setAttribute("invnum", invnum);
            memcachedClient.set("order_" + invnum, order, 18 * 60 * 60 * 1000);
            memcachedClient.set("order_" + oid, order, 18 * 60 * 60 * 1000);
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
    public void typeToPay(String type, HttpServletRequest request, HttpServletResponse response) {
        BaseReturn result = new BaseReturn();
        Map<String, String> resultMap = new HashMap<String, String>();
            OrderServiceImpl.PayType payType=null;
        try {
            payType = OrderServiceImpl.PayType.valueOf(type);
        } catch (Exception e) {
            result.setResult(0);
            result.setErrMessage("支付类型不正确....");
        }
        TOrder order = (TOrder) memcachedClient.get("order_" + request.getSession().getAttribute("invnum"));
        switch (payType){
            case Union:
                UnionpayRequest uReq = new UnionpayRequest();
                uReq.setBackEndUrl("返回URL");
                uReq.setFrontEndUrl("http://localhost:8080/order/union_notify.hyml");
//                req.setOrderTime(UnionPayment.formatDate(order.getCtime()));
//                req.setOrderNumber(order.getId());
//                req.setOrderAmount(String.valueOf(order.getAmt()));
//                req.setOrderCurrency(order.getCurrencyCode());
                uReq.setOrderParam(order);
//                req.setOrigQid(order.getInvnum());
                uReq.setCustomerIp("持卡人IP");
//                result.setResult(1);
//                result.setData(UnionPayment.unionPayConfig.getEndPoint());
                resultMap = orderServiceImpl.preAuth(uReq, OrderServiceImpl.PayType.Union, order); //保存返回的 跟踪号/流水号
                //TODO: 可直接根据返回结果处理(修改订单状态...流水号........

                break;
            case Paypal:
                PaypalRequest pReq = new PaypalRequest();
                String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); //+request.getContextPath();
                NVPEncoder encoder = new NVPEncoder();
//                String invnum = RandomSymbol.getAllSymbol(18);

                String returnUrl = baseUrl + "/order/payReturn.hyml";
                pReq.setReturnUrl(returnUrl);
                pReq.setCancelUrl(request.getHeader("REFERER"));

                pReq.setOrderParam(order);

                resultMap = orderServiceImpl.preAuth(pReq, OrderServiceImpl.PayType.Paypal, order);
                String strAck = resultMap.get("ACK");
                try {
                    if (strAck != null && !(strAck.equals("Success") || strAck.equals("SuccessWithWarning"))) {
                        request.getSession().setAttribute("errorMessage", "参数有误,支付失败");
                        response.sendRedirect("APIError.jsp");
                    } else
                        response.sendRedirect("https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + resultMap.get("TOKEN"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Aws:
                AwsRequest aReq = new AwsRequest();
                aReq.setOrderParam(order);
                resultMap = orderServiceImpl.preAuth(aReq, OrderServiceImpl.PayType.Aws, order);
                break;
            default:
              //..........
        }

    }

    /**
     * 银联支付结果通知
     * @param type
     * @param request
     */
    @RequestMapping("/order/union_notify")
    @ResponseBody
    public void pay(String type,HttpServletRequest request) {
        Map<String, String[]> respMap = request.getParameterMap();
        for (String param1 : respMap.keySet())
            System.out.println(param1);
        //更改订单状态,赠送积分或优惠卷
        String qid = respMap.get("qid")[0];
        String respCode = respMap.get("respCode")[0];
        if (respCode.equals("00")) {
            //TODO:更新
            TOrder order = orderServiceImpl.getById(String.valueOf(memcachedClient.get("order_" + respMap.get("orderNumber")[0])));
//            order.setStatus("订单已支付");
            orderServiceImpl.updtaOrder(order, qid);
            loger.info(MessageFormat.format("Event:Order is Payment,invnum:{0},time:{1},result:Success", qid, formatDateTime(new Date())));
        }else
            loger.info(MessageFormat.format("Event:Order is Payment,invnum:{0},time:{1},result:Error", qid, formatDateTime(new Date())));
    }
    //Paypal 支付核实
    @RequestMapping("/order/payReturn.hyml")
    @ResponseBody
    public BaseReturn payReturn(HttpServletRequest request) {
        BaseReturn result = new BaseReturn();
        PaypalRequest paypalRequest = new PaypalRequest();
        paypalRequest.setToken(request.getParameter("token"));
        TOrder order = (TOrder) memcachedClient.get("order_" + request.getParameter("invnum"));
        paypalRequest.setOrderParam(order);
        Map<String, String> resultMap = orderServiceImpl.preAuth(paypalRequest, OrderServiceImpl.PayType.Paypal, order);
        if (resultMap != null && ("Success".equals(resultMap.get("ACK")) || "SuccessWithWarning".equals(resultMap.get("ACK")))) {
            //TODO: 支付成功,修改订单状态,赠送积分或其它促销方案......

            result.setResult(1);
            result.setData(resultMap);
        }
//    paypalRequest

        return result;
    }
}
