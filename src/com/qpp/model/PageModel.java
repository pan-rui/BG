package com.qpp.model;

import com.qpp.service.market.MessageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qpp on 8/1/2014.
 */
public class PageModel<T> implements Serializable {
    private int start; //起始记录
//    private int end;  //结束记录
    private int pageNo=1; //获取记录页
    private int pageSize = Integer.parseInt(MessageInfo.getMessage("BGM_Page_Size")); //每页记录数
    private List<T> data; //当前页记录集合
    private int pageCount=1; //总页数
    private int rowCount; //总记录数
    private boolean hasNext; //是否有下一页
    private boolean hasPrevios; //是否有上一页;
    private String searchProperty;//搜索字段
    private Object searchValue; //搜索值;
    private String orderPropery; //排序字段
    private boolean isDesc=false;

    public PageModel(){
        calc();
        refrsh();
    }
//    public PageModel(int pageNo,int rowCount){
//        this.pageNo=pageNo;
//        setRowCount(rowCount);
//        calc();
//        refrsh();
//    }
    public PageModel(int pageNo,int pageSize){
        this.pageSize=pageSize;
        this.pageNo=pageNo;
        calc();
        refrsh();
    }

    public PageModel(String searchProperty,String searchValue,boolean isDesc){
        this();
        this.searchProperty=searchProperty;
        this.searchValue=searchValue;
        this.isDesc=isDesc;
    }



    //计算起始记录
    public void calc(){
        if(pageNo<=1) {
            this.start = 0;
//            this.end = (start + pageSize)<rowCount?start+pageSize:rowCount;
        }else if(pageNo>=pageCount){
            this.start=(pageCount-1)*pageSize;
//            this.end=rowCount;
        }else{
            this.start=pageNo-1*pageSize;
//            this.end=start+pageSize;
        }
    }
//计算是否有上一页或下一页
    public void refrsh(){
        if(pageCount<=1){
            hasPrevios=false;
            hasNext=false;
        }else if(pageNo<=1){
            hasPrevios=false;
            hasNext=true;
        }else{
            hasPrevios=true;
            hasNext=true;
        }
    }

    public boolean getIsDesc() {
        return isDesc;
    }

    public void setIsDesc(boolean isDesc) {
        this.isDesc = isDesc;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

//    public int getEnd() {
//        return end;
//    }
//
//    public void setEnd(int end) {
//        this.end = end;
//    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        setPageCount((int)Math.floor(Math.round(rowCount*1.0f/pageSize)));
        calc();
        refrsh();
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean getHasPrevios() {
        return hasPrevios;
    }

    public void setHasPrevios(boolean hasPrevios) {
        this.hasPrevios = hasPrevios;
    }

    public String getSearchProperty() {
        return searchProperty;
    }

    public void setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
    }

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public String getOrderPropery() {
        return orderPropery;
    }

    public void setOrderPropery(String orderPropery) {
        this.orderPropery = orderPropery;
    }
}
