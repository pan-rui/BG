package com.qpp.filter;

import com.qpp.dao.ApiLogDao;
import com.qpp.model.ApiLog;
import com.qpp.model.AppKey;
import com.qpp.service.common.MemcacheCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by SZ_it123 on 2014/9/23.
 */
public class apiLogFilter implements HandlerInterceptor {
    @Autowired
    private ApiLogDao apiLogDao;
    @Autowired
    private MemcacheCommonService memcacheCommonService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (!httpServletRequest.getRequestURI().startsWith("/common/apiLog")){
            ApiLog apiLog=new ApiLog();
            apiLog.setApiUrl(httpServletRequest.getRequestURI());
            apiLog.setIp(httpServletRequest.getRemoteAddr());
            apiLog.setCreateDate(new Date());
            AppKey tAppInfo=memcacheCommonService.getAppInfo(httpServletRequest);
            if (tAppInfo!=null)
                apiLog.setAppId(tAppInfo.getOid());
            apiLogDao.save(apiLog);
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
