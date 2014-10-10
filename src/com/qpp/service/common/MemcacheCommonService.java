package com.qpp.service.common;

import com.danga.MemCached.MemCachedClient;
import com.qpp.model.AppKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SZ_it123 on 2014/9/23.
 */
@Service
public class MemcacheCommonService {
    @Autowired
    protected MemCachedClient memcachedClient;
    public static String Head_App_Auth="Qpp_App_Auth";
    public AppKey getAppInfo(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getHeader(Head_App_Auth)==null) return null;
        String token=httpServletRequest.getHeader(Head_App_Auth);
        if (!memcachedClient.keyExists(token)) return null;
        AppKey tAppInfo=(AppKey)memcachedClient.get(token);
        return tAppInfo;
    }
    public String getUser(HttpServletRequest httpServletRequest){
        return "";
    }
}
