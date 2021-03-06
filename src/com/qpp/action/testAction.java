package com.qpp.action;

import com.qpp.dao.AppInfoDao;
import com.qpp.dao.AppRightDao;
import com.qpp.form.People;
import com.qpp.form.SessionModel;
import com.qpp.model.AppKey;
import com.qpp.model.BaseReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gary,
 * Date: 12-7-27
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class testAction extends BaseAction{
    private People pp;
    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private AppRightDao appRightDao;
    public People getPp() {
        return pp;
    }

    public void setPp(People pp) {
        this.pp = pp;
    }
    @RequestMapping(value = "/test/input")
    public String input(@Valid People pp,BindingResult result,HttpServletRequest request,Map map){
        if (result.hasErrors()){
            loger.info("Error occured in input.hyml");
        }
        loger.info("Test uid:"+request.getSession().getAttribute(SessionModel.UserId));
        map.put("rep","replicate");
        return "/test/input";
    }
    @RequestMapping(value = "/test/inputModel")
    public ModelAndView inputModel(@Valid People people,BindingResult result,HttpServletRequest request){
        result.rejectValue("name","user.username");
        if (result.hasErrors()){
            loger.info("Error occuredin input.hyml");
        }
        Map map=new HashMap();
        map.put("rep","replicate");
        return new ModelAndView("/test/inputModel",map);
    }
    @RequestMapping(value = "apptest/{appCode}",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn newApp(@PathVariable final String appCode,HttpServletRequest request){
        //commonDataService.testAc(appCode);
        return new BaseReturn();
    }
}
