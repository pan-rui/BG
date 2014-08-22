package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.StateDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TState;
import com.qpp.service.common.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common")
public class StateAction extends BaseAction {
    @Autowired
    private CommonDataService commonDataService;
    @Autowired
    private StateDao stateDao;
    @RequestMapping(value = "state",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateList(){
        List<TState> list=commonDataService.getAlState();
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @RequestMapping(value = "state/{countryCode}/{statecode}",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateById(@PathVariable final String countryCode,@PathVariable final String statecode,HttpServletRequest request){
        TState tStatePK=new TState();
        tStatePK.setCountryCode(countryCode);
        tStatePK.setStateCode(statecode);
        TState tState=commonDataService.getState(tStatePK);
        //TState tState=stateDao.getById(tStatePK);
        BaseReturn baseReturn=new BaseReturn();
        if (tState==null){
            baseReturn.setResult(1);
            baseReturn.setErrMessage(getMessage(request,"err.empty",null));
        }else{
            baseReturn.setData(tState);
        }
        return baseReturn;
    }

}
