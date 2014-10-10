package com.qpp.action.user;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.TUserRole;
import com.qpp.service.common.BaseResourceService;
import com.qpp.service.user.impl.UserRoleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by qpp on 10/8/2014.
 */
@Controller
 @RequestMapping("/userRole")
public class UserRoleAction extends BaseAction {

    @Resource
    private BaseResourceService baseResourceService;
    @Resource
    private UserRoleServiceImpl userRoleServiceImpl;

    @RequestMapping(value = "{userRoleId}",method = RequestMethod.GET)
    public @ResponseBody BaseReturn getUserRole(@PathVariable long userRoleId,HttpServletRequest request){
      return  baseResourceService.getById(TUserRole.class,userRoleId,request);
    }
    @RequestMapping(method = RequestMethod.GET,params = "userId")
    public @ResponseBody BaseReturn getAllUserRole(long userId,HttpServletRequest request) {
        return baseResourceService.getByQuery("from TUserRole where userId='" + userId + "'", request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody BaseReturn getAll(HttpServletRequest request) {
        return baseResourceService.getAllCache(TUserRole.class, 0, 1000000000);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody BaseReturn addUserRole(@RequestBody @Valid TUserRole userRole,BindingResult result,HttpServletRequest request){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        return baseResourceService.save(userRole, request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody BaseReturn updateUserRole(@RequestBody @Valid TUserRole userRole,BindingResult result,HttpServletRequest request){
        if (result.hasErrors()) return baseResourceService.getFormatError(result);
        userRoleServiceImpl.updateAuth(userRole);
        return new BaseReturn(1, getMessage(request, "common.success", null));
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody BaseReturn delUserRole(long userRoleId,HttpServletRequest request){
        return baseResourceService.deleteById(TUserRole.class, userRoleId, request);
    }
}
