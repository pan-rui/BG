package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.AppInfoDao;
import com.qpp.dao.AppRightDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TAppInfo;
import com.qpp.model.TAppRight;
import com.qpp.model.TAppRightPK;
import com.qpp.util.DESPlus;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common")
public class AppInfoAction extends BaseAction {
    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private AppRightDao appRightDao;
    @RequestMapping(value = "app/{appCode}",method = RequestMethod.POST)
    @ResponseBody
    private BaseReturn newApp(@PathVariable final String appCode,HttpServletRequest request){
        String appKey=appInfoDao.insertApp(appCode);
        BaseReturn baseReturn=new BaseReturn();
        if (StringUtils.isEmpty(appKey)){
            baseReturn.setResult(101);
            baseReturn.setErrMessage(getMessage(request,"data.duplicate",null));
        }else{
            baseReturn.setData(appKey);
        }
        return baseReturn;
    }
    @RequestMapping(value = "app/{appCode}",method = RequestMethod.PUT)
    @ResponseBody
    private BaseReturn changeAppRole(@PathVariable final String appCode,@RequestParam("role") short role,HttpServletRequest request){
        TAppInfo tAppInfo=appInfoDao.getById(appCode);
        BaseReturn baseReturn=new BaseReturn();
        if (tAppInfo==null){
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }else{
            tAppInfo.setRole(role);
            appInfoDao.update(tAppInfo);
            baseReturn.setData(super.getMessage(request,"common.success",null));
        }
        return baseReturn;
    }
    @RequestMapping(value = "app/{appCode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn checkApp(@PathVariable final String appCode,@RequestParam("appKey") String appKey,HttpServletRequest request){
        TAppInfo appInfo=appInfoDao.getById(appCode);
        BaseReturn baseReturn=new BaseReturn();
        if (appInfo==null || !appInfo.getAppkey().equals(appKey)){
            baseReturn.setResult(102);
            baseReturn.setErrMessage(getMessage(request,"data.inValid",null));
        }else{
            String token=getToken(appInfo);
            baseReturn.setData(token);
            memcachedClient.set(token, appInfo, new Date(2 * 60 * 60 * 1000));
        }
        return baseReturn;
    }
    private String getToken(TAppInfo appInfo){
        String token=appInfo.getAppkey()+"--"+appInfo.getStartTime().getTime();
        try{
            DESPlus des = new DESPlus("qpp.com");
            token=des.encrypt(token);
            token=Base64.encodeBase64String(token.getBytes());
            token=URLEncoder.encode(token);
        }catch (Exception e){
        }
        return token;
    }
    @RequestMapping(value = "appRight/{role}",method = RequestMethod.POST)
    @ResponseBody
    private BaseReturn newAppRight(@PathVariable short role,@RequestParam("url") String url,HttpServletRequest request){
        TAppRightPK tAppRightPK=new TAppRightPK();
        tAppRightPK.setRole(role);
        tAppRightPK.setUrl(url);
        TAppRight tAppRight=appRightDao.getById(tAppRightPK);
        if (tAppRight==null) {
            tAppRight=new TAppRight();
            tAppRight.setRole(role);
            tAppRight.setUrl(url);
            appRightDao.save(tAppRight);
        }
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(super.getMessage(request,"common.success",null));
        return baseReturn;
    }
    @RequestMapping(value = "appRight/{role}",method = RequestMethod.DELETE)
    @ResponseBody
    private BaseReturn delAppRight(@PathVariable short role,@RequestParam("url") String url,HttpServletRequest request){
        TAppRightPK tAppRightPK=new TAppRightPK();
        tAppRightPK.setRole(role);
        tAppRightPK.setUrl(url);
        TAppRight tAppRight=appRightDao.getById(tAppRightPK);
        if (tAppRight!=null)
            appRightDao.delete(tAppRight);
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(super.getMessage(request,"common.success",null));
        return baseReturn;
    }

}
