package com.mall.dao;

import com.mall.entity.Product;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据商品ID查询商品信息
     * @param productId 商品ID
     * @return 商品实体对象
     */
    Product selectById(Integer productId);
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
    List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);
}
