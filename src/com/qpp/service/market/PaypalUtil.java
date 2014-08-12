package com.qpp.service.market;

import com.alibaba.fastjson.JSON;
import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.core.nvp.NVPEncoder;
import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.NVPCallerServices;
import com.qpp.service.market.impl.OrderItemServiceImpl;
import com.qpp.service.market.impl.OrderServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by qpp on 7/11/2014.
 * Paypal支付、退款、查询等交易处理类
 */
@Component
public class PaypalUtil {
//    // 扫描状态------------------------------------------------------------------
//    public static String  PAYPAL_SCAN_STATUS="RESET";
//
//    public static String  PAYPAL_OK_SCAN_STATUS="RESET";
//
//    public static String  WITHDRAW_SCAN_STATUS="RESET";
//
//    public final static String SCANNING="SCANNING";
    //搜索类型
    private static final String BY_SCAN="by_scan";
    private static final String BY_SCAN_OK="by_scan_ok";
    private static final String BY_WEB="by_web";
    private static final NVPCallerServices caller=new NVPCallerServices();
    public static Map<String, Object> verifys = new HashMap<String, Object>();
    static{
        try {
            caller.setAPIProfile(getAPIProfile());
        } catch (PayPalException e) {
            e.printStackTrace();
            System.out.println("支付API配置文件出错.");
            log("Paypal API Profile is error.","error");
        }
    }
    @Resource
    private OrderItemServiceImpl orderItemServiceImpl;
    @Resource
    private OrderServiceImpl orderServiceImpl;

    //日志类型
    public enum LogType {
        WEB_PAYPALDEPOSIT("Log_Name__Web_PaypalDeposit"),
        ADMINSCAN_PAYPAL("Log_Name__AdminScan_Palpay"),
        ADMINSCAN_PAYPAL_OK("Log_Name__AdminScan_Palpay_OK"),
        SET_EXPRESS_CHECKOUT("Paypal_setExpressCheckout"),
        DO_EXPRESS_CHECKOUT("Paypal_doExpressCheckout"),
        GET_EXPRESS_CHECKOUT("Paypal_getExpressCheckout"),
        TRANSACTIONSEARCH("Paypal_TransactionSearch");
        private String value;

        LogType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
    /**
     * 比较 上一条记录中的日期时间 和 当前记录中的日期时间
     * @param beforeStr 上一条记录中的日期时间
     * @param currentStr 当前记录中的日期时间
     * @param className 记录的日志类型
     * @return(beforeStr>currentStr 1;beforeStr==currentStr 0; beforeStr<currentStr -1;异常-7)
     */
    protected static int compareTo(String beforeStr,String currentStr,String  className){
        log("beforeStr:"+beforeStr+">currentStr:"+currentStr,className);
        int result=-7;
        try {
            SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //dd/MM/yyyy hh:mm:ss
            beforeStr=beforeStr.substring(0,19);
            currentStr=currentStr.substring(0,19);
            if(beforeStr.indexOf("T")>=0 && currentStr.indexOf("T")>=0){
                beforeStr=beforeStr.replace("T", " ");
                currentStr=currentStr.replace("T", " ");
            }else if(beforeStr.indexOf("t")>=0 && currentStr.indexOf("t")>=0){
                beforeStr=beforeStr.replace("t", " ");
                currentStr=currentStr.replace("t", " ");
            }
            Date beforeDate=sFormat.parse(beforeStr);
            Date currentDate=sFormat.parse(currentStr);

            // beforeDate>currentDate ：1  ，beforeDate<currentDate 小：-1   ； beforeDate==currentDate :0
            result=beforeDate.compareTo(currentDate);
            log("beforeDate:"+beforeDate+">>currentDate:"+currentDate+">>result:"+result,className);
        } catch (Exception e) {
            //System.out.println("e:"+e.getMessage());
            result=-7;
            log("e:"+e.getMessage()+">>result:"+result,className);
        }
        return result;

    }

    public static APIProfile getAPIProfile() throws PayPalException {
        APIProfile profile = null;
        try {
            profile = ProfileFactory.createSignatureAPIProfile();
            profile.setAPIUsername(MessageInfo.getMessage("ApiUsername"));
            profile.setAPIPassword(MessageInfo.getMessage("ApiPassword"));
            profile.setSignature(MessageInfo.getMessage("ApiSignature"));
            profile.setEnvironment(MessageInfo.getMessage("ApiEnvironment"));
//            profile.setSubject(MessageInfo.getMessage("ApiSubject"));
            profile.setTimeout(Integer.parseInt(MessageInfo.getMessage("ApiTimeout")));
        } catch (Exception e) {
        }
        return profile;
    }

