package com.mall.entity;

import lombok.Data;

//基础用户类
@Data
public class BaseUser {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private java.util.Date createTime;
    private java.util.Date updateTime;
}

