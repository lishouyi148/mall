package com.mall.dao;

import com.mall.entity.Admin;
import com.mall.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    // 管理员查询
    Admin findAdminByUsername(@Param("username") String username);

    // 商家查询
    Merchant findMerchantByUsername(@Param("username") String username);
}
