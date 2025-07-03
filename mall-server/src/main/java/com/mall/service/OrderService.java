package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.entity.Order;

public interface OrderService {
    // 分页查询所有订单
    PageInfo<Order> getAllOrders(int pageNum, int pageSize);
    
    // 根据订单编号查询订单
    Order getOrderById(Long orderId);
    
    // 新增订单
    int addOrder(Order order);
    
    // 根据ID删除订单
    int deleteOrder(Long orderId);
    
    // 更新订单
    int updateOrder(Order order);
}