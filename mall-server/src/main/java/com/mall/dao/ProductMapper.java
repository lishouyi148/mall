package com.mall.dao;

import com.mall.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductMapper {
    public int insertProduct(Product product);
    // 分页查询商品
    List<Product> findProductsByPage(int start, int pageSize);
    // 获取总条数
    int getTotalCount();
    // 根据商品名称和状态分页查询商品
    List<Product> findProductsByPageAndCondition(int start, int pageSize, String name, Integer status,Integer merchantId);
    // 根据商品名称和状态获取总条数
    int getTotalCountByCondition(String name, Integer status, Integer merchantId);
    // 删除商品
    int deleteProductById(Integer productId);
    // 更新商品
    int updateProduct(Product product);
    // 根据商品 ID 查询商品信息
    Product getProductById(Integer productId);
}
