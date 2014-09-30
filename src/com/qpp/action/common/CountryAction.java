package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.TCountry;
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
public class CountryAction extends BaseAction {
    @Autowired
    private BaseResourceService baseResourceService;
    @RequestMapping(value = "country",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getCountryList(){
        return baseResourceService.getAllWithCache(TCountry.class);
    }
    @RequestMapping(value = "country/{countryCode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getCountryById(@PathVariable final String countryCode,HttpServletRequest request){
        return baseResourceService.getByIdWithCache(TCountry.class, countryCode, request);
    }

}
