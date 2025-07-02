// com.mall.service.impl.UserServiceImpl.java
package com.mall.service.impl;

import com.mall.dao.AdminMapper;
import com.mall.dao.MerchantMapper;
import com.mall.entity.*;
import com.mall.service.UserService;
import com.mall.dao.UserDao; // 导入 UserDao
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    // 新增：注入 UserDao 实例
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 密码加密器

    @Override
    public ResponseResult register(RegisterRequest request) {
        // 1. 检查用户名是否已存在
        if (checkUsernameExists(request.getUsername())) {
            return ResponseResult.error("用户名已存在");
        }

        // 2. 根据角色类型执行不同的注册逻辑
        try {
            if (request.getRole() == UserRole.ADMIN) {
                return registerAdmin(request);
            }else if (request.getRole() == UserRole.MERCHANT) {
                return registerMerchant(request);
            }else if (request.getRole() == UserRole.USER) { // 新增普通用户分支
                return registerUser(request);
            }
            else {
                return ResponseResult.error("不支持的角色类型");
            }
        } catch (Exception e) {
            return ResponseResult.error("注册失败: " + e.getMessage());
        }
    }

    private boolean checkUsernameExists(String username) {
        // 查询 admin 和 merchant 表
        Admin admin = adminMapper.findByUsername(username);
        Merchant merchant = merchantMapper.findByUsername(username);
        User user = userDao.findUserByUsername(username); // 新增用户检查
        return admin != null || merchant != null || user != null;
    }

    private ResponseResult registerAdmin(RegisterRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword())); // 加密存储
        admin.setPhone(request.getPhone());
        admin.setCreateTime(new Date());

        adminMapper.insert(admin);
        return ResponseResult.success("管理员注册成功");
    }

    private ResponseResult registerMerchant(RegisterRequest request) {
        Merchant merchant = new Merchant();
        merchant.setUsername(request.getUsername());
        merchant.setPassword(passwordEncoder.encode(request.getPassword())); // 加密存储
        merchant.setShopName(request.getShopName());
        merchant.setContactName(request.getContactName());
        merchant.setPhone(request.getPhone()); // 修改为 setPhone
        merchant.setAddress(request.getAddress());
        merchant.setStatus(1); // 默认启用
        merchant.setCreateTime(new Date());

        merchantMapper.insert(merchant);
        return ResponseResult.success("商家注册成功");
    }
    private ResponseResult registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname()); // 假设 RegisterRequest 有 nickname 字段
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        user.setGender(request.getGender());
        user.setBirthday(request.getBirthday());
        user.setStatus(1); // 默认启用
        user.setCreateTime(new Date());

        userDao.insertUser(user); // 需要在 UserDao 中添加此方法
        return ResponseResult.success("用户注册成功");
    }
}