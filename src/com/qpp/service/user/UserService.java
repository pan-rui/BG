package com.qpp.service.user;

import com.qpp.dao.BaseDao;
import com.qpp.model.BaseReturn;
import com.qpp.model.TGift;
import com.qpp.model.TUser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by qpp on 7/31/2014.
 */
public interface UserService {
    //登录验证
    public BaseReturn checkLogin(String user, String password);

    //注册验证
//    public BaseReturn checkRegister(String type, String value);

    //email激活
    public BaseReturn emailActive(long userId,String time);

    //注册
    public BaseReturn register(Map<String,Object> data,long appId);

    //重设密码
    public BaseReturn rePassword(String name,String userEmail);

    //更改密码
    public BaseReturn rePass(TUser user,String oldPass,String newPass);

    //修改资料
    public BaseReturn updateUser(TUser user);

    //查询用户
//    public BaseReturn queryUser(PageModel<TUser> page);

        //查询用户
//    public BaseReturn queryUser(String searchProperty, String searchValue,String orderProperty, Boolean isDesc, int start, int end);

    //用户积分记录
//    public BaseReturn queryScore(PageModel<TScore> page);

    //用户积分兑换
    public BaseReturn scoreProcess(TUser user,TGift gift,int count);

    public boolean update(String tableName, Map data);

    public TUser getById(Serializable id);
    public String exists(BaseDao dao, String sql);

    public BaseReturn delete(List<Object> userList);

}
