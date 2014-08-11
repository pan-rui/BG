package com.qpp.action.user;

import com.qpp.action.BaseAction;
import com.qpp.model.*;
import com.qpp.service.market.impl.UserServiceImpl;
import com.qpp.util.RandomSymbol;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qpp on 7/31/2014.
 */
@Controller
public class UserAction extends BaseAction {
    @Resource
    private UserServiceImpl userServiceImpl;

//    @Resource
//    private ResourceBundleMessageSource messageSource;


    /**
     * 用户登录(Ajax或form)
     * @param nameOrEmail
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/user/login.hyml")
    @ResponseBody
    public BaseReturn checkLogin(@RequestParam(value = "nameOrEmail") String nameOrEmail, String password, HttpServletRequest request) {
//        BaseReturn result = new BaseReturn();
//        String imCode = String.valueOf(request.getSession().getAttribute("imageCode"));
//        if (!imCode.equalsIgnoreCase(imageCode)) {   //Ajax验证
//            result.setResult(0);
//            result.setErrMessage("验证码不正确");
//        }
        BaseReturn result = userServiceImpl.checkLogin(nameOrEmail, password);
        if (result.getResult() == 1) {
            request.getSession().setAttribute("user", result.getData());
            memcachedClient.set(((TUser)result.getData()).getId(),result.getData(),18*60*60000);
            loger.info(MessageFormat.format("EventType:User login. DateTime: {} . Result:Success",formatDateTime(new Date())));
        }
//        if (!request.getHeader("Referer").contains("/user")) {
//            rMap.addAttribute("return", result);
//            return "redirect:/user/ajaxLogin.hyml";
//        } else
//            return "user/userInfo";
        return result;
    }

    /**
     * 验证码验证
     * @param imageCode
     * @param request
     * @return
     */
    @RequestMapping("/user/imageCheck.hyml")
    @ResponseBody
    public BaseReturn imageCheck(String imageCode,long timeStr, HttpServletRequest request) {
        String imCode = String.valueOf(request.getParameter("imageCode"));
        BaseReturn result = new BaseReturn();
        if (!imCode.equalsIgnoreCase(imageCode)) {
            result.setResult(0);
            result.setErrMessage("验证码输入错误,请重新输入!");
        } else if(timeStr+5*60*60*1000<new Date().getTime()){
            result.setResult(0);
            result.setErrMessage("验证码已过期,请重新输入!");
        }
            result.setResult(1);
        return result;
    }

    /**
     * Ajax登录(跳入)
     * @param request
     * @return
     */
//    @RequestMapping("/user/ajaxLogin.hyml")
//    @ResponseBody
//    public BaseReturn ajaxLogin(HttpServletRequest request) {
//        return (BaseReturn) request.getAttribute("return");
////        return (BaseReturn)rMap.get("return");
//    }

    /**
     * 注册验证
     * @param nameOrEmail option: 1.name  2.email
     * @param value
     * @return
     */
    @RequestMapping("/user/registerCheck.hyml")
    @ResponseBody
    public BaseReturn checkRegister(String nameOrEmail, String value) {
        return userServiceImpl.checkRegister(nameOrEmail, value);
    }

