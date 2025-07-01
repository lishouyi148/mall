package com.mall.entity;

import lombok.Data;

@Data
public class PageRequest {
    // 当前页码（默认第一页）
    private Integer pageNum = 1;
    // 每页条数（默认10条）
    private Integer pageSize = 10;
}