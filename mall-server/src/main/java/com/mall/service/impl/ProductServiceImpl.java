package com.mall.service.impl;

import com.mall.dao.ProductMapper;
import com.mall.entity.Product;
import com.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
   private ProductMapper productMapper ;
    @Override
   public boolean insertProduct(Product product) {
      int result = productMapper.insertProduct(product);
        return result > 0;
    }
    @Override
    public List<Product> findProductsByPage(Integer pageNum, Integer pageSize) {
        // 计算分页起始位置（MyBatis分页从0开始）
        int start = (pageNum - 1) * pageSize;
        return productMapper.findProductsByPage(start, pageSize);
    }
    @Override
    public int getTotalCount() {
        return productMapper.getTotalCount();
    }
    @Override
    public List<Product> findProductsByPageAndCondition(Integer pageNum, Integer pageSize, String name, Integer status, Integer merchantId) {
        int start = (pageNum - 1) * pageSize;
        return productMapper.findProductsByPageAndCondition(start, pageSize, name, status,merchantId);
    }

    @Override
    public int getTotalCountByCondition(String name, Integer status, Integer merchantId) {
        return productMapper.getTotalCountByCondition(name, status,merchantId);
    }

    @Override
    public boolean deleteProductById(Integer productId) {
        int result = productMapper.deleteProductById(productId);
        return result > 0;
    }

    @Override
    public boolean updateProduct(Product product) {
        int result = productMapper.updateProduct(product);
        return result > 0;
    }
}
