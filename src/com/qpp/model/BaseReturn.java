package com.qpp.model;

import com.qpp.util.JsonTool;

/**
 * Created by admin on 2014/7/29.
 */
public class BaseReturn {
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
