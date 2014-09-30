package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.TLocationType;
import com.qpp.service.common.BaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common")
public class LocAction extends BaseAction {
    @Autowired
    private BaseResourceService baseResourceService;

    @RequestMapping(value = "location",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateList(){
        return baseResourceService.getAllWithCache(TLocationType.class);
    }

}
