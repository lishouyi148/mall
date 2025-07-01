package com.mall.entity;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String phone;
    private UserRole role; // 注册角色（ADMIN/MERCHANT）

    // 商家额外信息
    private String shopName;
    private String contactName;
    private String contactPhone;
    private String address;
}