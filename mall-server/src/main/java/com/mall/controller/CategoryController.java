package com.mall.controller;

import com.github.pagehelper.PageInfo;
import com.mall.entity.Category;
import com.mall.entity.DataEvent;
import com.mall.entity.Product;
import com.mall.service.CategoryService;
import com.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    // 分页查询商品分类
    @GetMapping("/list")
    public ResponseEntity<PageInfo<Category>> getCategories(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer type
    ) {
        PageInfo<Category> pageInfo = categoryService.findCategoriesByPage(pageNum, pageSize, type);
        return ResponseEntity.ok(pageInfo);
    }
    // 查询所有商品分类
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }
    // 删除分类
    @DeleteMapping("/{categoryId}")
    public DataEvent<String> deleteCategory(@PathVariable Integer categoryId) {
        try {
            boolean result = categoryService.deleteCategoryById(categoryId);
            if (result) {
                return new DataEvent<>(200, "分类删除成功", "success", 1);
            } else {
                return new DataEvent<>(500, "分类删除失败", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "分类删除异常", null, 0);
        }
    }
    // 新增分类
    @PostMapping("/add")
    public DataEvent<String> addCategory(@RequestBody Category category) {
        try {
            boolean result = categoryService.addCategory(category);
            if (result) {
                return new DataEvent<>(200, "分类新增成功", "success", 1);
            } else {
                return new DataEvent<>(500, "分类新增失败", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "分类新增异常", null, 0);
        }
    }
    // 更新分类
    @PutMapping("/{categoryId}")
    public DataEvent<String> updateCategory(@PathVariable Integer categoryId, @RequestBody Category category) {
        try {
            category.setCategoryId(categoryId);
            boolean result = categoryService.updateCategory(category);
            if (result) {
                return new DataEvent<>(200, "分类更新成功", "success", 1);
            } else {
                return new DataEvent<>(500, "分类更新失败", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "分类更新异常", null, 0);
        }
    }
    @GetMapping("/{categoryId}")
    public DataEvent<Category> getCategoryById(@PathVariable Integer categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                return new DataEvent<>(200, "查询成功", category, 1);
            } else {
                return new DataEvent<>(404, "分类不存在", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询异常", null, 0);
        }
    }
    /**
     * 根据分类 ID 获取该分类下的商品名称
     * @param categoryId 分类 ID
     * @return 商品名称列表
     */
    @GetMapping("/{categoryId}/products")
    public DataEvent<List<String>> getProductsByCategoryId(@PathVariable Integer categoryId) {
        try {
            List<String> productNames = productService.findProductNamesByCategoryId(categoryId);
            return new DataEvent<>(200, "查询成功", productNames, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询异常", null, 0);
        }
    }
    /**
     * 根据一级分类 ID 获取该分类及其所有子分类下的商品
     * @param categoryId 一级分类 ID
     * @return 商品列表
     */
    @GetMapping("/{categoryId}/all-products")
    public DataEvent<List<Product>> getProductsByParentCategoryId(@PathVariable Integer categoryId) {
        try {
            // 获取该一级分类及其所有子分类的ID
            List<Integer> categoryIds = categoryService.getCategoryAndChildrenIds(categoryId);

            // 根据分类ID列表查询商品
            List<Product> products = productService.findProductsByCategoryIds(categoryIds);

            return new DataEvent<>(200, "查询成功", products, products.size());
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询异常", null, 0);
        }
    }
}