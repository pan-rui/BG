package com.qpp.filter;

import com.qpp.action.BaseAction;
import com.qpp.dao.AppRightDao;
import com.qpp.model.AppKey;
import com.qpp.model.BaseReturn;
import com.qpp.service.common.MemcacheCommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Set;

public class appFilter implements HandlerInterceptor {
    protected final Logger loger = Logger.getLogger(this.getClass().getName());
    @Autowired
    protected AppRightDao appRightDao;
    @Autowired
    private MemcacheCommonService memcacheCommonService;
    private Set<String> ignoreUrl;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
//        HandlerMethod handlerUrl=(HandlerMethod) handler;
//        RequestMapping reqMapping=handlerUrl.getMethodAnnotation(RequestMapping.class);
        loger.debug("CGP filter uri:"+httpServletRequest.getRequestURI()+",method:"+httpServletRequest.getMethod());
        if (checkIgnoreUrl(httpServletRequest.getRequestURI())) return true;
        AppKey tAppInfo=memcacheCommonService.getAppInfo(httpServletRequest);
        if (tAppInfo==null){
            writeResponse(httpServletResponse,new BaseReturn(201, BaseAction.getMessageStatic(httpServletRequest,"logic.isNotValid",null)).toString());
            return false;
        }else{
            boolean isValid=appRightDao.getAppRight(tAppInfo.getOid(),httpServletRequest.getRequestURI().toString());
            if (!isValid){
                writeResponse(httpServletResponse,new BaseReturn(201, BaseAction.getMessageStatic(httpServletRequest,"logic.isNotValid",null)).toString());
                return false;
            }else
                return true;
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
    private boolean checkIgnoreUrl(String url){
        for(String urlList:ignoreUrl){
            if (url.startsWith(urlList)) return true;
        }
        return false;
    }

    public Set<String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(Set<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
}
