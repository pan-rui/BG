package com.qpp.action;

import com.danga.MemCached.MemCachedClient;
import com.qpp.listener.SpringContextUtil;
import com.qpp.model.BaseReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseAction{
    @Autowired
    protected MemCachedClient memcachedClient;
    protected final org.apache.log4j.Logger loger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    public static String getMessageStatic(HttpServletRequest request,String key,Object[] obj){
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        MessageSource resources = SpringContextUtil.getApplicationContext();
        String message = resources.getMessage(key, obj,locale);
        return message;
    }

    public String getMessage(HttpServletRequest request,String key,Object[] obj){
        return BaseAction.getMessageStatic(request,key,obj);
    }
    protected String getAppCodefromToken(String token){
        if (memcachedClient.keyExists(token))
            return memcachedClient.get(token).toString();
        else
            return null;
    }
    protected long getUserId(HttpServletRequest request){
        //memcachedClient.get("UserToken")
        return 0;
    }


    //日期格式化为常用格式
    public static String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String formatDateTime(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(date);
    }
    private String readStream(HttpServletRequest request){
        String line ="";
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            line=sb.toString();
        }catch(Exception e){
            line="";
        }
        return line;
    }
    @ExceptionHandler
    @ResponseBody
    public BaseReturn exp(HttpServletRequest request, Exception ex,HttpServletResponse response) {
        BaseReturn baseReturn=new BaseReturn();
        ex.printStackTrace();
        if (ex instanceof HttpMessageNotReadableException){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(getMessage(request,"data.inValid",null));
        }else{
            baseReturn.setResult(BaseReturn.Err_system_error);
            baseReturn.setErrMessage(ex.getMessage());
        }
        loger.error(ex.getMessage());
        return baseReturn;
    }

    //protected final org.slf4j.Logger loger = org.slf4j.LoggerFactory.getLogger(this.getClass().getName());
}
