package com.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;



@Data
public class Product {

    /**
     * 商品ID
     */

    private Integer product_id;

    /**
     * 所属商家ID（外键）
     */

    private Integer merchant_id;

    /**
     * 分类ID（外键）
     */

    private Integer category_id;

    /**
     * 商品名称
     */

    private String name;

    /**
     * 商品副标题
     */

    private String subtitle;

    /**
     * 主图URL
     */

    private String main_image;

    /**
     * 子图URL（多个用逗号分隔）
     */

    private String sub_images;

    /**
     * 商品详情
     */

    private String detail;

    /**
     * 价格
     */

    private BigDecimal price;

    /**
     * 库存
     */

    private Integer stock;

    /**
     * 状态（1-上架，0-下架）
     */

    private Integer status;

    /**
     * 销量
     */

    private Integer sales;

    /**
     * 创建者类型（1-管理员，2-商家）
     */

    private Integer created_by;

    /**
     * 创建者ID（管理员ID或商家ID）
     */

    private Integer creator_id;

    /**
     * 创建时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 分类名称（非数据库字段，用于关联查询）
     */
    @Transient
    private String category_name;

    /**
     * 商家名称（非数据库字段，用于关联查询）
     */
    @Transient
    private String merchantName;
}
