package com.qpp.action.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.RoleInfoDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TRoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/common")
public class RoleInfoAction extends BaseAction {
    @Autowired
    private RoleInfoDao roleInfoDao;
    @RequestMapping(value = "role",method = RequestMethod.GET)
    @ResponseBody
    private BaseReturn getRoleInfoList(){
        List<TRoleInfo> list=roleInfoDao.getAll();
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @RequestMapping(value = "role/{roleCode}",method = RequestMethod.GET)//,consumes ="application/json"
    @ResponseBody
    private BaseReturn getRoleInfo(@PathVariable int roleCode, HttpServletRequest request){
        TRoleInfo roleInfo=roleInfoDao.getById(roleCode);
        BaseReturn baseReturn=new BaseReturn();
        if (roleInfo!=null){
            baseReturn.setData(roleInfo);
        }else
            baseReturn.setData(super.getMessage(request,"err.empty",null));
        return baseReturn;
    }

    @RequestMapping(value = "role",method = RequestMethod.POST)//,consumes ="application/json"
    @ResponseBody
    private BaseReturn newRole(HttpServletRequest request,@RequestBody TRoleInfo roleInfo){
        BaseReturn baseReturn=new BaseReturn();
        roleInfoDao.save(roleInfo);
        baseReturn.setData(roleInfo);
        return baseReturn;
    }
    @RequestMapping(value = "role/{roleCode}",method = RequestMethod.DELETE)//,consumes ="application/json"
    @ResponseBody
    private BaseReturn delApp(@PathVariable int roleCode, HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        if (roleCode==0){
            baseReturn.setData(super.getMessage(request,"logic.isNotValid",null));
            return baseReturn;
        }
        TRoleInfo roleInfo=roleInfoDao.getById(roleCode);
        if (roleInfo!=null){
            roleInfoDao.delete(roleInfo);
        }
        baseReturn.setData(super.getMessage(request,"common.success",null));
        return baseReturn;
    }
    @RequestMapping(value = "role/{roleCode}",method = RequestMethod.PUT)//,consumes ="application/json"
    @ResponseBody
    private BaseReturn updateRole(HttpServletRequest request,@PathVariable int roleCode,@RequestBody TRoleInfo roleInfo){
        TRoleInfo roleInfo1=roleInfoDao.getById(roleCode);
        BaseReturn baseReturn=new BaseReturn();
        if (roleInfo1!=null){
            roleInfo.setRole(roleCode);
            roleInfoDao.update(roleInfo);
        }else{
            baseReturn.setResult(102);
            baseReturn.setErrMessage(getMessage(request,"data.inValid",null));
            return baseReturn;
        }
        baseReturn.setData(super.getMessage(request,"common.success",null));
        return baseReturn;
    }

}
