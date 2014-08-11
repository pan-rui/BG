package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.ShippingInfoDao;
import com.qpp.dao.StateDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TShippinginfo;
import com.qpp.model.TState;
import com.qpp.model.TStatePK;
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
public class ShippingAction extends BaseAction {
    @Autowired
    private ShippingInfoDao shippingInfoDao;
    @RequestMapping(value = "shipping",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getStateList(){
        List<TShippinginfo> list=shippingInfoDao.getsByQuery("from TShippinginfo");
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }

}
