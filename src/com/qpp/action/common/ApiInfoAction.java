package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.model.ApiInfo;
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
@RequestMapping("/common/apiInfo")
public class ApiInfoAction extends BaseAction {
    @Autowired
    private BaseResourceService baseResourceService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    private BaseReturn newApp(HttpServletRequest request,@RequestBody @Valid ApiInfo tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        return baseResourceService.save(tAppInfo, request);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    private BaseReturn updateApp(HttpServletRequest request,@RequestBody @Valid ApiInfo tAppInfo,BindingResult result){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        return baseResourceService.updateById(ApiInfo.class, tAppInfo.getApiId(), tAppInfo, request);
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAll(HttpServletRequest request,@RequestParam(required = false,defaultValue ="-1") int from,@RequestParam(required = false,defaultValue =" -1") int size){
        return baseResourceService.getAllCache(ApiInfo.class,from,size);
    }

    @RequestMapping(value = "{appCode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getAppInfo(@PathVariable final int appCode,HttpServletRequest request){
        return baseResourceService.getById(ApiInfo.class, appCode, request);
    }
    @RequestMapping(value = "{appCode}",method = RequestMethod.DELETE)
    @ResponseBody
    private BaseReturn delAppInfo(@PathVariable final int appCode,HttpServletRequest request){
        return baseResourceService.deleteById(ApiInfo.class, appCode, request);
    }


}
