package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.BaseDao;
import com.qpp.dao.CountryDao;
import com.qpp.listener.SpringContextUtil;
import com.qpp.model.BaseReturn;
import com.qpp.model.TCountry;
import com.qpp.service.common.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common")
public class CountryAction extends BaseAction {
    @Autowired
    private CommonDataService commonDataService;
    @RequestMapping(value = "country",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getCountryList(){
        //List<TCountry> list=countryDao.getsByQuery("from TCountry");
        List<TCountry> list=commonDataService.getAllCountry();
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @RequestMapping(value = "country/{countryCode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getCountryById(@PathVariable final String countryCode,HttpServletRequest request){
        //TCountry country=countryDao.getById(countryCode);
        TCountry country=commonDataService.getCountry(countryCode);
        BaseReturn baseReturn=new BaseReturn();
        if (country==null){
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }else{
            baseReturn.setData(country);
        }
        return baseReturn;
    }

}
