package com.mall.dao;

import com.mall.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MerchantMapper {
    Merchant findByUsername(@Param("username") String username);
    Merchant findById(@Param("id") Integer id); // 参数名改为 id（对应新主键）
    void insert(Merchant merchant);
    void update(Merchant merchant);
    List<Merchant> findAll();
    void deleteById(@Param("id") Integer id); // 参数名改为 id
}