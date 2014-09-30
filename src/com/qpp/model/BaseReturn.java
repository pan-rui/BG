package com.qpp.model;

import com.qpp.listener.SpringContextUtil;
import com.qpp.util.JsonTool;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by admin on 2014/7/29.
 */
public class BaseReturn implements Serializable {
    private static final long serialVersionUID = -5041754406649553740L;
    public static int Err_data_empty=100;
    public static int Err_data_duplicate=101;
    public static int Err_data_inValid=102;
    public static int Err_logic_isNotValid=201;
    public static int Err_system_error=301;
    private int result;
    private String errMessage;
    private Object data;
    public BaseReturn(){
    }
    @Override
    public String toString(){
        return JsonTool.getJsonString(this);
    }

    public BaseReturn(int lresult,Object ldata){
        result=lresult;
        data=ldata;
    }

    public BaseReturn(String errMessage, int result) {
        this.result=result;
        this.errMessage=errMessage;
    }
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
