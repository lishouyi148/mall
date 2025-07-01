package com.mall.entity;

import lombok.Data;

// 商家实体类
@Data
public class Merchant extends BaseUser {
    private String shopName;
    private String contactName;
    private String contactPhone;
    private String address;
    private Integer status;
}