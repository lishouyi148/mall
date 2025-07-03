package com.mall.entity;

import lombok.Data;

@Data
public class AgeGroupCountDTO {
    private String ageGroup; // 年龄段（如"1-25"）
    private Integer count;   // 该年龄段的用户数量
}