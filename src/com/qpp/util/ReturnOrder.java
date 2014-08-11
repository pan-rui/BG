package com.qpp.util;

/**
 * Created with IntelliJ IDEA.
 * User: gary
 * Date: 13-3-14
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class ReturnOrder extends BaseXML {
    public ReturnOrder(String xml){
        super(xml);
    }
    public String getLocation(){
        return getHeaderValue("location");
    }
    public String getPhone(){
        return getValue("phone");
    }
    public String getAddNum(){
        return getValue("addnum");
    }
    public String getReqNum(){
        return getValue("reqnum");
    }

    public static void main(String[] args) {
        String tt= FileUtil.ReadFile("d:\\temp\\send.xml");
        ReturnOrder vd=new ReturnOrder("");
        System.out.println("Action="+vd.getAction()+",ResultId="+vd.getResultID());
    }
}
