package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.model.*;
import com.qpp.service.common.BaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.MessageFormat;

/**
 * Created by SZ_it123 on 2014/9/24.
 */
@Controller
@RequestMapping("/common/appApi")
public class AppApiAction extends BaseAction {
    @Autowired
    private BaseResourceService baseResourceService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    private BaseReturn newApp(HttpServletRequest request,@RequestBody @Valid AppApi tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        //tAppInfo.setType(AppKeyType.APP_KEY);
        return baseResourceService.saveOrUpdate(AppApi.class,tAppInfo,tAppInfo,request);
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAll(HttpServletRequest request){
        return baseResourceService.getAll(VAppApi.class);
    }

    @RequestMapping(value = "{appCode}",method = RequestMethod.GET,params = "type")
    @ResponseBody
    private BaseReturn getAppInfo(@PathVariable final long appCode,HttpServletRequest request,@RequestParam int type){
        return baseResourceService.getByQuery("from VAppApi where type="+type+" and oid="+appCode, request);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    private BaseReturn delApp(HttpServletRequest request,@RequestBody @Valid AppApi tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        //tAppInfo.setType(AppKeyType.APP_KEY);
        return baseResourceService.deleteById(AppApi.class,tAppInfo,request);
    }

}
