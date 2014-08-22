package com.qpp.action.user;

import com.alibaba.fastjson.JSON;
import com.qpp.action.BaseAction;
import com.qpp.dao.CartItemDao;
import com.qpp.model.*;
import com.qpp.service.market.MessageInfo;
import com.qpp.service.user.impl.CartJob;
import com.qpp.service.user.impl.UserServiceImpl;
import com.qpp.util.RandomSymbol;
import com.qpp.util.TriggerUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by qpp on 7/31/2014.
 */
@Controller
public class UserAction extends BaseAction {
    @Resource
    private UserServiceImpl userServiceImpl;
    @Resource
    private CartItemDao cartItemDao;
//    @Resource
//    private ResourceBundleMessageSource messageSource;


    /**
     * 用户登录(Ajax或form)
     * @param nameOrEmail
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/user/login")
    @ResponseBody
    public BaseReturn checkLogin(@RequestParam(value = "nameOrEmail") String nameOrEmail, String password, HttpServletRequest request) {
        BaseReturn result = userServiceImpl.checkLogin(nameOrEmail, password);
        if (result.getResult() == 1) {
//            request.getSession().setAttribute("user", result.getData());
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
    @RequestMapping("/user/imageCheck")
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
     * 注册验证
     * @param nameOrEmail option: 1.name  2.email
     * @param value
     * @return
     */
    @RequestMapping("/user/registerCheck")
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
    @RequestMapping("/user/register")
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
    @RequestMapping("/user/registerV")
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
@RequestMapping("/user/rePass")
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
    @RequestMapping("/user/rePassCommit")
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
    @RequestMapping("/user/findUser")
    @ResponseBody
    public BaseReturn queryUser(PageModel<TUser> page,String userId) {
        BaseReturn result = new BaseReturn();
//        TUser user = (TUser) request.getSession().getAttribute("user");
        TUser user = (TUser) memcachedClient.get(userId);
        if (!user.getType().equals("c")) {
             result.setResult(0);
            result.setErrMessage("您没有此权限.....");
//            map.addAttribute(result);
//            return "/user/userAuthErr";
            return result;
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
    @RequestMapping("/user/upUser")
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
    @RequestMapping("/user/scoreProcess")
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
    @RequestMapping("/user/modifyPass")
    @ResponseBody
    public BaseReturn modifyPass(TUser user, String oldPass, String newPass) {
        BaseReturn result = new BaseReturn();
        if (memcachedClient.get(user.getId())==null) {
            result.setResult(0);
            result.setErrMessage("请登录后再操作........");
        }else
            result=userServiceImpl.rePass(user,oldPass,newPass);
        if(result.getResult()==1)
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {} . DateTime: {}.Result:Success",user.getId(),formatDateTime(new Date())));
        else
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {} . DateTime: {}.Result:Error",user.getId(), formatDateTime(new Date())));
//        map.addAttribute(result);
        return result;
    }

    /**
     * 添加到购物车
     * @param userId
     * @param cartItem
     * @return
     */
    @RequestMapping("/user/cartAdd")
    @ResponseBody
    public BaseReturn addCart(String userId, TCartItem cartItem) {
//        if(request.getSession().getAttribute("user")!=null) {
//          判断用户是否登录
//        }
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = new ArrayList<TCartItem>();
        cartItem.setId(RandomSymbol.getAllSymbol(16));
        cartItem.setCtime(new Date());
        if (userId != null && !"".equals(userId.trim()))
            cartItem.setTUser((TUser) memcachedClient.get(userId));
        cartList.add(cartItem);
//        request.getSession().setAttribute("cartList", cartList);
        memcachedClient.set(userId + "cart", cartList,18*60*60*1000+60000);
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("dao", cartItemDao);
        dataMap.put("mem", memcachedClient);
        dataMap.put("key", userId + "cart");
        TriggerUtil.simpleTask(dataMap,CartJob.class ,new Date(System.currentTimeMillis()+18*60*60*1000));
        result.setResult(1);
        result.setData(cartList);
        return result;
    }

    /**
     * 查看购物车,已存在于Session当中
     *
     * @param userId
     * @return
     */
    @RequestMapping("/user/cartRead")
    @ResponseBody
    public BaseReturn readCart(String userId) {
        List<TCartItem> cartList = (List<TCartItem>) memcachedClient.get(userId + "cart");
        BaseReturn result = new BaseReturn();
        result.setResult(1);
        result.setData(cartList);
        return result;
    }

    /**
     * 购物车修改
     * @param userId
     * @param cartItem 购物车商品条目
     * @param action  动作 option (1.delete  2.modify)
     * @return
     */
    @RequestMapping("/user/cartModify")
    @ResponseBody
    public BaseReturn cartModify(String userId, TCartItem cartItem, String action) {
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = (List<TCartItem>) memcachedClient.get(userId+"cart");
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId().equals(cartItem.getId())) {
                if ("modify".equals(action))
                    cartList.set(i, cartItem);
                else
                    cartList.remove(i);
            }
        }
        memcachedClient.set(userId+"cart",cartList,18*60*60*1000+60000);
        result.setResult(1);
        result.setData(cartList);
        return result;
    }

