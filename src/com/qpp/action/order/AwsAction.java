package com.qpp.action.order;

import com.amazonaws.ipnreturnurlvalidation.SignatureUtilsForOutbound;
import com.qpp.action.BaseAction;
import com.qpp.model.TOrder;
import com.qpp.service.market.AwsUtil;
import com.qpp.service.market.MessageInfo;
import com.qpp.service.market.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**Amazon支付.
 * Created by qpp on 7/24/2014.
 */
@Controller
public class AwsAction extends BaseAction {
@Resource
    private AwsUtil awsUtil;
    @Resource
    private OrderService OrderService;
    private Map<String, Map<String, String>> verifyMap = new HashMap<String, Map<String, String>>();
    private String recipientEmail= MessageInfo.getMessage("recipientEmail");

    @RequestMapping(value = "/TOrder/aws_pay.hyml")
    @ResponseBody
    public void awsPay(HttpServletRequest request) {
        String baseUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort(); //+request.getContextPath();
        String param="";
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("CallerDescription",(param=request.getParameter("CallerDescription"))==null?"":param);
        reqMap.put("CallerReference", request.getParameter("CallerReference"));   //require
//        reqMap.put("ChargeFeeTo", (param=request.getParameter("ChargeFeeTo"))==null?"":param);
//        reqMap.put("DescriptorPolicy", (param=request.getParameter("DescriptorPolicy"))==null?"":param);
//        reqMap.put("MarketplaceFixedFee", (param=request.getParameter("MarketplaceFixedFee"))==null?"":param);
//        reqMap.put("MarketplaceVariableFee", (param=request.getParameter("MarketplaceVariableFee"))==null?"":param);
        reqMap.put("OverrideIPNURL",baseUrl+"/TOrder/aws_verify.hyml");  //suggest  返回 url可供验证交易....
//        reqMap.put("RecipientTokenId", (param=request.getParameter("RecipientTokenId"))==null?"":param);  //suggest
        reqMap.put("SenderDescription", (param=request.getParameter("SenderDescription"))==null?"":param);  //suggest
        reqMap.put("SenderTokenId", (param=request.getParameter("SenderTokenId"))); //require
        reqMap.put("TransactionAmount", (param=request.getParameter("TransactionAmount"))); //require
        reqMap.put("CurrencyCode", request.getParameter("CurrencyCode"));
        reqMap.put("CurrencyCode", (param=request.getParameter("CurrencyCode"))==null?"USD":param);
//        reqMap.put("TransactionTimeoutLnMins", (param=request.getParameter("TransactionTimeoutLnMins"))==null?"":param);
        Map<String,Object> resultMap=awsUtil.payment(reqMap);
        if((Boolean)resultMap.get("flag")){
            //保存记录到数据库.............
//            map.addAttribute("TransactionId",reqMap.get("TransactionId"));
            verifyMap.put(reqMap.get("TransactionId"), reqMap);
        }else
            System.out.println("发生异常");
    }

    /**
     * 支付验证
     * @return
     */
@RequestMapping(value = "/TOrder/aws_verify.hyml")
    public String awsVerify(HttpServletRequest request){
    String urlEndPoint = "http://www.mysite.com/call_pay.jsp"; //Your return url end point.
    //return url is sent as a http GET request and hence we specify GET as the http method.
    //Signature verification does not require your secret key
    Map<String,String> verify=verifyMap.get(request.getParameter("TransactionId"));
    Map<String, String> params = new HashMap<String, String>();
    if(verify.get("TransactionId").equals(request.getParameter("TransactioniId"))&&verify.get("TransactionAmount").equals(request.getParameter("transactionAmount"))&&recipientEmail.equals(request.getParameter("recipientEmail"))){
//    params.put("expiry", "10/2013");
    params.put("tokenID", request.getParameter("tokenId"));
//    params.put("status", "SC");
    params.put("callerReference", request.getParameter("callerReference"));
    params.put("signatureMethod", request.getParameter("signatureMethod"));
    params.put("signatureVersion", request.getParameter("signatureVersion"));
    params.put("certificateUrl", request.getParameter("certificateUrl"));
    params.put("signature", request.getParameter("signature"));
    try {
        boolean isPass=new SignatureUtilsForOutbound().validateRequest(params, urlEndPoint, "GET");  //验证是否通过.. 通过后更新订单状态......
        if(isPass) //更新订单
            System.out.println("OK");
    } catch (SignatureException e) {
        e.printStackTrace();
    }
        return "TOrder/......";//  //成功页面
    }else {
        return "TOrder/......."; //错误页面
    }

}

    @RequestMapping(value = "/TOrder/TOrder_Detail.hyml")
    public String TOrderDetail(RedirectAttributesModelMap rmap,HttpServletRequest request,ModelMap map){
        String transactionId= (String) map.get("TransactionId");
        //读数据
       TOrder TOrder= OrderService.getBySql("sql");
//        List<TOrderItem> list=TOrder.get
        map.addAttribute("TOrder", TOrder);
//        map.addAttribute("TOrderItems",list);
        return "TOrder/detail";
    }
    @ModelAttribute("count")
    public int getInt(){
        return 0;
    }
}