    //日期格式化为常用格式
    public static String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }


    //日志记录
    protected static void log(String message,String  className) {
        if(className==null || "".equals(className))
            className=LogType.WEB_PAYPALDEPOSIT.getValue();

        StringBuffer newMesssgae=new StringBuffer(">PaypalUtil:");
        newMesssgae.append(message);
        FileWriter fw;
        try {
            fw = new FileWriter(MessageInfo.getMessage("paypal.logPath"),true);
            fw.append("\r\n"+newMesssgae.toString()+className);
            fw.close();
        } catch (IOException e) {
            System.out.println("Log write is Fail..");
        }
    }

    /**
     *  废弃交易
     * @param invnum 授权ID
     * @param note 备注
     * @return
     */
    public Map<String, String> VoidTrans(String invnum,String note){
        String baseStr="Refund send:invnum="+invnum;
        log(baseStr,"refund");
        Map<String,String> map=new HashMap<String, String>();
        NVPCallerServices caller = null;
        NVPEncoder encoder = new NVPEncoder();
        NVPDecoder decoder = new NVPDecoder();
        try {
            caller = new NVPCallerServices();
//            caller.setAPIProfile(getAPIProfile());
            encoder.add("METHOD", "DoVoid");
            encoder.add("AUTHORIZATIONID",invnum);
            encoder.add("NOTE",note);
            String NVPRequest = encoder.encode();
            String NVPResponse = caller.call(NVPRequest);
            decoder.decode(NVPResponse);
            String strAck = decoder.get("ACK") ==null?"": decoder.get("ACK");
            map.put("strAck", strAck);
            if (!strAck.equalsIgnoreCase("Success") && !strAck.equalsIgnoreCase("SuccessWithWarning")) {
                String errcode=(decoder.get("L_ERRORCODE0")==null?"":decoder.get("L_ERRORCODE0"));
                String errmsg=(decoder.get("L_SHORTMESSAGE0")==null?"":decoder.get("L_SHORTMESSAGE0"));
                String errdetail=(decoder.get("L_LONGMESSAGE0")==null?"":decoder.get("L_LONGMESSAGE0"));
                log(baseStr+">>Return>>>>>>errcode="+errcode+">>errmsg="+errmsg+">>errdetail="+errdetail,"refund");
                map.put("errcode",errcode);
                map.put("errmsg",errmsg);
                map.put("errdetail",errdetail);
                map.put("status","1");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","9");
            return map;
        }
        String retStr=">>>ID="+decoder.get("REFUNDTRANSACTIONID")+">>Fee="+decoder.get("FEEREFUNDAMT")+">>Amt="+decoder.get("GROSSREFUNDAMT");
        retStr+=">>NetAmt="+decoder.get("NETREFUNDAMT")+">>Total="+decoder.get("TOTALREFUNDEDAMT");
        log(baseStr+">>Return>>>>>>"+retStr,"refund");
        map.put("status","0");
        map.put("rrnumber",decoder.get("REFUNDTRANSACTIONID"));
        map.put("Fee",decoder.get("FEEREFUNDAMT"));
        map.put("NetAmt",decoder.get("NETREFUNDAMT"));
        return map;
    }

    /**
     *  退款
     * @param invnum
     * @param amt
     * @param note
     * @return
     */
    public Map<String, String> refund(String invnum,double amt,String note){
        String baseStr="Refund send:invnum="+invnum+">>Amount="+amt+">>Note="+note;
        log(baseStr,"refund");
        Map<String,String> map=new HashMap<String, String>();
        NVPCallerServices caller = null;
        NVPEncoder encoder = new NVPEncoder();
        NVPDecoder decoder = new NVPDecoder();
        try {
            caller = new NVPCallerServices();
//            caller.setAPIProfile(getAPIProfile());
            encoder.add("METHOD", "RefundTransaction");
            encoder.add("TRXTYPE", "Q");
            encoder.add("TRANSACTIONID", invnum);
            encoder.add("NOTE",note);
            if (amt==0){
                encoder.add("REFUNDTYPE","Full");
            }else{
                encoder.add("REFUNDTYPE","Partial");
                encoder.add("AMT",String.valueOf(amt));
            }
            String NVPRequest = encoder.encode();
            String NVPResponse = caller.call(NVPRequest);
            decoder.decode(NVPResponse);
            String strAck = decoder.get("ACK") ==null?"": decoder.get("ACK");
            map.put("strAck", strAck);
            if (!strAck.equalsIgnoreCase("Success") && !strAck.equalsIgnoreCase("SuccessWithWarning")) {
                String errcode=(decoder.get("L_ERRORCODE0")==null?"":decoder.get("L_ERRORCODE0"));
                String errmsg=(decoder.get("L_SHORTMESSAGE0")==null?"":decoder.get("L_SHORTMESSAGE0"));
                String errdetail=(decoder.get("L_LONGMESSAGE0")==null?"":decoder.get("L_LONGMESSAGE0"));
                log(baseStr+">>Return>>>>>>errcode="+errcode+">>errmsg="+errmsg+">>errdetail="+errdetail,"refund");
                map.put("errcode",errcode);
                map.put("errmsg",errmsg);
                map.put("errdetail",errdetail);
                map.put("status","1");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","9");
            return map;
        }
        String retStr="Amount="+amt+">>>ID="+decoder.get("REFUNDTRANSACTIONID")+">>Fee="+decoder.get("FEEREFUNDAMT")+">>Amt="+decoder.get("GROSSREFUNDAMT");
        retStr+=">>NetAmt="+decoder.get("NETREFUNDAMT")+">>Total="+decoder.get("TOTALREFUNDEDAMT");
        log(baseStr+">>Return>>>>>>"+retStr,"refund");
        map.put("status","0");
        map.put("rrnumber",decoder.get("REFUNDTRANSACTIONID"));
        map.put("Fee",decoder.get("FEEREFUNDAMT"));
        map.put("NetAmt",decoder.get("NETREFUNDAMT"));
        return map;
    }
    /**
     * 获取decoder中(时间最大)的存款信息,以map 的方式进行返回
     * @param decoder
     * @param invnum
     * @param className 日志名
     * @return status trnId name email amt
     */
    public Map<String, Object>  getPaypalSuccessDepositRecord(NVPDecoder decoder,String invnum,String  className){
        Map<String, Object> returnInfoMap=null;
        String status = null;
        String trnId = null;
        String name = null;
        String email = null;
        String amt=null;
        int result=9; //默认
        String  beforeTime=null;//保存之前时间
        String currrentTime=null;//保存当前时间
        log("query>> invnum:"+invnum, className);
        if (decoder.get("L_TRANSACTIONID0") != null && !decoder.get("L_TRANSACTIONID0").equals("")) {
            returnInfoMap=new HashMap<String, Object>();
            int intCount = 0;
            while (decoder.get("L_TRANSACTIONID" + intCount) != null &&
                    decoder.get("L_TRANSACTIONID" + intCount).length() > 0) {
                currrentTime=(decoder.get("L_TIMESTAMP"+intCount)!=null)?decoder.get("L_TIMESTAMP"+intCount):"";

                //从第二条开始比较时间 取大的存款记录
                if(intCount>0 && beforeTime!=null && currrentTime!=null ){
                    result= compareTo(beforeTime,currrentTime,className);
                }

                //当上一条记录中的时间和当前这条记录的时间比较时,时间小于上一条记录的时间,不保存当前记录的信息
                if(result==1 || result==-7){
                    intCount++;
                    continue;
                }

                status = (decoder.get("L_STATUS" + intCount) != null) ? decoder.get("L_STATUS" + intCount): "";
                trnId = (decoder.get("L_TRANSACTIONID" + intCount) != null) ? decoder.get("L_TRANSACTIONID" + intCount): "";
                name = (decoder.get("L_NAME" + intCount) != null) ? decoder.get("L_NAME" + intCount) : "";
                email = (decoder.get("L_EMAIL" + intCount) != null) ? decoder.get("L_EMAIL" + intCount) : "";
                amt=	(decoder.get("L_AMT" + intCount) != null) ? decoder.get("L_AMT" + intCount): "";
                log("["+intCount+"] :time:"+currrentTime+"trnStatus:"+status+">>trnId:"+ trnId + ">>Name:" + name + ">>email:"+email,className);
                System.out.println( "["+intCount+"] :time:"+currrentTime+"trnStatus:"+status+">>trnId:"+ trnId + ">>Name:" + name + ">>email:"+email);
                beforeTime=currrentTime;
                intCount++;
            }
            log("save info>> ["+intCount+"] :time:"+beforeTime+"trnStatus:"+status+">>trnId:"+ trnId + ">>Name:" + name + ">>email:"+email,className);
            returnInfoMap.put("status", status);
            returnInfoMap.put("trnId", trnId);
            returnInfoMap.put("name", name);
            returnInfoMap.put("email", email);
            returnInfoMap.put("amt", amt);
        }
        return returnInfoMap ;
    }

    /**
     *
     * @param searchData
     * @return
     * @throws Exception
     */
    public Map<String,Object> TransactionSearch(Map<String,String> searchData)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        NVPEncoder encoder = new NVPEncoder();
        NVPDecoder decoder = new NVPDecoder();
        try {
            encoder.add("METHOD", "TransactionSearch");
            encoder.add("TRXTYPE", "Q");
            Iterator<String> it=searchData.keySet().iterator();
            while (it.hasNext()) {
                String key=it.next();
                if("STARTDATE".equalsIgnoreCase(key)||"ENDDATE".equalsIgnoreCase(key))
                    encoder.add(key, getDate(new SimpleDateFormat("yyyy-MM-dd").parse(searchData.get(key))));
                else
                    encoder.add(key,searchData.get(key));
            }
            encoder.add("STARTDATE",getDate(new SimpleDateFormat("yyy-MM-dd").parse("2014-07-17")));
            String NVPRequest = encoder.encode();
//            caller.setAPIProfile(getAPIProfile());
            String NVPResponse = caller.call(NVPRequest);
            decoder.decode(NVPResponse);
            String strAck = decoder.get("ACK") == null ? "" : decoder.get("ACK").trim();
            map.put("strAck", strAck);
            if (!strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_Success")) && !strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_SuccessWithWarning"))) {
                errorProcess(decoder,LogType.TRANSACTIONSEARCH.getValue());
                     map.put("status", "error");
                     map.put("flag", false);
                    return map;
            }
            map.put("status","true");
            map.put("flag",true);
            log("LogDate:"+formatDate(new Date())+"LogType: ",LogType.TRANSACTIONSEARCH.getValue());
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            for(int i=0;i<100;i++) {
                if (decoder.get("L_TIMESTAMP" + i) != null && !"".equals(decoder.get("L_TIMESTAMP" + i))) {
                    Map<String, String> ma = new HashMap<String, String>();
                    ma.put("timeStamp", decoder.get("L_TIMESTAMP" + i));
                    ma.put("type", decoder.get("L_TYPE" + i));
                    ma.put("email",decoder.get("L_EMAIL"+i));
                    ma.put("name",decoder.get("L_EMAIL"+i));
                    ma.put("transactionId",decoder.get("L_TRANSACTIONID"+i));
                    ma.put("status",decoder.get("L_STATUS"+i));
                    ma.put("amt",decoder.get("L_AMT"+i));
                    ma.put("netAmt",decoder.get("L_NETAMT"+i));
                    result.add(ma);
                }else
                    break;
            }
            map.put("result",result);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "error");
            map.put("flag", false);
            return map;
        }
    }

    public static String getStartDate(Date date) {
        Calendar startDateObj = Calendar.getInstance();
        startDateObj.set(Calendar.MONTH,-48);
        StringBuffer sb = new StringBuffer();
        sb.append(formatDate(date));
        sb.append("T00:00:00Z");
        return sb.toString();
    }

    public static String getEndDate(Date date) {
        StringBuffer sb = new StringBuffer();
        sb.append(formatDate(date));
        sb.append("T24:00:00Z");
        return sb.toString();
    }
    public static String getDate(Date date) {
        StringBuffer sb = new StringBuffer();
        sb.append(formatDate(date));
        sb.append("T24:00:00Z");
        return sb.toString();
    }

    /**
     * 提交付款信息到Paypal
     * @param encoder
     * @param className
     * @return
     * @throws PayPalException
     */
    public Map<String,Object> setExpress(NVPEncoder encoder,String className) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        encoder.add("METHOD","SetExpressCheckout");
