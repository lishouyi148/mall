package com.mall.dao;

import com.mall.entity.AgeGroupCountDTO;
import com.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findById(@Param("id") Integer id); // id改为Integer
    void insert(User user);
    void update(User user);
    void deleteById(@Param("id") Integer id); // id改为Integer
    List<User> findAll();

    List<AgeGroupCountDTO> countByAgeGroup();
}
