package com.mall.service;

import com.mall.entity.*;
import lombok.Data;

public interface AuthService {
    // 用户认证方法
    AuthResult authenticate(String username, String password, UserRole role);

    // 登录成功后的令牌生成
    String generateToken(Integer userId, UserRole role);

    // 令牌验证
    AuthInfo validateToken(String token);
}
