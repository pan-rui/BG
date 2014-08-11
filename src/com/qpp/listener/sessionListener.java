package com.qpp.listener;

import com.qpp.dao.CartItemDao;
import com.qpp.model.TCartItem;
import com.qpp.model.TUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * 用户关闭浏览器或断电时将其购物车数据持久化到数据库..
 * Created by qpp on 8/6/2014.
 */
public class sessionListener implements HttpSessionListener {
    @Resource
    private CartItemDao cartItemDao;
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session=event.getSession();
        TUser user = (TUser) session.getAttribute("user");
        List<TCartItem> cartList = (List<TCartItem>) session.getAttribute("cartList");
        if(user!=null&&cartList!=null&&!cartList.isEmpty()){
            for(TCartItem cartItem:cartList)
                cartItemDao.insert(cartItem);  //持久化到数据库
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }
}
