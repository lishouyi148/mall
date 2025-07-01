package com.mall.entity;

public class DataEvent<T> {
//    响应编码，和前端约定一个数字，他请求成功就返回该数字，失败返回其他数字
//    用网络响应编码
//    200 成功
//    400 参数绑定失败
//    404 找不到目标资源
//    500 服务器内部错误
//    405 请求方式错误
private  Integer code;
    //    提示信息
    private  String message;
    //    响应数据
    private T data;

   // 新增总条数字段
   private Integer total;
    public DataEvent(Integer code, String message, T data,Integer total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
