package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.model.ApiInfo;
import com.qpp.model.AppRole;
import com.qpp.model.BaseReturn;
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
@RequestMapping("/common/appRole")
public class AppRoleAction extends BaseAction {
    @Autowired
    private BaseResourceService baseResourceService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    private BaseReturn newApp(HttpServletRequest request,@RequestBody @Valid AppRole tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        return baseResourceService.saveOrUpdate(AppRole.class,tAppInfo,tAppInfo,request);
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAll(HttpServletRequest request){
        return baseResourceService.getAll(AppRole.class);
    }

    @RequestMapping(value = "{appCode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAppInfo(@PathVariable final long appCode,HttpServletRequest request){
        return baseResourceService.getByQuery("from AppRole where appid="+appCode, request);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    private BaseReturn delApp(HttpServletRequest request,@RequestBody @Valid AppRole tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        return baseResourceService.deleteById(AppRole.class,tAppInfo,request);
    }


}