//        encoder.add("RETURNURL",returnURL + "?paymentAmount=" + request.getParameter("paymentAmount") + "&currencyCodeType=" + request.getParameter("currencyCodeType"));

        NVPDecoder decoder=new NVPDecoder();
        try{
        String strNVPRequest=encoder.encode();
//            caller.setAPIProfile(getAPIProfile());
        String resultResponse=caller.call(strNVPRequest);
        decoder.decode(resultResponse);
        String strAck=decoder.get("ACK");
        if (!strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_Success")) && !strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_SuccessWithWarning"))) {
                errorProcess(decoder,className);
            resultMap.put("status", "error");
            resultMap.put("flag", false);
            resultMap.put("errorInfo",JSON.toJSONString(decoder.getMap()));
            return resultMap;
        }else{
            resultMap.put("status","success");
            resultMap.put("flag",true);
            resultMap.put("url","https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+decoder.get("TOKEN"));
            resultMap.put("TOKEN", decoder.get("TOKEN"));
            return resultMap;
        }
    } catch (PayPalException e) {
        e.printStackTrace();
        resultMap.put("flag", false);
        resultMap.put("status", "error");
        resultMap.put("errorInfo",JSON.toJSONString(decoder.getMap()));
        return resultMap;
    }
    }

    /**
     *  验证Paypal信息及付款
     * @param encoder
     * @param className
     * @return
     */
    public Map<String,Object> doExpress(NVPEncoder encoder,String className){
        Map<String,Object> resultMap=new HashMap<String, Object>();
            encoder.add("METHOD","GetExpressCheckoutDetails");
                NVPDecoder decoder=new NVPDecoder();
            try {
//                caller.setAPIProfile(getAPIProfile());
             String resultResponse=caller.call(encoder.encode());
                decoder.decode(resultResponse);
                String strAck=decoder.get("ACK");
                if (!strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_Success")) && !strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_SuccessWithWarning"))){
                    errorProcess(decoder,className);
                    resultMap.put("status", "error");
                    resultMap.put("flag", false);
                    resultMap.put("errorInfo",JSON.toJSONString(decoder.getMap()));
                    return resultMap;
                }
                //支付验证..........
