package com.mall.service.impl;

import com.mall.dao.UserMapper;
import com.mall.entity.ResponseResult;
import com.mall.entity.User;
import com.mall.service.UsersystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsersystemServiceImpl implements UsersystemService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult<List<User>> getAllUsers() {
        List<User> users = userMapper.findAll();
        return ResponseResult.success(users);
    }

    @Override
    public ResponseResult<String> addUser(User user) {
        try {
            // 1. 校验用户名唯一性
            User existing = userMapper.findByUsername(user.getUsername());
            if (existing != null) {
                return ResponseResult.error("用户名已存在");
            }

            // 2. 加密密码
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // 3. 设置默认值
            user.setStatus(1);
            user.setCreateTime(new Date());

            // 4. 插入数据库
            userMapper.insert(user);
            return ResponseResult.success("用户新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("新增失败：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult<String> updateUser(Integer id, User user) {
        try {
            // 1. 校验用户是否存在（参数改为Integer）
            User existing = userMapper.findById(id);
            if (existing == null) {
                return ResponseResult.error("用户不存在");
            }

            // 2. 设置主键id（Integer类型，与User类的setId匹配）
            user.setId(id);

            // 3. 加密密码
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(existing.getPassword());
            }

            // 4. 执行更新
            userMapper.update(user);
            return ResponseResult.success("用户更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("更新失败：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult<String> deleteUser(Integer id) {
        try {
            // 1. 校验用户是否存在（参数改为Integer）
            User user = userMapper.findById(id);
            if (user == null) {
                return ResponseResult.error("用户不存在");
            }

            // 2. 执行删除（参数改为Integer）
            userMapper.deleteById(id);
            return ResponseResult.success("用户删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("删除失败：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult<User> getUserById(Integer id) {
        // 参数改为Integer，与User类id类型匹配
        User user = userMapper.findById(id);
        if (user == null) {
            return ResponseResult.error("用户不存在");
        }
        return ResponseResult.success(user);
    }
}
