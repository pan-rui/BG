package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.LocInfoDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TLocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by admin on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/common")
public class LocAction extends BaseAction {
    @Autowired
    private LocInfoDao locInfoDao;
    @RequestMapping(value = "location",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateList(){
        List<TLocationType> list=locInfoDao.getAll();
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }

}
