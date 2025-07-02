package com.mall.service;

import com.mall.entity.Product;

import java.util.List;

public interface ProductService {
    boolean insertProduct(Product product);
    // 分页查询商品
    List<Product> findProductsByPage(Integer pageNum, Integer pageSize);
    // 获取商品总条数
    int getTotalCount();
    // 根据商品名称和状态分页查询商品
    List<Product> findProductsByPageAndCondition(Integer pageNum, Integer pageSize, String name, Integer status, Integer merchantId);
    // 根据商品名称和状态获取总条数
    int getTotalCountByCondition(String name, Integer status, Integer merchantId);
    // 删除商品
    boolean deleteProductById(Integer productId);
    // 更新商品
    boolean updateProduct(Product product);
    // 根据商品 ID 查询商品信息
    Product getProductById(Integer productId);
}
