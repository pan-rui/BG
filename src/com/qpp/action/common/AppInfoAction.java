package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.AppInfoDao;
import com.qpp.dao.UserDao;
import com.qpp.model.AppKey;
import com.qpp.model.BaseReturn;
import com.qpp.model.TUser;
import com.qpp.service.common.BaseResourceService;
import com.qpp.service.common.MemcacheCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common/app")
public class AppInfoAction extends BaseAction{
    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseResourceService baseResourceService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    private BaseReturn newApp(HttpServletRequest request,@RequestBody @Valid AppKey tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        tAppInfo.setAppkey(UUID.randomUUID().toString().replace("-",""));
        tAppInfo.setCreate_date(new Date());
        return baseResourceService.save(tAppInfo, request);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    private BaseReturn updateApp(HttpServletRequest request,@RequestBody @Valid AppKey tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        return baseResourceService.updateById(AppKey.class,tAppInfo.getOid(),tAppInfo, request);
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAll(HttpServletRequest request){
        return baseResourceService.getAllWithCache(AppKey.class);
    }

    @RequestMapping(value = "user/{userId}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getUserAppInfo(@PathVariable final long userId,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        List resource=appInfoDao.getUserApp(userId);
        baseReturn.setData(resource);
        return baseReturn;
    }
    @RequestMapping(value = "{appCode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAppInfo(@PathVariable final long appCode,HttpServletRequest request){
        return baseResourceService.getById(AppKey.class, appCode, request);
    }

    @RequestMapping(value = "/check/{appCode}",method = RequestMethod.GET,params = "appKey")
    @ResponseBody
    private BaseReturn checkApp(@PathVariable final long appCode,@RequestParam("appKey") String appKey,HttpServletRequest request,HttpServletResponse response){
        AppKey appInfo=appInfoDao.getById(appCode);
        BaseReturn baseReturn=new BaseReturn();
        if (appInfo==null || !appInfo.getAppkey().equals(appKey)){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(getMessage(request,"data.inValid",null));
        }else{
            String token;
           if (appInfo.getToken()!=null && memcachedClient.get(appInfo.getToken())!=null){
                token=appInfo.getToken();
            }else{
                token=getToken(appInfo);
                appInfo.setToken(token);
                appInfo.setStartTime(new Date());
                appInfoDao.update(appInfo);
                memcachedClient.set(token, appInfo, new Date(2 * 60 * 60 * 1000));
            }
            baseReturn.setData(token);
            response.setHeader(MemcacheCommonService.Head_App_Auth,token);
        }
        return baseReturn;
    }
    private String getToken(AppKey appInfo){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
