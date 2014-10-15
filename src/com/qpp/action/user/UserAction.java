package com.qpp.action.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qpp.action.BaseAction;
import com.qpp.dao.AddressDao;
import com.qpp.dao.CartItemDao;
import com.qpp.dao.MailPublishDao;
import com.qpp.dao.UserRoleDao;
import com.qpp.model.*;
import com.qpp.service.common.BaseResourceService;
import com.qpp.service.common.MemcacheCommonService;
import com.qpp.service.market.MessageInfo;
import com.qpp.service.user.impl.CartJob;
import com.qpp.service.user.impl.UserServiceImpl;
import com.qpp.util.RandomSymbol;
import com.qpp.util.TriggerUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by qpp on 7/31/2014.
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {
    @Resource
    private UserServiceImpl userServiceImpl;
    @Resource
    private BaseResourceService baseResourceService;
    @Resource
    private MemcacheCommonService memcacheCommonService;
    @Resource
    private CartItemDao cartItemDao;
    @Resource
    private AddressDao addressDao;
    @Resource
    private UserRoleDao userRoleDao;
    private int userExpire=18 * 60 * 60000; //用户信息缓存周期

    /**
     * 用户登录
     * @param dataStr
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn checkLogin(String dataStr, HttpServletRequest request) {
//        AppKey appKey = memcacheCommonService.getAppInfo(request);
        String userTokenId = RandomSymbol.getAllSymbol(16);
        Map<String, Object> jsonMap =(Map<String,Object>) JSON.parse(dataStr);
        BaseReturn result = new BaseReturn();
        if (jsonMap==null||jsonMap.size()<1) { //匿名用户
            memcachedClient.set(userTokenId, request.getSession(true), userExpire);
            return new BaseReturn(0, userTokenId);
        } else if (jsonMap.get("nameOrEmail")!=null&&jsonMap.get("password")!=null) //注册用户
            result = userServiceImpl.checkLogin(String.valueOf(jsonMap.get("nameOrEmail")), String.valueOf(jsonMap.get("password")));
        else  //第三方登录
           result= userServiceImpl.otherLogin(jsonMap);
        if (result.getResult() == 0) {
            memcachedClient.set(userTokenId, result.getData(), userExpire);
            loger.info(MessageFormat.format("EventType:User login. DateTime: {0} . Result:Success", formatDateTime(new Date())));
            result.setData(userTokenId);
        }else result.setErrMessage(getMessage(request, result.getErrMessage(), null));
        return result;
//        return new BaseReturn(1, getMessage(request, "login error is Argments inValid.. ", null));
    }

    /**
     * 注销
     * @param tokenId
     * @param request
     * @return
     */
    @RequestMapping(value = "{tokenId}",method = RequestMethod.DELETE)
    public @ResponseBody BaseReturn logout(@PathVariable String tokenId,HttpServletRequest request){
        Object userCache=memcachedClient.get(tokenId);
        if(userCache==null)
            return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        memcachedClient.delete(tokenId);
       return new BaseReturn(1, null);
    }

    /**
     * 验证码验证
     * @param imgCode
     * @param request
     * @return
     */

//    @RequestMapping(value = "imageCheck",method = RequestMethod.GET)
//    @ResponseBody
//    public BaseReturn imageCheck(String imgCode, HttpServletRequest request) {
//        String imCode = String.valueOf(request.getSession().getAttribute("imageCode"));
//        long imgTime = Long.parseLong(String.valueOf(request.getSession().getAttribute("imgTime")));
////        JSONObject jsonObj = JSON.parseObject(imgCode);
////        String imageCode= (String) jsonObj.get("imageCode");
//        BaseReturn result = new BaseReturn();
//        if (!imCode.equalsIgnoreCase(imgCode)) {
//            result.setResult(1);
//            result.setErrMessage(getMessage(request, "err.user.login.imgCheck1", null));
//        } else if(imgTime+5*60*60*1000<new Date().getTime()){
//            result.setResult(1);
//            result.setErrMessage(getMessage(request, "err.user.login.imgCheck2", null));
//        }
//            result.setResult(0);
//        return result;
//    }


    /**
     * 注册验证
     * @param nameOrEmail option: 1.name  2.email
     * @param value
     * @return
     */
//    @RequestMapping(value = "registerCheck/{nameOrEmail}/{value}",method = RequestMethod.GET)
//    @ResponseBody
//    public BaseReturn checkRegister(@PathVariable String nameOrEmail,@PathVariable String value, HttpServletRequest request) {//MessageFormat.format("", )
//        BaseReturn result = userServiceImpl.checkRegister(nameOrEmail, value);
//        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
//        return result;
//    }

    /**
     * 注册
     * @param jsonMap
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BaseReturn register(@RequestBody JSONObject jsonMap, HttpServletRequest request) {
//        AppKey appKey=memcacheCommonService.getAppInfo(request);
        AppKey appKey=new AppKey();appKey.setIsbuildin((short) 1);//TODO:内部用户
//        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonStr);
        try {
            jsonMap.put("birthDay", new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(jsonMap.get("birthDay"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        BaseReturn result=userServiceImpl.register(jsonMap,appKey.getOid()); //TODO:appKey
        BaseReturn result=userServiceImpl.register(jsonMap,appKey);
        if(result.getResult()==0){
            result.setErrMessage(getMessage(request, result.getErrMessage(), null));
            loger.info(MessageFormat.format("EventType: New User Register. . DateTime: {0} . Result:Error",formatDateTime(new Date())));
        }else{
            loger.info(MessageFormat.format("EventType: New User Register. UserId: {0} . DateTime: {1} . Result:Success",((TUser)result.getData()).getOid(),formatDateTime(new Date())));
        }
        return result;
    }

    /**
     * 邮箱验证
     * @param userId
     * @param time
     * @return
     */
    @RequestMapping(value="{userId}",method = RequestMethod.GET,params = "time")
    @ResponseBody
    public BaseReturn registerV(@PathVariable long userId, String time, HttpServletRequest request) {
        BaseReturn result=userServiceImpl.emailActive(userId, time.replaceAll(",",""));
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, "err.user.action.email.failed", null));
        if(result.getResult()==1)
            loger.info(MessageFormat.format("EventType:Registry is Verify .DateTime: {0} Result:Success ", formatDateTime(new Date())));
        else
            loger.info(MessageFormat.format("EventType:Registry is Verify .DateTime: {0} Result:Error ", formatDateTime(new Date())));
        return result;
    }

    /**
     * 忘记密码,也可由JSP直接跳转
     * @param name
     * @param map
     * @return
     */
