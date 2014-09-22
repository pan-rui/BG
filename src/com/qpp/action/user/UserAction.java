package com.qpp.action.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qpp.action.BaseAction;
import com.qpp.dao.CartItemDao;
import com.qpp.model.*;
import com.qpp.service.market.MessageInfo;
import com.qpp.service.user.impl.CartJob;
import com.qpp.service.user.impl.UserServiceImpl;
import com.qpp.util.TriggerUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by qpp on 7/31/2014.
 */
@Controller
@RequestMapping("/user")
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
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn checkLogin(String nameOrEmail, String password, HttpServletRequest request) {
        BaseReturn result = userServiceImpl.checkLogin(nameOrEmail, password);
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
        if (result.getResult() == 1) {
//            request.getSession().setAttribute("user", result.getData());
            memcachedClient.set(String.valueOf(((TUser)result.getData()).getId()),result.getData(),18*60*60000);
            loger.info(MessageFormat.format("EventType:User login. DateTime: {0} . Result:Success",formatDateTime(new Date())));
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
     * @param imgCode
     * @param request
     * @return
     */

    @RequestMapping(value = "imageCheck",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn imageCheck(String imgCode, HttpServletRequest request) {
        String imCode = String.valueOf(request.getSession().getAttribute("imageCode"));
        long imgTime = Long.parseLong(String.valueOf(request.getSession().getAttribute("imgTime")));
//        JSONObject jsonObj = JSON.parseObject(imgCode);
//        String imageCode= (String) jsonObj.get("imageCode");
        BaseReturn result = new BaseReturn();
        if (!imCode.equalsIgnoreCase(imgCode)) {
            result.setResult(1);
            result.setErrMessage(getMessage(request, "err.user.login.imgCheck1", null));
        } else if(imgTime+5*60*60*1000<new Date().getTime()){
            result.setResult(1);
            result.setErrMessage(getMessage(request, "err.user.login.imgCheck2", null));
        }
            result.setResult(0);
        return result;
    }


    /**
     * 注册验证
     * @param nameOrEmail option: 1.name  2.email
     * @param value
     * @return
     */
    @RequestMapping(value = "registerCheck/{nameOrEmail}/{value}",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn checkRegister(@PathVariable String nameOrEmail,@PathVariable String value, HttpServletRequest request) {//MessageFormat.format("", )
        BaseReturn result = userServiceImpl.checkRegister(nameOrEmail, value);
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
        return result;
    }

    /**
     * 注册入库
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BaseReturn register(@RequestBody TUser user, HttpServletRequest request) {
        BaseReturn result=userServiceImpl.register(user);
//            map.addAttribute(result);
//        TUser tuser= (TUser)result.getData();
        if(result.getResult()==1){
            loger.info(MessageFormat.format("EventType: New User Register. UserId: {0} . DateTime: {1} . Result:Success",((TUser)result.getData()).getId(),formatDateTime(new Date())));
//            return "/user/userRegisert";

        }else{
            result.setErrMessage(getMessage(request, result.getErrMessage(), null));
//           return "/user/error";
            loger.info(MessageFormat.format("EventType: New User Register. . DateTime: {0} . Result:Error",formatDateTime(new Date())));
        }
        return result;
    }

    /**
     * 邮箱验证
     * @param userId
     * @param time
     * @return
     */
    @RequestMapping(value = "registerV/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn registerV(@PathVariable int userId, String time, HttpServletRequest request) {
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
@RequestMapping(value = "rePass",method = RequestMethod.GET)
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
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "rePassCommit",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn rePassM(@RequestBody String jsonStr, HttpServletRequest request) {
        JSONObject obj = JSON.parseObject(jsonStr);
        BaseReturn result = userServiceImpl.rePasswordd(String.valueOf(obj.get("email")), String.valueOf(obj.get("password")), String.valueOf(obj.get("time")).replaceAll(",", ""));
        if (result.getResult() == 1) {
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

//    @RequestMapping(value = "{userId}/find-{start}-{end}",method = RequestMethod.GET)
//    @ResponseBody
//    public BaseReturn queryUser(String searchProperty,String searchValue,String orderProperty,boolean isDesc,@PathVariable int start,@PathVariable int end,@PathVariable int userId, HttpServletRequest request) {
//        BaseReturn result = new BaseReturn();
//        TUser user = (TUser) memcachedClient.get(String.valueOf(userId));
//        if (user==null||!user.getType().equals("c")) {
//            result.setResult(0);
//            result.setErrMessage(getMessage(request, "err.user.findUser.authorized", null));
//            return result;
//        }
//        result = userServiceImpl.queryUser(searchProperty, searchValue, orderProperty, isDesc, start, end);
//        return result;
//    }

    @RequestMapping(value = "{userId}/find", method = RequestMethod.GET)
    public @ResponseBody BaseReturn findUser(@PathVariable int userId, HttpServletRequest request) {
        if(memcachedClient.get(String.valueOf(userId))==null)
            return new BaseReturn(getMessage(request, "err.user.noLogin", null), 0);
        TUser user = userServiceImpl.getById(userId);
        return new BaseReturn(1, user);
    }

    /**
     * 更新用户资料
     * @param user
     * @param request
     * @return
     */
//    @RequestMapping(value = "upUser",method = RequestMethod.PUT)
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn updateUser(@RequestBody TUser user, HttpServletRequest request) {
        BaseReturn result = userServiceImpl.updateUser(user);
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
        return result;
    }

    /**
     * 兑换积分
     * @param userId
     * @param gift
     * @param count
     * @return
     */
    @RequestMapping(value = "{userId}/convert-{count}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn scoreProcess(@PathVariable int userId,@RequestBody TGift gift,@PathVariable int count,HttpServletRequest request) {
        TUser user = (TUser) memcachedClient.get(String.valueOf(userId));
        BaseReturn result = userServiceImpl.scoreProcess(user, gift, count);
        if (result.getErrMessage() != null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
//        map.addAttribute(result);
        return result;
    }

    /**
     * 修改密码
     * @param userId
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "{userId}/modifyPass",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn modifyPass(@PathVariable int userId,@RequestBody String jsonStr, HttpServletRequest request) {
        BaseReturn result = new BaseReturn();
        TUser user=userServiceImpl.getById(userId);
        JSONObject obj = JSON.parseObject(jsonStr);
        if (memcachedClient.get(String.valueOf(userId))==null||user==null) {
            result.setResult(1);
            result.setErrMessage(getMessage(request,"err.user.modify.Pass.noLogin",null));
            return result;
        }else
            result = userServiceImpl.rePass(user, obj.getString("oldPass"), obj.getString("newPass"));
        if(result.getResult()==1)
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {0} . DateTime: {1}.Result:Success", userId, formatDateTime(new Date())));
        else {
            result.setErrMessage(getMessage(request, result.getErrMessage(), null));
            loger.info(MessageFormat.format("EventType: ModifyPassWord .UserId: {0} . DateTime: {1}.Result:Error", userId, formatDateTime(new Date())));
        }
//        map.addAttribute(result);
        return result;
    }

    /**
     * 添加到购物车
     * @param userId
     * @param cartItem
     * @return
     */
    @RequestMapping(value = "{userId}/cartAdd",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn addCart(@PathVariable int userId,@RequestBody TCartItem cartItem) {
//        if(request.getSession().getAttribute("user")!=null) {
//          判断用户是否登录
//        }
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = new ArrayList<TCartItem>();
//        cartItem.setId(RandomSymbol.getAllSymbol(16));
        cartItem.setCtime(new Date());
        if (userId <=0)
            cartItem.setUserId((TUser) memcachedClient.get(String.valueOf(userId)));
        cartList.add(cartItem);
//        request.getSession().setAttribute("cartList", cartList);
        memcachedClient.set(userId + "cart", cartList,18*60*60*1000+60000);
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("dao", cartItemDao);
        dataMap.put("mem", memcachedClient);
        dataMap.put("key", userId + "cart");
        TriggerUtil.simpleTask(dataMap,CartJob.class ,new Date(System.currentTimeMillis()+18*60*60*1000));
        result.setResult(0);
        result.setData(cartList);
        return result;
    }

    /**
     * 查看购物车,
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}/cartRead",method = RequestMethod.GET)
    @ResponseBody
    public BaseReturn readCart(@PathVariable int userId) {
        List<TCartItem> cartList = (List<TCartItem>) memcachedClient.get(userId + "cart");
        BaseReturn result = new BaseReturn();
        result.setResult(0);
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
    @RequestMapping(value = "{userId}/cartModify/{action}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn cartModify(@PathVariable int userId,@RequestBody TCartItem cartItem,@PathVariable String action) {
        BaseReturn result = new BaseReturn();
        List<TCartItem> cartList = (List<TCartItem>) memcachedClient.get(userId+"cart");
        for (int i = 0; i < cartList.size(); i++) {
            if (String.valueOf(cartList.get(i).getId()).equals(cartItem.getId())) {
                if ("modify".equals(action))
                    cartList.set(i, cartItem);
                else
                    cartList.remove(i);
            }
        }
        memcachedClient.set(userId+"cart",cartList,18*60*60*1000+60000);
        result.setResult(0);
        result.setData(cartList);
        return result;
    }

    /**
     * 设置用户状态
     * @param userId
     * @param status 状态值:1 激活,0 未激活 3 黑名单
     * @return
     */
    @RequestMapping(value = "{userId}/status-{status}",method = RequestMethod.PUT)
    @ResponseBody
    public BaseReturn setUserStatus(@PathVariable int userId,@PathVariable String status,HttpServletRequest request) {
        if(memcachedClient.get(String.valueOf(userId))==null)
            return new BaseReturn(getMessage(request, "err.user.noLogin", null), 0);
        if (!"".equals(status)){
            BaseReturn result= userServiceImpl.setUserStatus(userId, status);
            if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
            return result;
        }
        return new BaseReturn(getMessage(request,"err.user.set.status.notNull",null),0);
    }

    /**
     * 设置用户类型
     * @param userId
     * @param type
     * @return
     */
@RequestMapping(value = "{userId}/type-{type}",method = RequestMethod.PUT)
@ResponseBody
    public BaseReturn setUserType(@PathVariable int userId,@PathVariable String type,HttpServletRequest request) {
    if(memcachedClient.get(String.valueOf(userId))==null)
        return new BaseReturn(getMessage(request, "err.user.noLogin", null), 0);
        if (!"".equals(type)){
            BaseReturn result= userServiceImpl.setUserType(userId, type);
            if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
            return result;
        }
        return new BaseReturn(getMessage(request,"err.user.set.type.notNull",null),0);
    }

    /**
     * 用户地址增、删、改
     * @param userId
     * @param address eg: [{"isDefault":"true","addr":"xxxxxxx"},{"isDefault":"false","addr":"xxxxxxx"}]
     * @return
     */
@RequestMapping(value = "{userId}/addr",method = RequestMethod.PUT)
@ResponseBody
    public BaseReturn setUserAddr(@PathVariable int userId,@RequestBody List<Map> address,HttpServletRequest request){
        if (address.size()>0) {
            BaseReturn result=userServiceImpl.setUserAddr(userId, JSON.toJSONString(address));
            if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
            return result;
        }
        return new BaseReturn(getMessage(request,"err.user.set.address.notNull",null),0);
    }

    /**
     * 添加或修改邮件订阅
     * @param request eg:{"subject":"活动通知1","content":"xxxxxxdfdfs","sendDate":"2014-08-29 15:15","isPublish":"1"}
     * @return
     */
    @RequestMapping(value = "{userId}/addEmail",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturn addEmail(@PathVariable int userId,HttpServletRequest request) {
        TMailPublish mailPublish=null;
        JSONObject json=new JSONObject();
        TUser user = (TUser) memcachedClient.get(String.valueOf(userId));
        if (user == null || !"c".equals(user.getType()))
            return new BaseReturn(getMessage(request, "err.user.email.add.authorized", null), 0);
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
                    } else{
                        if(fi.getFieldName().contains("Date"))
                            json.put(fi.getFieldName(), new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(URLDecoder.decode(fi.getString(),request.getHeader("Content-Encoding"))));//TODO:请求时设置编码
                        else
                        json.put(fi.getFieldName(), URLDecoder.decode(fi.getString(),request.getHeader("Content-Encoding")));
                    }
                }
                mailPublish=JSON.toJavaObject(json, TMailPublish.class);
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
     * @param userId
     * @param emailId
     * @return
     */
    @RequestMapping(value = "{userId}/subscription/{emailId}",method = RequestMethod.GET)
    public @ResponseBody BaseReturn subscription(@PathVariable int userId,@PathVariable int emailId,HttpServletRequest request) {
        BaseReturn result=userServiceImpl.subscription((TUser)memcachedClient.get(String.valueOf(userId)),emailId);
        if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request, result.getErrMessage(), null));
        return result;
    }

    /**
     * 用户取消订阅
     * @param userId
     * @param emailId
     * @return
     */
@RequestMapping(value = "{userId}/cancelSubscript/{emailId}",method = RequestMethod.DELETE)
@ResponseBody
    public BaseReturn cancelSubscript(@PathVariable int userId,@PathVariable int emailId) {
        return userServiceImpl.cancelSubscript(userId,emailId);
    }

    /**
     * 用户查看订阅邮件
     * @param page
     * @param userId
     * @return
     */
@RequestMapping(value = "{userId}/queryEmail",method = RequestMethod.GET)
@ResponseBody
    public BaseReturn queryEmail(@RequestBody PageModel<TMailPublish> page,@PathVariable int userId,HttpServletRequest request) {
        BaseReturn result = new BaseReturn();
    TUser user = (TUser) memcachedClient.get(String.valueOf(userId));
        if (page == null)
            page = new PageModel<TMailPublish>();
        result = userServiceImpl.queryEmail(page,user);
        if(result.getErrMessage()!=null) result.setErrMessage(getMessage(request,result.getErrMessage(),null));
        return result;
    }
}
