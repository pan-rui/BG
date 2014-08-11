package com.qpp.filter;

import com.danga.MemCached.MemCachedClient;
import com.qpp.action.BaseAction;
import com.qpp.dao.AppRightDao;
import com.qpp.model.BaseReturn;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class springFilter implements HandlerInterceptor {
    protected final Logger loger = Logger.getLogger(this.getClass().getName());
    @Autowired
    protected MemCachedClient memcachedClient;
    @Autowired
    protected AppRightDao appRightDao;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        loger.debug("CGP filter uri:"+httpServletRequest.getRequestURI()+",user:");
        if (httpServletRequest.getParameter("token")==null){
            writeResponse(httpServletResponse,new BaseReturn(201, BaseAction.getMessageStatic(httpServletRequest,"logic.isNotValid",null)).toString());
            return false;
        }else{
            String token=httpServletRequest.getParameter("token");
            if (!memcachedClient.keyExists(token)){
                writeResponse(httpServletResponse,new BaseReturn(201, BaseAction.getMessageStatic(httpServletRequest,"logic.isNotValid",null)).toString());
                return false;
            }else {
                String appId=(String)memcachedClient.get(token);
                loger.debug("CGP filter uri:"+httpServletRequest.getRequestURI()+",User:"+appId);
                boolean isValid=appRightDao.getAppRight(0,httpServletRequest.getRequestURI().toString());
                if (!isValid){
                    writeResponse(httpServletResponse,new BaseReturn(201, BaseAction.getMessageStatic(httpServletRequest,"logic.isNotValid",null)).toString());
                    return false;
                }else
                    return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //loger.debug("post uri:"+httpServletRequest.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //loger.debug("After uri:"+httpServletRequest.getRequestURI());
    }
    private void writeResponse(HttpServletResponse response,String ret){
        try{
            java.io.PrintWriter out = response.getWriter();
            out.println(ret);
            out.close();
        }catch(Exception e){

        }
    }
}
