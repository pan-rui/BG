package com.qpp.action.user;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.TRole;
import com.qpp.service.common.BaseResourceService;
import com.qpp.service.user.impl.RoleServiceImpl;
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
 * Created by qpp on 10/9/2014.
 */
@Controller
@RequestMapping("/role")
public class RoleAction extends BaseAction {
    @Resource
    private BaseResourceService baseResourceService;
    @Resource
    private RoleServiceImpl roleServiceImpl;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody BaseReturn addRole(@RequestBody @Valid TRole role,BindingResult result,HttpServletRequest request){
        if(result.hasErrors()) return baseResourceService.getFormatError(result);
       return baseResourceService.save(role, request);
    }

    @RequestMapping(method =RequestMethod.GET)
    public @ResponseBody BaseReturn getRole(HttpServletRequest request) {
      return   baseResourceService.getAllCache(TRole.class, 0, 1000000000);
    }

    @RequestMapping(value = "{roleId}", method = RequestMethod.GET)
    public @ResponseBody BaseReturn getRoleForId(@PathVariable long roleId, HttpServletRequest request) {
        return baseResourceService.getById(TRole.class, roleId, request);
    }

    @RequestMapping(method = RequestMethod.GET,params = "appId")
    public @ResponseBody BaseReturn getRolesForAppId(String appId,HttpServletRequest request) {
        return baseResourceService.getByQuery("from TRole where appId='" + appId + "'", request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody BaseReturn updateRole(@RequestBody @Valid TRole role, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()) return baseResourceService.getFormatError(result);
        roleServiceImpl.update(role);
        return new BaseReturn(1,getMessage(request, "common.success", null)) ;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody BaseReturn deleteRole(long roleId,HttpServletRequest request) {
        return baseResourceService.deleteById(TRole.class,roleId, request);
    }
}
