package com.mall.controller;

import com.mall.entity.ResponseResult;
import com.mall.entity.UserAddress;
import com.mall.dao.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class UserAddressController {

    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 获取所有地址
     * @return 地址列表
     */
    @GetMapping
    public ResponseResult<List<UserAddress>> getAllAddresses() {
        try {
            List<UserAddress> addresses = userAddressMapper.findAll();
            return ResponseResult.success(addresses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("获取地址列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据地址ID获取单个地址
     * @param addressId 地址ID
     * @return 单个地址信息
     */
    @GetMapping("/{addressId}")
    public ResponseResult<UserAddress> getAddressById(@PathVariable Long addressId) {
        try {
            UserAddress address = userAddressMapper.findById(addressId);
            if (address == null) {
                return ResponseResult.error("地址不存在");
            }
            return ResponseResult.success(address);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("获取地址失败：" + e.getMessage());
        }
    }
}