//                    Map<String, String> verify = (Map<String,String>)verifys.get(decoder.get("TOKEN"));
//                 if(!decoder.get("SHIPTOSTREET").equals(verify.equals("address"))|| !decoder.get("INVNUM").equals(verify.get("invnum")) || !decoder.get("PHONENUM").equals(verify.get("phone"))){
//                    errorProcess(decoder,LogType.GET_EXPRESS_CHECKOUT.getValue());
//                     resultMap.put("status", "error");
//                     resultMap.put("flag", false);
//                     resultMap.put("errorInfo",JSON.toJSONString(decoder.getMap()));
//                     return resultMap;
//                 }
                    verifys.remove(decoder.get("TOKEN"));
                    encoder.add("METHOD","DoExpressCheckoutPayment");
                    encoder.add("TOKEN", decoder.get("TOKEN"));
                    encoder.add("PAYERID", decoder.get("PAYERID"));//付款人PaypalID
                    encoder.add("PAYMENTACTION",decoder.get("PAYMENTACTION"));
                    encoder.add("AMT",decoder.get("AMT"));
                    encoder.add("CURRENCYCODE",decoder.get("CURRENCYCODE"));
                    encoder.add("SHIPPINGAMT",decoder.get("SHIPPINGAMT"));//运费
                    encoder.add("TAXAMT",decoder.get("TAXAMT")); //税金合计
