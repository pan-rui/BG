package com.qpp.service.market;

import com.amazonaws.fps.AmazonFPS;
import com.amazonaws.fps.AmazonFPSConfig;
import com.amazonaws.fps.AmazonFPSException;
import com.amazonaws.fps.model.*;
import com.amazonaws.utils.PropertyBundle;
import com.amazonaws.utils.PropertyKeys;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by qpp on 7/23/2014.
 */
@Component
public class AwsUtil {

    protected static AmazonFPS service;

    static {
        try {
            service = new com.amazonaws.fps.AmazonFPSClient(PropertyBundle.getProperty(PropertyKeys.AWS_ACCESS_KEY), PropertyBundle.getProperty(PropertyKeys.AWS_SECRET_KEY));

        } catch (AmazonFPSException e) {
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Type: " + e.getErrorType());
            System.out.println("Request ID: " + e.getRequestId());
            System.out.print("XML: " + e.getXML());
        }
    }

    public enum TransactionType {
        Cancel,
        CancelToken,
        GetAccountActivity,
        GetAccountBalance,
        GetRecipientVerificationStatus,
        GetTokens,
        GetTokenByCaller,
        GetTokenUsage,
        GetTransaction,
        GetTransactionStatus,
        Pay,
        Refund,
        Reserve,
        Settle,
        VerifySignature
    }

    /**
     * 交易通用方法
     *
     * @param reqMap          请求参数
     * @param transactionType 交易类型
     * @return 结果Map
     */
//    public Map<String, Object> payment(Map<String, String> reqMap, TransactionType transactionType) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        Object target = null;
//        Object resp = null;
//        Object result = null;
//        TransactionStatus statu = null;
//        Set<String> keys = reqMap.keySet();  //reqMap中的Key首字母大写
//        try {
//            target = Class.forName("com." + transactionType.name() + "Request");
//            for (String key : keys) {
//                Method me = target.getClass().getDeclaredMethod("set" + key, String.class);
//                me.setAccessible(true);
//                if (reqMap.get(key) != null)
//                    me.invoke(target, reqMap.get(key));
//            }
//
//            Method method = service.getClass().getDeclaredMethod(transactionType.name(), String.class);
//            method.setAccessible(true);
//            resp = method.invoke(service, target);
//            if (Class.forName("com." + transactionType.name() + "Response").isInstance(resp)) {
//                Method re = resp.getClass().getDeclaredMethod("get" + transactionType.name() + "Result", null);
//                re.setAccessible(true);
//                result = re.invoke(resp, null);
//                statu = (TransactionStatus) result.getClass().getDeclaredMethod("getTransactionStatus", null).invoke(result.getClass(), null);
//            }
//            if (!"Success".equalsIgnoreCase(statu.value())) {
//                resultMap.put("status", statu.value());
//                resultMap.put("flag", false);
//                resultMap.put("errorInfo", resp.getClass().getDeclaredMethod("toJSON", null).invoke(resp.getClass(), null));
//                Logger logger = Logger.getLogger(this.getClass().getName());
//                //写入日志.......
//                return resultMap;
//            }
//            resultMap.put("status", statu.value());
//            resultMap.put("flag", true);
//            if ("GetTransaction".equalsIgnoreCase(transactionType.name()))
//                resultMap.put("detail", result.getClass().getDeclaredMethod(transactionType.name(),String.class).invoke(result.getClass(), null));
//            //修改数据库订单状态等..........
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return resultMap;
//    }

