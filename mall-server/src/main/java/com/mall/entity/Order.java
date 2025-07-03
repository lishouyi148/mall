package com.mall.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Order {
    private Long orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal currentPrice;
    private BigDecimal paymentAmount;
    private String userName;
    private Integer paymentType;
    private BigDecimal shippingFee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shippingTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;
    private String note;
    private Integer status;
    private String shippingAddress;
}