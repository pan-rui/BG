package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.ApiLogDao;
import com.qpp.model.ApiLog;
import com.qpp.model.BaseReturn;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by SZ_it123 on 2014/9/23.
 */
@Controller
@RequestMapping("/common/apiLog")
public class ApiLogAction extends BaseAction {
    @Autowired
    private ApiLogDao apiLogDao;
    private static  String[] pattern = new String[]{"yyyy-MM","yyyyMM","yyyy/MM",
            "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",
            "yyyyMMddHHmmss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss"};

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected BaseReturn getAll(@RequestParam(required = false,defaultValue ="-1") int from,@RequestParam(required = false,defaultValue =" -1") int size){
        List<ApiLog> list=apiLogDao.getAll(new ApiLog(),from,size);
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @RequestMapping(method = RequestMethod.GET,params ={"startDate","endDate"})
    @ResponseBody
    protected BaseReturn getRange(HttpServletRequest request,@RequestParam String startDate,@RequestParam String endDate,
                                  @RequestParam(required = false,defaultValue ="-1") int from,@RequestParam(required = false,defaultValue =" -1") int size){
        Date sDate;
        Date eDate;
        try {
            sDate=DateUtils.parseDate(startDate,pattern);
            eDate=DateUtils.parseDate(endDate,pattern);
        }catch (Exception e){
            BaseReturn baseReturn=new BaseReturn();
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(getMessage(request,"data.inValid",null));
            return baseReturn;
        }
        List<ApiLog> list=apiLogDao.getByDateRange(sDate,eDate,from,size);
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @RequestMapping(method = RequestMethod.GET,value ="{appId}")
    @ResponseBody
    protected BaseReturn getAllById(@PathVariable String appId,
                                    @RequestParam(required = false,defaultValue ="-1") int from,@RequestParam(required = false,defaultValue =" -1") int size){
        List<ApiLog> list=apiLogDao.getByAppId(appId,from,size);
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @RequestMapping(method = RequestMethod.GET,value ="{appId}",params ={"startDate","endDate"})
    @ResponseBody
    protected BaseReturn getRangeById(HttpServletRequest request,@PathVariable String appId,@RequestParam String startDate,@RequestParam String endDate,
                                      @RequestParam(required = false,defaultValue ="-1") int from,@RequestParam(required = false,defaultValue =" -1") int size){
        Date sDate;
        Date eDate;
        try {
            sDate=DateUtils.parseDate(startDate,pattern);
            eDate=DateUtils.parseDate(endDate,pattern);
        }catch (Exception e){
            BaseReturn baseReturn=new BaseReturn();
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(getMessage(request,"data.inValid",null));
            return baseReturn;
        }
        List<ApiLog> list=apiLogDao.getByDateRange(appId,sDate,eDate,from,size);
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }

}
