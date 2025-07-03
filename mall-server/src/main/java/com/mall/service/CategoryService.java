package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    // 分页查询商品分类
    PageInfo<Category> findCategoriesByPage(
            Integer pageNum,
            Integer pageSize,
            Integer type
    );
    // 查询所有商品分类
    List<Category> findAllCategories();

    // 删除分类
    boolean deleteCategoryById(Integer categoryId);
    // 新增分类
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    Category getCategoryById(Integer categoryId);
    /**
     * 获取指定分类及其所有子分类的ID
     * @param categoryId 分类 ID
     * @return 分类ID列表
     */
    List<Integer> getCategoryAndChildrenIds(Integer categoryId);
    /**
     * 获取一级分类及其子分类的总销量
     * @return 分类 ID 和总销量的映射列表
     */
    List<Map<String, Object>> getTotalSalesByFirstLevelCategory();

}