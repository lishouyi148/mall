package com.mall.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String avatar;
    private Integer gender;
    private Date birthday;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}