//                    encoder.add("NOTIFYURL","http://localhost:8080/order/notify.hyml");//待定(通知URL)  //外网环境测试
                    encoder.add("SHIPTOSTREET",decoder.get("SHIPTOSTREET"));

                    encoder.add("PAYMENTACTION","Sale");
                    encoder.add("SHIPTOCOUNTRYCODE","US");
                    encoder.add("SHIPTOSTREET","111 Bliss Ave"); //街道地址
                    encoder.add("SHIPTOCITY", "San Jose"); //城市
                    encoder.add("SHIPTOSTATE", "CA"); //州或省
                encoder.add("SHIPTOZIP","95128");
                encoder.add("ITEMAMT", decoder.get("ITEMAMT"));

                resultResponse=caller.call(encoder.encode());
                    NVPDecoder decoder1=new NVPDecoder();
                    decoder1.decode(resultResponse);
                    strAck=decoder1.get("ACK");
                if (!strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_Success")) && !strAck.equalsIgnoreCase(MessageInfo.getMessage("PAYPAL_SuccessWithWarning"))) {
                    errorProcess(decoder1,LogType.DO_EXPRESS_CHECKOUT.getValue());
                    resultMap.put("status", "error");
                    resultMap.put("flag", false);
                    resultMap.put("errorInfo",JSON.toJSONString(decoder1.getMap()));
                    return resultMap;
                }
                //数据库字段修改,保存.............[交易状态,订单状态,更新 用户相关属性..]
