package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.TState;
import com.qpp.service.common.BaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common")
public class StateAction extends BaseAction {
    @Autowired
    private BaseResourceService baseResourceService;
    @RequestMapping(value = "state",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateList(){
        return baseResourceService.getAllWithCache(TState.class);
    }
    @RequestMapping(value = "state/{countryCode}/{statecode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateById(@PathVariable final String countryCode,@PathVariable final String statecode,HttpServletRequest request){
        TState tState=new TState();
        tState.setCountryCode(countryCode);
        tState.setStateCode(statecode);
        return baseResourceService.getByIdWithCacheKey(TState.class, tState, request,"state-"+countryCode + "-" + statecode);
    }

}
