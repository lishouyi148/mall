package com.mall.dao;

import com.mall.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // 根据条件分页查询商品分类
    List<Category> findCategoriesByPageAndCondition(
            @Param("type") Integer type
    );

    // 根据条件获取商品分类总条数
    int getTotalCountByCondition(@Param("type") Integer type);

    // 查询所有商品分类
    List<Category> findAllCategories();
    // 删除分类
    int deleteCategoryById(@Param("categoryId") Integer categoryId);
    // 新增分类
    int insertCategory(Category category);
    int updateCategory(Category category);
    Category getCategoryById(@Param("categoryId") Integer categoryId);
}