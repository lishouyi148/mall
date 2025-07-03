package com.mall.service;

import com.mall.entity.Product;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据商品ID查询商品信息
     * @param productId 商品ID
     * @return 商品实体对象
     */
    Product getProductById(Integer productId);
    /**
     * 根据分类 ID 查询商品名称
     * @param categoryId 分类 ID
     * @return 商品名称列表
     */
    List<String> findProductNamesByCategoryId(Integer categoryId);
    /**
     * 根据分类ID列表查询商品
     * @param categoryIds 分类ID列表
     * @return 商品列表
     */
    List<Product> findProductsByCategoryIds(List<Integer> categoryIds);

    /**
     * 查询不同价格区间的总销量
     * @return 价格区间和总销量的映射列表
     */
    List<Map<String, Object>> getSalesByPriceRange();
}
