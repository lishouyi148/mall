package com.mall.dao;

import com.mall.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 分页查询所有订单
    List<Order> selectAll();
    
    // 根据订单ID查询订单
    Order selectByOrderId(@Param("orderId") Long orderId);
    
    // 新增订单
    int insert(Order order);
    
    // 根据ID删除订单
    int deleteByPrimaryKey(Long orderId);
    
    // 更新订单
    int updateByPrimaryKey(Order order);
}