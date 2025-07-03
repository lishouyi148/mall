package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.dao.CategoryMapper;
import com.mall.dao.ProductMapper;
import com.mall.entity.Category;
import com.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public PageInfo<Category> findCategoriesByPage(
            Integer pageNum,
            Integer pageSize,
            Integer type
    ) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = Integer.MAX_VALUE;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categories = categoryMapper.findCategoriesByPageAndCondition(
                type
        );
        return new PageInfo<>(categories);
    }
    @Override
    public List<Category> findAllCategories() {
        return categoryMapper.findAllCategories();
    }
    @Override
    public boolean deleteCategoryById(Integer categoryId) {
        int result = categoryMapper.deleteCategoryById(categoryId);
        return result > 0;
    }
    @Override
    public boolean addCategory(Category category) {
        int result = categoryMapper.insertCategory(category);
        return result > 0;
    }
    @Override
    public boolean updateCategory(Category category) {
        int result = categoryMapper.updateCategory(category);
        return result > 0;
    }
    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryMapper.getCategoryById(categoryId);
    }
    @Override
    public List<Integer> getCategoryAndChildrenIds(Integer categoryId) {
        List<Integer> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId); // 添加一级分类ID

        // 获取所有子分类
        List<Category> children = categoryMapper.getChildrenByParentId(categoryId);
        for (Category child : children) {
            categoryIds.add(child.getCategoryId());
        }

        return categoryIds;
    }
    @Override
    public List<Map<String, Object>> getTotalSalesByFirstLevelCategory() {
        List<Map<String, Object>> result = new ArrayList<>();
        // 获取所有一级分类
        List<Category> firstLevelCategories = categoryMapper.findCategoriesByPageAndCondition(1);
        for (Category firstLevelCategory : firstLevelCategories) {
            Integer firstLevelCategoryId = firstLevelCategory.getCategoryId();
            // 获取一级分类下的所有二级分类 ID
            List<Integer> secondLevelCategoryIds = new ArrayList<>();
            List<Category> secondLevelCategories = categoryMapper.getChildrenByParentId(firstLevelCategoryId);
            for (Category secondLevelCategory : secondLevelCategories) {
                secondLevelCategoryIds.add(secondLevelCategory.getCategoryId());
            }
            // 统计二级分类下商品的总销量
            int totalSales = 0;
            if (!secondLevelCategoryIds.isEmpty()) {
                List<Map<String, Object>> salesMap = productMapper.getTotalSalesByCategoryIds(secondLevelCategoryIds);
                for (Map<String, Object> map : salesMap) {
                    totalSales += ((Number) map.get("total_sales")).intValue();
                }
            }
            Map<String, Object> item = new HashMap<>();
            item.put("category_id", firstLevelCategoryId);
            item.put("total_sales", totalSales);
            item.put("category_name", firstLevelCategory.getName()); // 添加一级分类的名字
            result.add(item);
        }
        return result;
    }

}