//                Order order=orderServiceImpl.getBySql("select * from t_order where invnum='"+decoder.get("INVNUM")+"'");
//                Map<String,Object> updataMap=new HashMap<String, Object>();
//                updataMap.put("status",OrderServiceImpl.OrderStatus.pay.getValue());
//                updataMap.put("utime",new Date());
//                updataMap.put("transactionId", decoder1.get("TRANSACTIONID"));
//                boolean result= orderServiceImpl.updateOnMap("t_order", updataMap, "invnum='" + decoder.get("INVNUM") + "'");
//                if(result) {
                    System.out.println("交易成功");
                    log(MessageFormat.format("logDate:{0},Paypal_Info:(订单ID)invnum={1}  status=Paypal_Success", formatDate(new Date()), decoder.get("invnum")), "PayPal_Info");
                    resultMap.put("status", "success");
                    resultMap.put("flag", true);
                    resultMap.put("decoder", decoder1);
                    return resultMap;
//                }
                } catch (PayPalException e) {
                e.printStackTrace();
            }
        return resultMap;
    }

    /**
     * 记录错误信息
     * @param decoder
     * @param className 错误类型
     */
    public void errorProcess(NVPDecoder decoder,String className){
        long time=System.currentTimeMillis();
//                log("paypal Deopsit is error on Date:"+formatDate(new Date())+"error is Detail : "+ JSON.toJSONString(decoder),"Paypal_setExpress_error");
        StringBuffer paypalErrorMsg=new StringBuffer("Paypal_setExpressCheckout Deopsit is error==>\n\r");
        paypalErrorMsg.append("logDate:"+time);
        paypalErrorMsg.append(">TIMESTAMP:");
        paypalErrorMsg.append(decoder.get("TIMESTAMP"));
        paypalErrorMsg.append(">CORRELATIONID:");
        paypalErrorMsg.append(decoder.get("CORRELATIONID"));
        paypalErrorMsg.append(">VERSION:");
        paypalErrorMsg.append(decoder.get("VERSION"));
        paypalErrorMsg.append(">BUILD:");
        paypalErrorMsg.append(decoder.get("BUILD"));
        if (decoder.get("L_ERRORCODE0")!=null&&!decoder.get("L_ERRORCODE0").equals("")) {
            StringBuffer shortMsg=new StringBuffer("eid:");
            shortMsg.append(time);
            int index=0;
            while(decoder.get("L_ERRORCODE"+index)!=null&&!decoder.get("L_ERRORCODE"+index).equals("")){
                paypalErrorMsg.append(">>L_ERRORCODE");
                paypalErrorMsg.append(index);
                paypalErrorMsg.append(":");
                paypalErrorMsg.append(decoder.get("L_ERRORCODE"+index));

                paypalErrorMsg.append("&L_SHORTMESSAGE");
                paypalErrorMsg.append(index);
                paypalErrorMsg.append(":");
                paypalErrorMsg.append(decoder.get("L_SHORTMESSAGE"+index));

                shortMsg.append(">index:");
                shortMsg.append(index);
                shortMsg.append(decoder.get("L_SHORTMESSAGE"+index));

                paypalErrorMsg.append("&L_LONGMESSAGE");
                paypalErrorMsg.append(index);
                paypalErrorMsg.append(":");
                paypalErrorMsg.append(decoder.get("L_LONGMESSAGE"+index));

                paypalErrorMsg.append("&L_SEVERITYCODE");
                paypalErrorMsg.append(index);
                paypalErrorMsg.append(":");
                paypalErrorMsg.append(decoder.get("L_SEVERITYCODE"+index));
                index++;
            }
            log(paypalErrorMsg.toString(),className);
        }
    }

    /**
     * 返回指定长度的数字序列
     * @param length
     * @return
     */
    public static String getUuid(int length){
        String[] meta={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        StringBuffer sb=new StringBuffer();
        Random random=new Random();
        for(int i=0;i<length;i++)
        sb.append(meta[random.nextInt(1000)%16]);
        return sb.toString();
    }

    public static String dateFormat(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static void main(String[] args) {
        try {
            System.out.println(PaypalUtil.getAPIProfile().getAPIPassword());
                String str="a";
                System.out.println(str="abc");
            System.out.println(str);
        } catch (PayPalException e) {
            e.printStackTrace();
        }
    }
}
