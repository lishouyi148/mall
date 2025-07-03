package com.mall.controller;

import com.mall.entity.AgeGroupCountDTO;
import com.mall.entity.ResponseResult;
import com.mall.entity.User;
import com.mall.service.UsersystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersystemService userService;

    // 查询所有用户（无需修改）
    @GetMapping
    public ResponseResult<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    // 新增用户（无需修改）
    @PostMapping
    public ResponseResult<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // 更新用户信息（修改id类型为Integer）
    @PutMapping("/{id}")
    public ResponseResult<String> updateUser(
            @PathVariable Integer id,  // 修改为Integer
            @RequestBody User user
    ) {
        return userService.updateUser(id, user);
    }

    // 删除用户（修改id类型为Integer）
    @DeleteMapping("/{id}")
    public ResponseResult<String> deleteUser(@PathVariable Integer id) {  // 修改为Integer
        return userService.deleteUser(id);
    }

    // 查询单个用户（修改id类型为Integer）
    @GetMapping("/{id}")
    public ResponseResult<User> getUserById(@PathVariable Integer id) {  // 修改为Integer
        return userService.getUserById(id);
    }

    @GetMapping("/age-group/count")
    public ResponseResult<List<AgeGroupCountDTO>> getUsersCountByAgeGroup() {
        return userService.countUsersByAgeGroup();
    }
}

