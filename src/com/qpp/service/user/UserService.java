package com.qpp.service.user;

import com.qpp.model.*;

import java.util.Map;

/**
 * Created by qpp on 7/31/2014.
 */
public interface UserService {
    //登录验证
    public BaseReturn checkLogin(String user, String password);

    //注册验证
    public BaseReturn checkRegister(String type, String value);

    //email激活
    public BaseReturn emailActive(String userId,String time);

    //注册
    public BaseReturn register(TUser user);

    //重设密码
    public BaseReturn rePassword(String userId,String userEmail);

    //更改密码
    public BaseReturn rePass(TUser user,String oldPass,String newPass);

    //修改资料
    public BaseReturn updateUser(TUser user);

    //查询用户
    public BaseReturn queryUser(PageModel<TUser> page);

    //用户积分记录
    public BaseReturn queryScore(PageModel<TScore> page);

    //用户积分兑换
    public BaseReturn scoreProcess(TUser user,TGift gift,String count);

    public boolean update(String tableName, Map data, String id);
}
