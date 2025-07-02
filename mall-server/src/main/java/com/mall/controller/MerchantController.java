package com.mall.controller;

import com.mall.entity.Merchant;
import com.mall.entity.ResponseResult;
import com.mall.dao.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 查询所有商家
     * @return 商家列表
     */
    @GetMapping
    public ResponseResult<List<Merchant>> getAllMerchants() {
        List<Merchant> merchants = merchantMapper.findAll();
        return ResponseResult.success(merchants);
    }

    /**
     * 新增商家
     * @param merchant 商家信息（包含username、password、shopName等）
     * @return 操作结果
     */
    @PostMapping
    public ResponseResult<String> addMerchant(@RequestBody Merchant merchant) {
        try {
            // 1. 校验用户名唯一性（原逻辑保留）
            Merchant existing = merchantMapper.findByUsername(merchant.getUsername());
            if (existing != null) {
                return ResponseResult.error("用户名已存在");
            }

            // 2. 新增：加密密码（核心修改）
            String rawPassword = merchant.getPassword(); // 前端传入的原始密码
            String encodedPassword = passwordEncoder.encode(rawPassword); // 加密处理
            merchant.setPassword(encodedPassword); // 替换为加密后的密码

            // 3. 设置默认值（原逻辑保留）
            merchant.setStatus(1);
            merchant.setCreateTime(new Date());

            // 4. 插入数据库（此时存储的是加密密码）
            merchantMapper.insert(merchant);
            return ResponseResult.success("商家新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("新增失败：" + e.getMessage());
        }
    }

    /**
     * 更新商家信息
     * @param id 商家主键id
     * @param merchant 新的商家信息
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public ResponseResult<String> updateMerchant(
            @PathVariable Integer id,
            @RequestBody Merchant merchant
    ) {
        try {
            // 1. 校验商家是否存在
            Merchant existing = merchantMapper.findById(id);
            if (existing == null) {
                return ResponseResult.error("商家不存在");
            }

            // 2. 设置主键id（确保更新的是指定商家）
            merchant.setId(id);

            // 3. 执行更新
            merchantMapper.update(merchant);
            return ResponseResult.success("商家更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除商家
     * @param id 商家主键id
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseResult<String> deleteMerchant(@PathVariable Integer id) {
        try {
            // 1. 校验商家是否存在
            Merchant merchant = merchantMapper.findById(id);
            if (merchant == null) {
                return ResponseResult.error("商家不存在");
            }

            // 2. 执行删除（会触发product表的外键级联删除）
            merchantMapper.deleteById(id);
            return ResponseResult.success("商家删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 按id查询单个商家
     * @param id 主键id
     * @return 商家详情
     */
    @GetMapping("/{id}")
    public ResponseResult<Merchant> getMerchantById(@PathVariable Integer id) {
        Merchant merchant = merchantMapper.findById(id);
        if (merchant == null) {
            return ResponseResult.error("商家不存在");
        }
        return ResponseResult.success(merchant);
    }
}