//    @RequestMapping("/user/forgetPass.hyml")
//    public String forgetPass(String name,ModelMap map) {
//        map.addAttribute("name", name);
//        return "/user/rePassword";
//    }

    /**
     * 重设密码处理
     * @param name
     * @param email
     * @param request
     * @return
     */
@RequestMapping(method = RequestMethod.GET,params = "{name,email}")
@ResponseBody
    public BaseReturn rePass(String name,String email,HttpServletRequest request) {
    BaseReturn result = userServiceImpl.rePassword(name, email);
    if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
    return result;
}

    /**
     * 重设密码 from 邮箱链接
     * @param email
     * @param time
     * @return
     */
//    @RequestMapping("/user/rePassV.hyml")
//    @ResponseBody
//    public String rePassV(String email,String time,ModelMap map){
//        int interval=2*24*3600*1000;
//        boolean valid=new Date().getTime()-Integer.parseInt(time)<interval;
//        if(valid) {
//            map.addAttribute("email", email);
//            return "user/rePassV";
//        }else
//            return "user/error";
//    }

    /**
     * 重设提交
     * @param jsonMap
     * @return
     */
    @RequestMapping(value = "rePass",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn rePassM(@RequestBody JSONObject jsonMap, HttpServletRequest request) {
//        JSONObject obj = JSON.parseObject(jsonStr);
        BaseReturn result = userServiceImpl.rePasswordd(String.valueOf(jsonMap.get("email")), String.valueOf(jsonMap.get("password")), String.valueOf(jsonMap.get("time")).replaceAll(",", ""));//TODO:currentPassword->
        if (result.getResult() == 0) {
            loger.info(MessageFormat.format("EventType: ReplacePassWord . DateTime: {0}.Result:Success", formatDateTime(new Date())));
        } else {
            result.setErrMessage(getMessage(request, result.getErrMessage(), null));
            loger.info(MessageFormat.format("EventType: ReplacePassWord . DateTime: {0}.Result:Error", formatDateTime(new Date())));
        }
        return result;
    }

    /**
     * 查询用户
     * @param
     * @return
     */
//    @RequestMapping(value = "findUser/{userId}",method = RequestMethod.GET)
//    @ResponseBody
//    public BaseReturn queryUser(@RequestBody PageModel<TUser> page,@PathVariable String userId, HttpServletRequest request) {
//        BaseReturn result = new BaseReturn();
////        TUser user = (TUser) request.getSession().getAttribute("user");
//        TUser user = (TUser) memcachedClient.get(userId);
//        if (user==null||!user.getType().equals("c")) {
//             result.setResult(0);
//            result.setErrMessage(getMessage(request, "err.user.findUser.authorized", null));
////            map.addAttribute(result);
////            return "/user/userAuthErr";
//            return result;
//        }
//        if (page == null)
//            page = new PageModel<TUser>();
////        if ((page.getSearchValue() == null || page.getSearchValue().toString().trim().equals("")) && (page.getOrderPropery() == null || page.getOrderPropery().equals("")) && memcachedClient.keyExists(user.getId()+"_"+page.getPageNo())) {
////              //从缓存取出.......
////        }
//        result = userServiceImpl.queryUser(page);
////        map.addAttribute(result.getData());
//        //保存在缓存
////        if ((page.getSearchValue() == null || page.getSearchValue().toString().trim().equals("")) && (page.getOrderPropery() == null || page.getOrderPropery().equals("")))
////            memcachedClient.set(user.getId()+"_"+page.getPageNo(), page.getData(), 5 * 3600 * 1000);
//        return result;
//    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn queryUser(String searchProperty,String searchValue,String orderProperty,boolean isDesc,@RequestParam(required = false,defaultValue = "0") int from,@RequestParam(required = false,defaultValue = "1000000000") int size,String tokenId, HttpServletRequest request) {
        AppKey appKey = memcacheCommonService.getAppInfo(request);
        BaseReturn result = new BaseReturn();

//        Object userCache = memcachedClient.get(tokenId);
//        if (userCache == null || userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
//        TUser user = (TUser) userCache;
////        if (user == null || appKey.getOid()!=1000l) { //TODO:查询用户列表权限
//        Long appId = Long.parseLong(userServiceImpl.exists(cartItemDao, "select appId from t_availableApp where userId='" + user.getOid() + "'"));
//        if (appId!=1000l) {
//            result.setResult(0);
//            result.setErrMessage(getMessage(request, "err.user.findUser.authorized", null));
//            return result;
//        }
        result = userServiceImpl.queryUser(searchProperty, searchValue, orderProperty, isDesc, from, size);
        return result;
    }

    @RequestMapping(value = "{userId}",method = RequestMethod.GET)
    public @ResponseBody BaseReturn findUser(@PathVariable long userId, HttpServletRequest request) {
//        Object obj=memcachedClient.get(userId);
//        TUser user=null;
//        if(obj==null || obj instanceof HttpSession)
//            return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
//
//        user = userServiceImpl.getById(((TUser)obj).getOid());
////            user=userServiceImpl.getById(((TOpenidInfo)obj).getUserId());
//        return new BaseReturn(0, user);
        return baseResourceService.getById(TUser.class, userId, request);
    }

    /**
     * 更新用户资料
     * @param jsonMap
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn updateUser(@RequestBody Map jsonMap, HttpServletRequest request) {
//        AppKey appKey = memcacheCommonService.getAppInfo(request);
        AppKey appKey=new AppKey();appKey.setIsbuildin((short)1); //TODO:内部用户
//        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonStr);
            String tokenId = (String) jsonMap.remove("tokenId");
            if (appKey.getIsbuildin()!=1&&(tokenId == null || memcachedClient.get(tokenId) instanceof HttpSession))
                return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        TUser obj = (TUser) memcachedClient.get(tokenId)==null?new TUser():(TUser)memcachedClient.get(tokenId);
        try {
            jsonMap.put("utime", new Date());
            jsonMap.remove("password");
           if(appKey.getIsbuildin()!=1) jsonMap.remove("email"); //TODO:字段过滤.....
            if (jsonMap.containsKey("name") && obj.getRegisterType() != 20)
                jsonMap.remove("name");
            if(jsonMap.containsKey("birthDay")) jsonMap.put("birthDay",new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(jsonMap.get("birthDay"))));
                BeanUtils.populate(obj, jsonMap); //TODO:验证...
            Set<ConstraintViolation<TUser>> constraints = userServiceImpl.validator.validate(obj);
            for (ConstraintViolation<TUser> constraintViolation : constraints)
                return new BaseReturn(getMessage(request, constraintViolation.getMessageTemplate(), null), 1);
            jsonMap=new LinkedHashMap<String,Object>(jsonMap);
            jsonMap.put("oid", jsonMap.remove("oid"));
//            memcachedClient.set(tokenId, obj, userExpire);
            userServiceImpl.update("t_user", jsonMap);
//            BaseReturn result = userServiceImpl.updateUser(user);
//            if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
            return new BaseReturn(0, obj);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseReturn(getMessage(request, "err.parameter.inValid", new Object[]{jsonMap}), 1);
        }
    }

    /**
     * 兑换积分
     * @param tokenId
     * @param gift
     * @param count
     * @return
     */
    @RequestMapping(value = "convert-{count}/{tokenId}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn scoreProcess(@PathVariable String tokenId,@RequestBody TGift gift,@PathVariable int count,HttpServletRequest request) {
        Object obj = memcachedClient.get(tokenId);
        if(obj==null||obj instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
          TUser user= (TUser) obj;
        BaseReturn result = userServiceImpl.scoreProcess(user, gift, count);
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
//        map.addAttribute(result);
        return result;
    }

    /**
     * 修改密码
     * @param tokenId
     * @param jsonMap
     * @return
     */
    @RequestMapping(value = "{tokenId}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn modifyPass(@PathVariable String tokenId,@RequestBody JSONObject jsonMap, HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null || userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        TUser user=(TUser)userCache;
        BaseReturn result = new BaseReturn();
//        TUser user = userServiceImpl.getById(userId);
//        JSONObject obj = JSON.parseObject(jsonStr);
//        if (memcachedClient.get(String.valueOf(userId)) == null || user == null) {
//            result.setResult(1);
//            result.setErrMessage(getMessage(request, "err.user.modify.Pass.noLogin", null));
//            return result;
//        } else
            result = userServiceImpl.rePass(user, jsonMap.getString("oldPass"), jsonMap.getString("newPass"));
        if (result.getResult() == 0)
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {0} . DateTime: {1}.Result:Success", user.getOid(), formatDateTime(new Date())));
        else {
            result.setErrMessage(getMessage(request, result.getErrMessage(), null));
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {0} . DateTime: {1}.Result:Error", user.getOid(), formatDateTime(new Date())));
        }
//        map.addAttribute(result);
        return result;
    }

    /**
     * 添加到购物车
     * @param tokenId
     * @param cartItem
     * @return
     */
    @RequestMapping(value = "cartAdd/{tokenId}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn addCart(@PathVariable String tokenId,@RequestBody TCartItem cartItem,HttpServletRequest request) {
        Object userCache = (TUser) memcachedClient.get(tokenId);
        if (userCache==null) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
            BaseReturn result = new BaseReturn();
//        TUser user=(TUser)userCache;
        List<TCartItem> cartList = new ArrayList<TCartItem>();
//        cartItem.setId(RandomSymbol.getAllSymbol(16));
        cartItem.setCtime(new Date());
        cartList.add(cartItem);
        if (!(userCache instanceof HttpSession)) {
            cartItem.setUserId(((TUser) userCache).getOid());
//        request.getSession().setAttribute("cartList", cartList);
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("dao", cartItemDao);
            dataMap.put("mem", memcachedClient);
            dataMap.put("key", tokenId + "_cart");
//            dataMap.put("action", "add");
            TriggerUtil.simpleTask(dataMap, CartJob.class, new Date(System.currentTimeMillis() + 16 * 60 * 60 * 1000));
        }
            result.setResult(0);
            result.setData(cartList);
            memcachedClient.set(tokenId + "_cart", cartList, 16 * 60 * 60 * 1000 + 90000);
            return result;
    }

    /**
     * 查看购物车,
     * @param tokenId
     * @return
     */
    @RequestMapping(value = "cart",method = RequestMethod.GET,params = "tokenId")
    @ResponseBody
    public BaseReturn readCart(String tokenId) {
        List<TCartItem> cartList = (List<TCartItem>) memcachedClient.get(tokenId + "_cart");
        BaseReturn result = new BaseReturn();
        result.setResult(0);
        result.setData(cartList);
        return result;
    }

    /**
     * 购物车修改
     * @param tokenId
     * @param cartItem 购物车商品条目
     * @param action  动作 option (1.delete  2.modify)
     * @return
     */
    @RequestMapping(value = "cart/{action}/{tokenId}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn cartModify(@PathVariable String tokenId,@RequestBody TCartItem cartItem,@PathVariable String action) {
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = (List<TCartItem>) memcachedClient.get(tokenId+"_cart");
        List<TCartItem> cartChange = new ArrayList<TCartItem>();
        for (int i = 0; i < cartList.size(); i++) {
            if (String.valueOf(cartList.get(i).getId()).equals(cartItem.getId())) {
                if ("modify".equals(action))
                    cartList.set(i, cartItem);
                else
                    cartChange.add(cartList.remove(i));
            }
        }
        Object userCache=memcachedClient.get(tokenId);
        if (!(userCache instanceof HttpSession)) {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("dao", cartItemDao);
            dataMap.put("mem", memcachedClient);
            dataMap.put("key", tokenId + "_cart");
            dataMap.put("action",cartChange);
            TriggerUtil.simpleTask(dataMap, CartJob.class, new Date(System.currentTimeMillis() + 16 * 60 * 60 * 1000));
        }
        memcachedClient.set(tokenId+"_cart",cartList,16*60*60*1000+90000);
        result.setResult(0);
        result.setData(cartList);
        return result;
    }

    /**
     * 设置用户状态
     * @param tokenId
     * @param status 状态值:1 激活,0 未激活 3 黑名单
     * @return
     */
    @RequestMapping(value = "{status}/{tokenId}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn setUserStatus(@PathVariable String tokenId,@PathVariable String status,HttpServletRequest request) {
        Object userCache=memcachedClient.get(tokenId);
        if(userCache==null ||userCache instanceof HttpSession)
            return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        if (!"".equals(status)){
            BaseReturn result= userServiceImpl.setUserStatus(((TUser)userCache).getOid(), status);
            if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
            return result;
        }
        return new BaseReturn(getMessage(request,"err.user.set.status.notNull",null),1);
    }

    /**
     * 设置用户类型
     * @param userId
     * @param type
     * @return
     */
//@RequestMapping(value = "type-{type}",method = RequestMethod.PUT)
//@ResponseBody
//    public BaseReturn setUserType(@PathVariable int userId,@PathVariable String type,HttpServletRequest request) {
//    if(memcachedClient.get(String.valueOf(userId))==null)
//        return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
//        if (!"".equals(type)){
//            BaseReturn result= userServiceImpl.setUserType(userId, type);
//            if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
//            return result;
//        }
//        return new BaseReturn(getMessage(request,"err.user.set.type.notNull",null),0);
//    }

    /**
     * 用户地址增加
     * @param tokenId
     * @param address
     * @return
     */
@RequestMapping(value = "addr/{tokenId}",method = RequestMethod.POST)
@ResponseBody
    public BaseReturn addAddr(@PathVariable String tokenId,@RequestBody TAddress address,HttpServletRequest request) {
    Object userCache = memcachedClient.get(tokenId);
    if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
//    if (address.size() > 0) {
//        BaseReturn result = userServiceImpl.setUserAddr(((TUser)userCache).getOid(), JSON.toJSONString(address));
    if(address!=null)
       return userServiceImpl.addAddr(address);
//        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
    return new BaseReturn(getMessage(request, "err.user.set.address.notNull", null), 1);
}

    /**
     * 用户地址修改
     * @param tokenId
     * @param address
     * @return
     */
    @RequestMapping(value = "addr/{tokenId}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn setUserAddr(@PathVariable String tokenId,@RequestBody TAddress address,HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        if(address!=null)
            return userServiceImpl.updateAddr(address);
        return new BaseReturn(getMessage(request, "err.user.set.address.notNull", null), 1);
    }

    /**
     * 用户地址查询
     * @param tokenId
     * @param userId
     * @return
     */
    @RequestMapping(value = "addr/{tokenId}",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn queryAddr(@PathVariable String tokenId,Long userId,HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        if(userId!=null)
            return userServiceImpl.queryAddr(userId);
        return new BaseReturn(getMessage(request, "err.user.set.address.notNull", null), 1);
    }

    /**
     * 用户默认地址查询
     * @param tokenId
     * @param userId
     * @return
     */
    @RequestMapping(value = "dAddr/{tokenId}",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn getDefaultAddr(@PathVariable String tokenId,Long userId,HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        if(userId!=null) {
            TUser user=userServiceImpl.getById(userId);
            return new BaseReturn(0,addressDao.getById(user.getDefaultAddr()));
        }
        return new BaseReturn(getMessage(request, "err.user.set.address.notNull", null), 1);
    }

    /**
     * 用户地址删除
     * @param tokenId
     * @param address
     * @return
     */
    @RequestMapping(value = "addr/{tokenId}",method = RequestMethod.DELETE)
    @ResponseBody
    public BaseReturn delAddr(@PathVariable String tokenId,Long address,HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        if(address!=null)
            return userServiceImpl.delAddr(address);
        return new BaseReturn(getMessage(request, "err.user.set.address.notNull", null), 1);
    }

    /**
     * 添加或修改邮件订阅
     * @param request eg:{"subject":"活动通知1","content":"xxxxxxdfdfs","sendDate":"2014-08-29 15:15","isPublish":"1"}
     * @return
     */
    @RequestMapping(value = "addEmail/{tokenId}",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturn addEmail(@PathVariable String tokenId,HttpServletRequest request) {
        AppKey appKey = memcacheCommonService.getAppInfo(request);
        TMailPublish mailPublish = null;
        JSONObject json = new JSONObject();
        Object userCache = memcachedClient.get(tokenId); //TODO:非匿名用户
        if (userCache == null || userCache instanceof HttpSession || !"1000".equals(appKey.getOid())) //TODO:用户类型待定
            return new BaseReturn(getMessage(request, "err.user.email.add.authorized", null), 1);
        File file = null;
        int maxFileSize = Integer.parseInt(MessageInfo.getMessage("upload.File.MaxSize"));
        int maxMemSize = Integer.parseInt(MessageInfo.getMessage("upload.Mem.MaxSize"));
        String filePath = "d:/upload/email/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String contentType = request.getContentType();
        if (contentType.indexOf("multipart/form-data") >= 0) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置内存中存储文件的最大值
            factory.setSizeThreshold(maxMemSize);
            //本地存储的数据大于MaxMemSize时
            factory.setRepository(new File("C:\\temp"));
            //创建一个文件上传处理程序
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置上传的文件最大值
            upload.setFileSizeMax(maxFileSize);
            upload.setHeaderEncoding("UTF-8");
            try {
                List fileItems = upload.parseRequest(request);
                Iterator it = fileItems.iterator();
                while (it.hasNext()) {
                    FileItem fi = (FileItem) it.next();
                    if (!fi.isFormField()) {
                        //获取上传文件的参数
                        String filedName = fi.getFieldName();
                        String fileName = fi.getName();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                        //写入文件
//                        if(fileName.lastIndexOf("\\")>=0)
                        file = new File(filePath, fileName.substring(fileName.lastIndexOf("\\") + 1));
//                        else
//                            file = new File(filePath, filedName);
                        if (!file.getParentFile().exists())
                            file.getParentFile().mkdirs();
                        fi.write(file);
                        System.out.println("上传的邮件附件文件名:" + filePath + fileName);
                    } else {
                        if (fi.getFieldName().contains("Date"))
                            json.put(fi.getFieldName(), new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(URLDecoder.decode(fi.getString(), request.getHeader("Content-Encoding"))));//TODO:请求时设置编码
                        else
                            json.put(fi.getFieldName(), URLDecoder.decode(fi.getString(), request.getHeader("Content-Encoding")));
                    }
                }
                mailPublish = JSON.toJavaObject(json, TMailPublish.class);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mailPublish.setAttachment(file.getAbsolutePath());
        BaseReturn result = userServiceImpl.addEmail(mailPublish);
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
        return result;
    }

    /**
     * 用户订阅邮件
     * @param tokenId
     * @param emailId
     * @return
     */
    @RequestMapping(value = "subscription",method = RequestMethod.GET)
    public @ResponseBody BaseReturn subscription(String tokenId,long emailId,HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        BaseReturn result=userServiceImpl.subscription(((TUser)userCache).getOid(),emailId);
        if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
        return result;
    }

    /**
     * 用户取消订阅
     * @param tokenId
     * @param emailId
     * @return
     */
@RequestMapping(value = "cancelSubscript",method = RequestMethod.DELETE)
@ResponseBody
    public BaseReturn cancelSubscript( String tokenId, long emailId,HttpServletRequest request) {
    Object userCache = memcachedClient.get(tokenId);
    if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        return userServiceImpl.cancelSubscript(((TUser)userCache).getOid(),emailId);
    }

    /**
     * 用户查看订阅邮件
     * @param page
     * @param tokenId
     * @return
     */
@RequestMapping(value = "queryEmail",method = RequestMethod.GET)
@ResponseBody
    public BaseReturn queryEmail(PageModel<TMailPublish> page,String tokenId,HttpServletRequest request) {
    AppKey appKey = memcacheCommonService.getAppInfo(request);
    Object userCache = memcachedClient.get(tokenId);
    if (userCache == null || userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
    BaseReturn result = new BaseReturn();
    TUser user = (TUser) userCache;
    if (page == null)
        page = new PageModel<TMailPublish>();
    result = userServiceImpl.queryEmail(page, user,appKey.getOid());
    if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
    return result;
}

    /**
     * 删除用户
     * @param dataStr
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody BaseReturn delete(String dataStr,HttpServletRequest request) {
//        AppKey appKey = memcacheCommonService.getAppInfo(request);
        AppKey appKey=new AppKey();appKey.setIsbuildin((short)1); //TODO:内部用户
//        if(appKey.getOid()!=1000l) return new BaseReturn(getMessage(request, "err.user.delete.authorized", null), 1); //TODO:删除权限
        Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(dataStr);
        String tokenId = (String) dataMap.remove("tokenId");
        Object userCache = memcachedClient.get(tokenId);
        if(appKey.getIsbuildin()!=1&&(userCache==null||userCache instanceof HttpSession)) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        List<Object> userList = (List<Object>) dataMap.get("users");
        if(userList!=null&&userList.size()>0)
       return userServiceImpl.delete(userList);
        return new BaseReturn(getMessage(request, "err.parameter.inValid", new Object[]{dataStr}), 1);
    }

    /**
     * 检查访问权限
     * @param request
     * @param tokenId
     * @param dataAuth
     * @return
     */
    public BaseReturn checkAuth(HttpServletRequest request,String tokenId,Map<AppKey,List<TRole>> dataAuth) {
        AppKey appKey = memcacheCommonService.getAppInfo(request);  //可读取注解判断权限级别
        if(!dataAuth.containsKey(appKey)) return new BaseReturn(getMessage(request, "err.user.authorized", null), 1);
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
        List<TRole> roleList = dataAuth.get(appKey);
        List<Object> userRole =new MailPublishDao().queryAddr("select roleId from t_userRole where userId='" + ((TUser) userCache).getOid() + "'","roleId");
        for (TRole role : roleList) {
            if(userRole.contains(role.getOid()))
                return new BaseReturn(0, null);
        }
        return new BaseReturn("err.user.authorized", 1);
    }

    /**
     * 设置默认地址
     * @param tokenId
     * @param jsonMap
     * @param request
     * @return
     */
    @RequestMapping(value = "addrUp/{tokenId}", method = RequestMethod.PUT)
    public @ResponseBody BaseReturn setAddr(@PathVariable String tokenId, @RequestBody JSONObject jsonMap, HttpServletRequest request) {
        Object userCache = memcachedClient.get(tokenId);
        if(userCache==null||userCache instanceof HttpSession) return new BaseReturn(getMessage(request, "err.user.noLogin", null), 1);
//        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("defaultAddr", jsonMap.get("addrId"));
        dataMap.put("oid",((TUser)userCache).getOid());
        userServiceImpl.update("t_user", dataMap);
        return new BaseReturn(1, getMessage(request, "common.success", null));
    }
}
