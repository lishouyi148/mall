package com.mall.entity;

import lombok.Data;

// 认证信息类
@Data
public class AuthInfo {
    private Integer userId;
    private UserRole role;
    private boolean valid;
    // 新增message字段用于错误提示
    private String message;
}
