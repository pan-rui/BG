package com.qpp.service.market.impl;

import com.qpp.dao.BaseDao;
import com.qpp.dao.ConfigDao;
import com.qpp.dao.ScoreDao;
import com.qpp.dao.UserDao;
import com.qpp.model.*;
import com.qpp.service.market.UserService;
import com.qpp.util.Email;
import com.qpp.util.MD5;
import com.qpp.util.RandomSymbol;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by qpp on 7/31/2014.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private Email Mail;
    @Resource
    private ConfigDao configDao;
    private Pattern patten = Pattern.compile("^([\\w|-]+)@(\\w+)\\.(\\w+)$");
    private MD5 md5 = new MD5();
    private ScoreDao scoreDao;
//    @Resource
//    private Email Mail;
//    @Resource
//    private MessageSourceResourceBundle messageSource;
    @Override
    public BaseReturn checkLogin(String user, String password) {
        BaseReturn result = new BaseReturn();
        TUser tuser = null;
        if (patten.matcher(user).matches()) {
            tuser = userDao.getBySql("select name,email,password from t_user where email='" + user + "'");
        } else
            tuser = userDao.getBySql("select name,email,password from t_user where name='" + user + "'");
        if (tuser != null && tuser.getPassword().equals(md5.getMD5ofStr(password))) {
            result.setResult(1);
            result.setData(tuser);
        } else {
            result.setResult(0);
            result.setErrMessage("用户名(邮箱)与密码不匹配");
        }
        return result;
    }

    @Override
    public BaseReturn checkRegister(String type, String value) {
        BaseReturn result = new BaseReturn();
        TUser tuser = null;
        if ("name".equals(type))
            tuser = userDao.getBySql("select name from t_user where name='" + value + "'");
        else
            tuser = userDao.getBySql("select name from t_user where email='" + value + "'");
        if (tuser != null) {
            result.setResult(0); //0表示失败,1表示成功
            result.setErrMessage(MessageFormat.format("该{0}已被其它用户注册","name".equals(type)?"用户名":"邮箱"));
        }else{
            result.setResult(1);
        }
        return result;
    }

    @Override
    public BaseReturn emailActive(String userId,String time) {
        BaseReturn result = new BaseReturn();
        int interval=7*24*3600*1000;
        TUser tuser = userDao.getById(userId);
           boolean valid= (Integer.parseInt(time)-tuser.getCtime().getTime())<interval;
        if (tuser != null&&valid) {
            tuser.setStatus('1');
            userDao.update(tuser);
            result.setResult(1);
            result.setData(tuser);
        }else {
            result.setResult(0);
            result.setErrMessage("验证失败,验证连接已过期..");
        }
        return result;
    }

    @Override
    public BaseReturn register(TUser user) {
        BaseReturn result = new BaseReturn();
        if ("c".equals(user.getType())) {
            //CGP用户....事项
        }else{
            //一般用户......
        }

        Date date=new Date();
        String userId= RandomSymbol.getAllSymbol(16);
        user.setId(userId);
        user.setStatus('0');
        user.setCtime(date);
        user.setUtime(date);
        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        try {
            userDao.save(user);
//        Mail.sendMail(messageSource.getString());
            result.setResult(1);
            result.setData(user);
            TConfig subject = configDao.getById("email.subject");
            TConfig context = configDao.getById("email.context");
//            Mail.sendMail(getMessage(request,"email.subject",null),getMessage(request,"email.context",new Object[]{tuser.getId(),new Date().getTime()}),tuser.getEmail());
            Mail.sendMail(MessageFormat.format(subject.getConfigValue(), null), MessageFormat.format(context.getConfigValue(), user.getId(), new Date().getTime()), user.getEmail());
        } catch (Exception e) {
            result.setResult(0);
            result.setErrMessage("保存错误,注册失败...");
        }
        return result;
    }

    @Override
    public BaseReturn rePassword(String name, String userEmail) {
        BaseReturn result = new BaseReturn();
        if(!patten.matcher(name).matches()){
            TUser tuser=userDao.getBySql("select name,email from t_user where name='"+name+"'");
            if(!tuser.getEmail().equals(userEmail)){
                result.setResult(0);
                result.setErrMessage("用户名与您填写的邮箱不匹配,请核对后重新输入.");
                return result;
            }
        }else{
            TUser tuser=userDao.getBySql("select name,email from t_user where email='"+name+"'");
            if(!tuser.getEmail().equals(userEmail)){
                result.setResult(0);
                result.setErrMessage("该邮箱并未注册,请核对后重新输入.");
                return result;
            }
        }
        result.setResult(1);
        TConfig subject=configDao.getById("email.rePass.subject");
        TConfig context=configDao.getById("email.rePass.context");
        Mail.sendMail(MessageFormat.format(subject.getConfigValue(),null),MessageFormat.format(context.getConfigValue(),userEmail,new Date().getTime()),userEmail);
        return result;
    }

    @Override
    public BaseReturn rePass(TUser user,String oldPass,String newPass) {
        BaseReturn result = new BaseReturn();
        if (!user.getPassword().equals(md5.getMD5ofStr(oldPass))) {
            result.setResult(0);
            result.setErrMessage("您输入的旧密码不正确,修改失败....");
        }
//        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("password",md5.getMD5ofStr(newPass));
        try {
            userDao.update("t_user", dataMap, user.getId());
            result.setResult(1);
            result.setData(user);
        } catch (Exception e) {
            result.setResult(0);
            result.setErrMessage("数据更新错误,修改密码失败.");
        }
        return result;
    }

    @Override
    public BaseReturn updateUser(TUser user) {
        BaseReturn result = new BaseReturn();
        try {
            userDao.save(user);
            result.setResult(1);
            result.setData(user);
        } catch (Exception e) {
            result.setResult(0);
            result.setErrMessage("数据更新错误,用户资料更新失败");
        }
        return result;
    }

    @Override
    public BaseReturn queryUser(PageModel<TUser> page) {
//        BaseReturn result = new BaseReturn();
//        StringBuffer sql = new StringBuffer("select * from t_user");
//        if(page.getSearchValue()!=null&&!page.getSearchValue().toString().trim().equals(""))
//            sql.append(" where " + page.getSearchProperty() + "like '%" + page.getSearchValue().toString() + "%'");
//        if(page.getOrderPropery()!=null&&!page.getOrderPropery().trim().equals(""))
//            sql.append(" order by "+page.getOrderPropery());
//        if(page.getIsDesc())
//            sql.append(" desc");
//        page.setRowCount(userDao.getKeyBySQL(sql.toString()));
//        page.setData(userDao.getsByQueryPage(sql.toString(), page.getStart(), page.getPageSize()));
////        result.setResult(page.getRowCount());
//        result.setData(page);
        return queryPage(page,"t_user",userDao);
    }

    @Override
    public BaseReturn queryScore(PageModel<TScore> page) {

        return queryPage(page,"t_score",scoreDao);
    }

    @Override
    public BaseReturn scoreProcess(TUser user,TGift gift,String count) {
        BaseReturn result = new BaseReturn();
        int needScore = gift.getPrice() * Integer.parseInt(count);
//        if(user.getStatus()==0){
//            //未激活用户
//            result.setResult(0);
//            result.setErrMessage("抱歉,未激活用户无法享受此活动!");
//              return result;
//        }
        if (user.getScore() < needScore) {
            result.setResult(0);
            result.setErrMessage("抱歉,您的积分数量不够,兑换不成功!");
        }
        //更新单个字段
        Map<String,Object> dataMap=new HashMap<String, Object>();
        dataMap.put("score",user.getScore()-needScore);
        userDao.update("t_user",dataMap,user.getId());
        //新增积分记录
        TScore score = new TScore();
        Date date = new Date();
        score.setId(RandomSymbol.getAllSymbol(16));
        score.setCtime(date);
        score.setTGift(gift);
        score.setTUser(user);
        score.setCount(Integer.parseInt(count));
        score.setSize(needScore);
        score.setStatus("1");
        scoreDao.insert(score);
        result.setResult(0);
        result.setData(user);
        return result;
    }

    public BaseReturn rePasswordd(String email,String password,String time) {
        BaseReturn result = new BaseReturn();
        if(Long.parseLong(time)+24*60*60*1000<new Date().getTime()){
            result.setResult(0);
            result.setErrMessage("链接已经过期,建议重新发起请求");
            return result;
        }
        TUser tuser = userDao.getBySql("select * from t_user where email='" + email + "'");
        tuser.setPassword(md5.getMD5ofStr(password));
        try {
            userDao.update(tuser);
            result.setResult(1);
            result.setData(tuser);
        } catch (Exception e) {
            result.setResult(0);
            result.setErrMessage("数据更新错误,重设密码失败.");
        }
        return result;
    }

    /**
     * 分页查询
     * @param page
     * @param tableName
     * @param dao
     * @return
     */
    public BaseReturn queryPage(PageModel page, String tableName, BaseDao dao) {
        BaseReturn result = new BaseReturn();
        StringBuffer sql = new StringBuffer("select * from "+tableName);
        if(page.getSearchValue()!=null&&!page.getSearchValue().toString().trim().equals(""))
            sql.append(" where " + page.getSearchProperty() + "like '%" + page.getSearchValue().toString() + "%'");
        if(page.getOrderPropery()!=null&&!page.getOrderPropery().trim().equals(""))
            sql.append(" order by "+page.getOrderPropery());
        if(page.getIsDesc())
            sql.append(" desc");
        page.setRowCount(dao.getKeyBySQL(sql.toString()));
        page.setData(dao.getsByQueryPage(sql.toString(), page.getStart(), page.getPageSize()));
//        result.setResult(page.getRowCount());
        result.setData(page);
        return result;
    }

}
