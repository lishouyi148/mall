package com.mall.dao;

import com.mall.entity.UserAddress;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface UserAddressMapper {
    /**
     * 查询所有地址
     * @return 地址列表
     */
    @Select("SELECT * FROM user_address")
    List<UserAddress> findAll();

    /**
     * 根据地址ID查询单个地址
     * @param addressId 地址ID
     * @return 单个地址信息
     */
    @Select("SELECT * FROM user_address WHERE address_id = #{addressId}")
    UserAddress findById(Long addressId);
}