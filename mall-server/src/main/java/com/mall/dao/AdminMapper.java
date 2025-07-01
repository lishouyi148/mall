// com.mall.dao.AdminMapper.java
package com.mall.dao;

import com.mall.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    Admin findByUsername(@Param("username") String username);
    void insert(Admin admin);
}


