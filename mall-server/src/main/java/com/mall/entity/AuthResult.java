package com.mall.entity;

import lombok.Data;

@Data
public class AuthResult {
    private boolean success;      // 认证是否成功
    private String message;       // 消息描述
    private String tokenType;     // Token类型（如"Bearer"）
    private Integer userId;       // 用户ID
    private UserRole role;        // 用户角色
    private String token;         // JWT令牌
    private Object userInfo;      // 可选：用户详细信息
}
