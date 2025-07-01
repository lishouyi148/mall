// com.mall.service.UserService.java
package com.mall.service;

import com.mall.entity.RegisterRequest;
import com.mall.entity.ResponseResult;

public interface UserService {
    ResponseResult register(RegisterRequest request);
}