    /**
     * 设置用户状态
     * @param userId
     * @param status 状态值:1 激活,0 未激活 3 黑名单
     * @return
     */
    @RequestMapping("/user/setStatus")
    @ResponseBody
    public BaseReturn setUserStatus(String userId, String status) {
        if (!"".equals(status))
            return userServiceImpl.setUserStatus(userId, status);
        return new BaseReturn(0, "状态值不能为空");
    }

    /**
     * 设置用户类型
     * @param userId
     * @param type
     * @return
     */
@RequestMapping("/user/setType")
@ResponseBody
    public BaseReturn setUserType(String userId, String type) {
        if (!"".equals(type))
            return userServiceImpl.setUserType(userId, type);
        return new BaseReturn(0, "类型值不能为空");
    }

    /**
     * 用户地址增、删、改
     * @param userId
     * @param address eg: [{"isDefault":"true","addr":"xxxxxxx"},{"isDefault":"false","addr":"xxxxxxx"}]
     * @return
     */
@RequestMapping("/user/setAddr")
@ResponseBody
    public BaseReturn setUserAddr(String userId,List<Map> address){
        if (address.size()>0)
            return userServiceImpl.setUserAddr(userId, JSON.toJSONString(address));
        return new BaseReturn(0, "地址值不能为空");
    }

    /**
     * 添加或修改邮件订阅
     * @param email
     * @param request
     * @return
     */
    @RequestMapping("/user/addEamil")
    @ResponseBody
    public BaseReturn addEmail(TMailPublish email,HttpServletRequest request) {
        String id =email.getId()!=null&&!"".equals(email.getId())?email.getId():RandomSymbol.getAllSymbol(16);
        email.setId(id);
        File file = null;
        int maxFileSize = Integer.parseInt(MessageInfo.getMessage("upload.File.MaxSize"));
        int maxMemSize = Integer.parseInt(MessageInfo.getMessage("upload.Mem.MaxSize"));
        String filePath = "/email/" + id;
        String contentType=request.getContentType();
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
            try {
                List fileItems = upload.parseRequest(request);
                Iterator it=fileItems.iterator();
                while (it.hasNext()) {
                    FileItem fi=(FileItem)it.next();
                    if(!fi.isFormField()){
                        //获取上传文件的参数
                        String filedName=fi.getFieldName();
                        String fileName=fi.getName();
                        boolean isInMemory=fi.isInMemory();
                        long sizeInBytes=fi.getSize();
                        //写入文件
//                        if(fileName.lastIndexOf("\\")>=0)
                            file=new File(filePath,filedName.substring(fileName.lastIndexOf("\\")+1));
//                        else
//                            file = new File(filePath, filedName);
                        if(!file.getParentFile().exists())
                            file.getParentFile().createNewFile();
                        fi.write(file);
                        System.out.println("上传的邮件附件文件名:"+filePath+fileName);
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
                email.setAttachment(file.getAbsolutePath());
        return userServiceImpl.addEmail(email);
    }

    /**
     * 用户订阅邮件
     * @param userEmail
     * @return
     */
    @RequestMapping("/user/subscription")
    @ResponseBody
    public BaseReturn subscription(TUserEmail userEmail) {
        return userServiceImpl.subscription(userEmail);
    }

    /**
     * 用户取消订阅
     * @param userEmail
     * @return
     */
@RequestMapping("/user/cancelSubscript")
@ResponseBody
    public BaseReturn cancelSubscript(TUserEmail userEmail) {
        return userServiceImpl.cancelSubscript(userEmail);
    }

    /**
     * 用户查看订阅邮件
     * @param page
     * @param userId
     * @return
     */
@RequestMapping("/user/queryEmail")
@ResponseBody
    public BaseReturn queryEmail(PageModel<TMailPublish> page,String userId) {
        BaseReturn result = new BaseReturn();
        TUser user = (TUser) memcachedClient.get(userId);
        if (page == null)
            page = new PageModel<TMailPublish>();
            result = userServiceImpl.queryEmail(page,user);
        return result;
    }
}