    /**
     * 支付
     * @param reqMap 支付参数
     * @return
     */
    public Map<String, Object> payment(Map<String, String> reqMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PayRequest request = new PayRequest();
        Set<String> keys = reqMap.keySet();
        try {
//            for (String key : keys) {
//                if (key != null && reqMap.get(key) != null) {
//                    Method method = request.getClass().getMethod("set" + key, String.class);
//                    method.invoke(request.getClass(), reqMap.get(key));
//                }
//            }
            request.setCallerDescription(reqMap.get("CallerDescription"));
            request.setOverrideIPNURL(reqMap.get("OverrideIPNURL"));
            request.setCallerReference(reqMap.get("CallerReference"));
            request.setSenderDescription(reqMap.get("SenderDescription"));
            request.setSenderTokenId(reqMap.get("SendTokenId"));
            request.setTransactionAmount(new Amount(CurrencyCode.fromValue(reqMap.get("CurrencyCode")),reqMap.get("TransactionAmount")));
//            request.setTransactionTimeoutInMins(Integer.parseInt(reqMap.get("TransactionTimeoutInMins")));
            PayResponse resp=service.pay(request);
            PayResult result = resp.getPayResult();
            String status=result.getTransactionStatus().value();
            if (!status.equalsIgnoreCase("Success")){
                resultMap.put("status", status);
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
               //写入日志......

                return resultMap;
            }
            resultMap.put("status", status);
            resultMap.put("flag", true);
            resultMap.put("transactionId",result.getTransactionId());
            //写入日志..
        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 根据交易ID查询交易明细
     * @param transactionId 交易ID
     * @return
     */
    public Map<String,Object> getTransaction(String transactionId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        GetTransactionRequest request=new GetTransactionRequest();
        try {
            request.setTransactionId(transactionId);
            GetTransactionResponse resp=service.getTransaction(request);
            GetTransactionResult result = resp.getGetTransactionResult();
            TransactionDetail trans=result.getTransaction();
            if (trans==null){
                resultMap.put("status", "error");
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", "Success");
            resultMap.put("flag", true);
            resultMap.put("detail",trans);
            //写入日志...
        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;

    }

    /**
     * 取消交易
     * @param reqMap
     * @return
     */
    public Map<String,Object> cancel(Map<String,String> reqMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CancelRequest request = new CancelRequest();
        request.setTransactionId(reqMap.get("TransactionId"));
        String str=null;
        if ((str=reqMap.get("Description"))!=null)
            request.setDescription(str);
        if((str=reqMap.get("OVerrideIPNURL"))!=null)
            request.setOverrideIPNURL(str);
        try {
            CancelResponse resp=service.cancel(request);
            CancelResult result=resp.getCancelResult();
            String status=result.getTransactionStatus().value();
            if(!status.equalsIgnoreCase("Success")){
                resultMap.put("status", status);
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志.......
                return resultMap;
            }
            resultMap.put("status", status);
            resultMap.put("flag", true);
            resultMap.put("transactionId",result.getTransactionId());
        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 退款
     * @param reqMap
     * @return
     */
    public Map<String,Object> refund(Map<String,Object> reqMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        RefundRequest request = new RefundRequest();
        if(!reqMap.containsKey("CallerReference"))
            throw new IllegalArgumentException("Argument:CallerReference is not found.....");
        if(!reqMap.containsKey("TransactionId"))
            throw new IllegalArgumentException("Argument:TransactionId is not found.....");
        Set<String> keys = reqMap.keySet();
        try {
            for (String key : keys) {
                if (key != null && reqMap.get(key) != null) {
                    Method method = request.getClass().getMethod("set" + key, String.class);
                    method.invoke(request.getClass(), reqMap.get(key));
                }
            }
            RefundResponse resp=service.refund(request);
            RefundResult result = resp.getRefundResult();
            String status=result.getTransactionStatus().value();
            if (!status.equalsIgnoreCase("Success")){
                resultMap.put("status", status);
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", status);
            resultMap.put("flag", true);
            resultMap.put("transactionId",result.getTransactionId());
            //写入日志..
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取账户余额.
     * @return
     */
    public Map<String,Object> getAccountBalance() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        GetAccountBalanceRequest request=new GetAccountBalanceRequest();
        try {
            GetAccountBalanceResponse resp=service.getAccountBalance(request);
            GetAccountBalanceResult result=resp.getGetAccountBalanceResult();
            AccountBalance accountBalance=result.getAccountBalance();
            if(accountBalance==null){
                resultMap.put("status", "error");
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", "Success");
            resultMap.put("flag", true);
            resultMap.put("balance",accountBalance);

        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取交易Token
     * @param parms
     * @return
     */
    public Map<String,Object> getTokenByCaller(String parms) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        GetTokenByCallerRequest request = new GetTokenByCallerRequest();
        if(parms.equalsIgnoreCase("CallerReference"))
            request.setCallerReference(parms);
        if(parms.equalsIgnoreCase("TokenId"))
            request.setTokenId(parms);
        try {
            GetTokenByCallerResponse resp = service.getTokenByCaller(request);
            GetTokenByCallerResult result=resp.getGetTokenByCallerResult();
            Token token=result.getToken();
            if(result==null){
                resultMap.put("status", "error");
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", "Success");
            resultMap.put("flag", true);
            resultMap.put("token",token);

        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取交易状态....
     * @param transactionId
     * @return
     */
    public Map<String,Object> getTransactionStatus(String transactionId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        GetTransactionStatusRequest request = new GetTransactionStatusRequest();
        if (transactionId == null || "".equals(transactionId))
            throw new IllegalArgumentException("error:transactionId is required Argument.");
        request.setTransactionId(transactionId);
        try {
            GetTransactionStatusResponse resp=service.getTransactionStatus(request);
            GetTransactionStatusResult result=resp.getGetTransactionStatusResult();
            TransactionStatus status=result.getTransactionStatus();
            if (!"Success".equalsIgnoreCase(status.value())) {
                resultMap.put("status", "error");
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", "Success");
            resultMap.put("flag", true);
            resultMap.put("message",result.getStatusMessage());

        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 解决未完成的交易
     * @param reqMap
     * @return
     */
    public Map<String,Object> settle(Map<String,String> reqMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SettleRequest request = new SettleRequest();
        String str=null;
        if((str=reqMap.get("OverrideIPNURL"))!=null)
            request.setOverrideIPNURL(str);
        if((str=reqMap.get("ReserveTransactionId"))!=null)
            request.setOverrideIPNURL(str);
        if((str=reqMap.get("TransactionAmount"))!=null)
            request.setOverrideIPNURL(str);
        try {
            SettleResponse resp = service.settle(request);
            SettleResult result=resp.getSettleResult();
            String status=result.getTransactionStatus().value();
            if(!"Success".equalsIgnoreCase(status)){
                resultMap.put("status", status);
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", status);
            resultMap.put("flag", true);
            resultMap.put("transactionId",result.getTransactionId());
            //写入日志..
        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * Amazon 存储交易
     * @param reqMap
     * @return
     */
    public Map<String, Object> reserve(Map<String, String> reqMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ReserveRequest request = new ReserveRequest();
        Set<String> keys = reqMap.keySet();
        try {
            for (String key : keys) {
                if (key != null && reqMap.get(key) != null) {
                    Method method = request.getClass().getMethod("set" + key, String.class);
                    method.invoke(request.getClass(), reqMap.get(key));
                }
            }
            ReserveResponse resp=service.reserve(request);
            ReserveResult result = resp.getReserveResult();
            String status=result.getTransactionStatus().value();
            if (!status.equalsIgnoreCase("Success")){
                resultMap.put("status", status);
                resultMap.put("flag", false);
                resultMap.put("errorInfo", resp.toJSON());
                //写入日志......

                return resultMap;
            }
            resultMap.put("status", status);
            resultMap.put("flag", true);
            resultMap.put("transactionId",result.getTransactionId());
            //写入日志..
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (AmazonFPSException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取对象所有的set方法
     *
     * @param clazz
     * @return
     */
    public List<Method> getMethods(Class clazz) {
        List<Method> resultList = new ArrayList<Method>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method me : methods) {
//            if(me.getName().startsWith("set"))
            me.setAccessible(true);
            if (me.getName().matches("^set[A-Z]+\\w*"))
                resultList.add(me);
//            System.out.println(me.getName());
        }
        return resultList;
    }


    public static void main(String[] args) {
//        System.out.println(TransactionType.Cancel.name());
//        System.out.println(TransactionType.Cancel.toString());
//        System.out.println("getCanonicalName print is \t"+AmazonFPSClient.class.getCanonicalName());
//        System.out.println("getName print is \t"+AmazonFPSClient.class.getName());
//        System.out.println("getSimpleName print is \t"+AmazonFPSClient.class.getSimpleName());
        AwsUtil awsUtil=new AwsUtil();
        System.out.println(URLDecoder.decode("http%3A%2F%2Fwww.oschina.net%2Fcode%2Fsnippet_100825_21906"));
        System.out.println(awsUtil.getMethods(String.class));
        System.out.println("===========================分隔线================================");
        System.out.println(awsUtil.getMethods(PayRequest.class));

            System.out.println(new AmazonFPSConfig().getServiceURL());
    }
}

