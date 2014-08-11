package com.qpp.service.market.impl;

import com.qpp.dao.OrderItemDao;
import com.qpp.model.TOrderItem;
import com.qpp.service.market.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by qpp on 7/14/2014.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public boolean save(TOrderItem orderItem) {
        try {
            orderItemDao.save(orderItem);
        }catch (Exception e){
            return orderItemDao.insert(orderItem);
        }
        return true;
    }
    public boolean insert(TOrderItem orderItem){
        return orderItemDao.insert(orderItem);
    }
}
