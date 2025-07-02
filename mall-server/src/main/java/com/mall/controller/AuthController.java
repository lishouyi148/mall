package com.mall.controller;

import com.mall.entity.AuthResult;
import com.mall.entity.RegisterRequest;
import com.mall.entity.ResponseResult;
import com.mall.entity.UserRole;
import com.mall.service.AuthService;
import com.mall.service.UserService;
import com.mall.service.UsersystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthResult login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        String roleStr = loginInfo.get("role");

        UserRole role = UserRole.valueOf(roleStr.toUpperCase());
        AuthResult result = authService.authenticate(username, password, role);
        System.out.println("登录返回结果: " + result); // 添加日志输出
        return result;
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}