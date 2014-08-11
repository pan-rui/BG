package com.qpp.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class BaseXML {
    protected Document doc;
    protected Element base;

    public BaseXML(){
        init();
    }
    public void init(){
        doc= DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        base=doc.addElement("response");
    }
    public BaseXML(String xml){
        try{
            doc= DocumentHelper.parseText(xml);
            base=doc.getRootElement();
        }catch(Exception e){
            init();
            setResultID("1003");
            setResultMessage("请求数据格式异常,解析XML错误");
        }
    }
    public void setBase(String value){
        base.setName(value);
    }
    public String getAction(){
        return getHeaderValue("action");
    }
    public void setAction(String value){
        setHeaderValue("action",value);
    }
    public String getHeaderValue(String node){
        return getVariant(node,"header");
    }
    public void setHeaderValue(String nodeName,String value){
        setVariant(nodeName,value,"header");
    }
    public String getValue(String node){
        return getVariant(node,"params");
    }
    public void setValue(String nodeName,String value){
        setVariant(nodeName,value,"params");
    }

    public String toString(){
        //return doc.asXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
        return doc.asXML();
    }
    public void setVariant(String nodeName,String value){
        setVariant(nodeName,value,null);
    }
    public void setVariant(String nodeName,String value,String baseNodeName){
        Element baseNode;
        if (baseNodeName!=null){
            if (base.element(baseNodeName)==null)
                baseNode=base.addElement(baseNodeName);
            else
                baseNode=base.element(baseNodeName);
        }else
            baseNode=base;
        Element node=baseNode.element(nodeName);
        if (node==null)
            node=baseNode.addElement(nodeName);
        node.setText(value);
    }
    public String getVariant(String nodeName,String baseNodeName){
        Element baseNode;
        if (baseNodeName!=null){
            if (base.element(baseNodeName)==null)
                return "";
            else
                baseNode=base.element(baseNodeName);
        }else
            baseNode=base;
        Element node=baseNode.element(nodeName);
        if (node==null)
            return "";
        return node.getText();
    }
    public void setResultID(String id){
        setVariant("result",id,"params");
    }
    public String getResultID(){
        return getValue("result");
    }
    public String getResultMessage(){
        return getValue("resultmessage");
    }
    public void setResultMessage(String value){
        setVariant("resultmessage",value,"params");
    }

    public static void main(String[] args) {
//        BaseXML baseOrder=new BaseXML();
//        baseOrder.setAction("bb");
//        baseOrder.setResultID("0001");
//        baseOrder.setResultMessage("ok");
//        System.out.println(baseOrder.toString());
		htmlTool tool=new htmlTool();
		String content=tool.ReadFile("d:\\temp\\Lettering.xml");		
    	BaseXML baseOrder=new BaseXML(content);
    	System.out.println("Value:"+baseOrder.getVariant("NumStitches","Info"));
    	
    }
}