    /**
     * 注册入库
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/user/register.hyml")
    @ResponseBody
    public BaseReturn register(TUser user,HttpServletRequest request) {
        BaseReturn result=userServiceImpl.register(user);
//            map.addAttribute(result);
//        TUser tuser= (TUser)result.getData();
        if(result.getResult()==1){
            loger.info(MessageFormat.format("EventType: New User Register. UserId: {} . DateTime: {} . Result:Success",((TUser)result.getData()).getId(),formatDateTime(new Date())));
//            return "/user/userRegisert";
        }else{
            result.setErrMessage("注册失败.........");
//           return "/user/error";
            loger.info(MessageFormat.format("EventType: New User Register. . DateTime: {} . Result:Error",formatDateTime(new Date())));
        }
        return result;
    }

    /**
     * 邮箱验证
     * @param userId
     * @param time
     * @return
     */
    @RequestMapping("/user/registerV.hyml")
    @ResponseBody
    public BaseReturn registerV(String userId,String time){
        BaseReturn result=userServiceImpl.emailActive(userId, time);
        if(result.getResult()==1)
            loger.info(MessageFormat.format("EventType:Registry is Verify .DateTime: {} Result:Success ",formatDateTime(new Date())));
//            return "/user/loginV";
        else{
            loger.info(MessageFormat.format("EventType:Registry is Verify .DateTime: {} Result:Error ", formatDateTime(new Date())));
//            rmap.addAttribute("message",result.getErrMessage());
//            return "/user/error";
        }
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
@RequestMapping("/user/rePass.hyml")
@ResponseBody
    public BaseReturn rePass(String name,String email,HttpServletRequest request) {
    BaseReturn result = userServiceImpl.rePassword(name, email);
//    if(result.getResult()==0) {
//        loger.info(MessageFormat.format("EventType: ReplacePassWord . DateTime: {}.Result:Error", formatDateTime(new Date())));
//        request.setAttribute("message", result.getErrMessage());
//        return "user/error";
//    }else
//        loger.info(MessageFormat.format("EventType: ReplacePassWord . DateTime: {}.Result:Success", formatDateTime(new Date())));
//    return "user/rePass";
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
     * @param email
     * @param password
     * @param time
     * @return
     */
    @RequestMapping("/user/rePassCommit.hyml")
    @ResponseBody
    public BaseReturn rePassM(String email,String password,String time) {
        BaseReturn result = userServiceImpl.rePasswordd(email, password,time);
        if(result.getResult()==1) {
//            map.addAttribute("message","重设密码成功!");
            loger.info(MessageFormat.format("EventType: ReplacePassWord . DateTime: {}.Result:Success", formatDateTime(new Date())));
        }else{
            loger.info(MessageFormat.format("EventType: ReplacePassWord . DateTime: {}.Result:Error", formatDateTime(new Date())));
//            map.addAttribute("message","重设密码失败!");
        }
//        return "user/rePassOK";
        return result;
    }

    /**
     * 查询用户
     * @param page
     * @return
     */
    @RequestMapping("/user/findUser.hyml")
    @ResponseBody
    public BaseReturn queryUser(PageModel<TUser> page,HttpServletRequest request) {
        BaseReturn result = new BaseReturn();
        TUser user = (TUser) request.getSession().getAttribute("user");
        if (!user.getType().equals("c")) {
             result.setResult(0);
            result.setErrMessage("您没有此权限.....");
//            map.addAttribute(result);
//            return "/user/userAuthErr";
        }
        if (page == null)
            page = new PageModel<TUser>();
//        if ((page.getSearchValue() == null || page.getSearchValue().toString().trim().equals("")) && (page.getOrderPropery() == null || page.getOrderPropery().equals("")) && memcachedClient.keyExists(user.getId()+"_"+page.getPageNo())) {
//              //从缓存取出.......
//        }
        result = userServiceImpl.queryUser(page);
//        map.addAttribute(result.getData());
        //保存在缓存
//        if ((page.getSearchValue() == null || page.getSearchValue().toString().trim().equals("")) && (page.getOrderPropery() == null || page.getOrderPropery().equals("")))
//            memcachedClient.set(user.getId()+"_"+page.getPageNo(), page.getData(), 5 * 3600 * 1000);
        return result;
    }

    /**
     * 更新用户资料
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/user/upUser.hyml")
    @ResponseBody
    public BaseReturn updateUser(TUser user, HttpServletRequest request) {
        BaseReturn result = userServiceImpl.updateUser(user);
        return result;
    }

    /**
     * 兑换积分
     * @param user
     * @param gift
     * @param count
     * @return
     */
    @RequestMapping("/user/scoreProcess.hyml")
    @ResponseBody
    public BaseReturn scoreProcess(TUser user, TGift gift, String count) {
        BaseReturn result = userServiceImpl.scoreProcess(user, gift, count);
//        map.addAttribute(result);
        return result;
    }

    /**
     * 修改密码
     * @param user
     * @param oldPass
     * @param newPass
     * @return
     */
    @RequestMapping("/user/modifyPass.hyml")
    @ResponseBody
    public BaseReturn modifyPass(TUser user, String oldPass, String newPass) {
        BaseReturn result = new BaseReturn();
        if (memcachedClient.get(user.getId())==null) {
            result.setResult(0);
            result.setErrMessage("请登录后再操作........");
        }else
            result=userServiceImpl.rePass(user,oldPass,newPass);
        if(result.getResult()==1)
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {} . DateTime: {}.Result:Error",user.getId(),formatDateTime(new Date())));
        else
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {} . DateTime: {}.Result:Error",user.getId(), formatDateTime(new Date())));
//        map.addAttribute(result);
        return result;
    }

    /**
     * 添加到购物车
     * @param userId
     * @param cartItem
     * @param request
     * @return
     */
    @RequestMapping("/user/cartAdd.hyml")
    @ResponseBody
    public BaseReturn addCart(String userId, TCartItem cartItem, HttpServletRequest request) {
//        if(request.getSession().getAttribute("user")!=null) {
//          判断用户是否登录
//        }
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = new ArrayList<TCartItem>();
        cartItem.setId(RandomSymbol.getAllSymbol(16));
        cartItem.setCtime(new Date());
        if (userId != null && !"".equals(userId.trim()))
            cartItem.setUserId(userId);
        cartList.add(cartItem);
        request.getSession().setAttribute("cartList", cartList);
        result.setResult(1);
        result.setData(cartList);
        return result;
    }

    /**
     * 查看购物车,已存在于Session当中
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/cartRead.hyml")
    @ResponseBody
    public BaseReturn readCart(HttpServletRequest request) {
        List<TCartItem> cartList = (List<TCartItem>) request.getSession().getAttribute("cartList");
        BaseReturn result = new BaseReturn();
        result.setResult(1);
        result.setData(cartList);
        return result;
    }

    /**
     * 购物车修改
     * @param request
     * @param cartItem 购物车商品条目
     * @param action  动作 option (1.delete  2.modify)
     * @return
     */
    @RequestMapping("/user/cartModify.hyml")
    @ResponseBody
    public BaseReturn cartModify(HttpServletRequest request, TCartItem cartItem, String action) {
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = (List<TCartItem>) request.getSession().getAttribute("cartList");
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId().equals(cartItem.getId())) {
                if ("modify".equals(action))
                    cartList.set(i, cartItem);
                else
                    cartList.remove(i);
            }
        }
        request.getSession().setAttribute("cartList", cartList);
        result.setResult(1);
        result.setData(cartList);
        return result;
    }
}
