package com.mall.entity;

import lombok.Data;

// 统一响应结果实体类（改为泛型类）
@Data
public class ResponseResult<T> {
    private boolean success;
    private String message;
    private T data; // 使用泛型类型 T

    // 静态工厂方法也需要支持泛型
    public static <T> ResponseResult<T> success() {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(true);
        result.setMessage("操作成功");
        return result;
    }

    public static <T> ResponseResult<T> success(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(true);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> error(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
