package com.mall.entity;

import lombok.Data;

import java.util.Date;

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

    // 新增普通用户可能需要的字段
    private String nickname;
    private String email;
    private String avatar;
    private Integer gender;
    private Date birthday;
}