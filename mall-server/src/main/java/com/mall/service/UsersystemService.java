package com.mall.service;

import com.mall.entity.ResponseResult;
import com.mall.entity.User;

import java.util.List;

public interface UsersystemService {
    ResponseResult<List<User>> getAllUsers();
    ResponseResult<String> addUser(User user);
    // 仅保留Integer类型的id方法，删除Long类型重载
    ResponseResult<String> updateUser(Integer id, User user);
    ResponseResult<String> deleteUser(Integer id);
    ResponseResult<User> getUserById(Integer id);
}