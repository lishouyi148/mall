package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.dao.CategoryMapper;
import com.mall.entity.Category;
import com.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

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
}