package com.mall.controller;
import com.mall.entity.PageRequest;
import com.mall.entity.DataEvent;
import com.mall.entity.Product;
import com.mall.entity.ResponseResult;
import com.mall.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;

    // 从配置文件中读取上传文件的存储路径
    @Value("${upload.path}")
    private String uploadPath;

    // 文件上传接口
    @PostMapping("/uploadImage")
    public DataEvent<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return new DataEvent<>(400, "上传文件不能为空", null, 0);
            }

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // 创建存储文件的目录
            File dest = new File(uploadPath + newFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            // 保存文件
            file.transferTo(dest);

            // 返回文件的访问路径
            return new DataEvent<>(200, "文件上传成功", "/uploads/" + newFileName, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return new DataEvent<>(500, "文件上传失败", null, 0);
        }
    }


    @PostMapping("/insert")
    public DataEvent<String> insertProduct(@RequestBody Product product) {
        try {
            boolean result = productService.insertProduct(product);
            if (result) {
                return new DataEvent<>(200, "商品插入成功", "success", 1);
            } else {
                return new DataEvent<>(500, "商品插入失败", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "商品插入异常", null, 0);
        }
    }
    @GetMapping("/list")
    public DataEvent<List<Product>> getProductByPage(PageRequest pageRequest) {
        try {
            List<Product> products = productService.findProductsByPage(pageRequest.getPageNum(), pageRequest.getPageSize());
            int total = productService.getTotalCount();
            System.out.println("查询到的商品数量：" + products.size());
            return new DataEvent<>(200, "查询成功", products, total);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询失败", null, 0);
        }
    }
    @GetMapping("/listByCondition")
    public DataEvent<List<Product>> getProductByPageAndCondition(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer merchantId
    ) {
        try {
            List<Product> products = productService.findProductsByPageAndCondition(pageNum, pageSize, name, status,merchantId);
            int total = productService.getTotalCountByCondition(name, status,merchantId);
            System.out.println("查询到的商品数量：" + products.size());
            return new DataEvent<>(200, "查询成功", products, total);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询失败", null, 0);
        }
    }

    // 删除商品
    @DeleteMapping("/{productId}")
    public DataEvent<String> deleteProduct(@PathVariable Integer productId) {
        try {
            boolean result = productService.deleteProductById(productId);
            if (result) {
                return new DataEvent<>(200, "商品删除成功", "success", 1);
            } else {
                return new DataEvent<>(500, "商品删除失败", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "商品删除异常", null, 0);
        }
    }
    // 更新产品
    @PutMapping("/{productId}")
    public DataEvent<String> updateProduct(@PathVariable Integer productId, @RequestBody Product product) {
        try {
            product.setProduct_id(productId);
            boolean result = productService.updateProduct(product);
            if (result) {
                return new DataEvent<>(200, "商品信息更新成功", "success", 1);
            } else {
                return new DataEvent<>(500, "商品信息更新失败", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "商品信息更新异常", null, 0);
        }
    }

    /**
     * 根据商品ID查询商品信息
     * @param productId 商品ID
     * @return 商品信息及状态
     */
    @GetMapping("/{productId}")
    public DataEvent<Product> getProductById(@PathVariable Integer productId) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                return new DataEvent<>(200, "查询成功", product, 1);
            } else {
                return new DataEvent<>(404, "商品不存在", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询异常", null, 0);
        }
    }

    /**
     * 查询不同价格区间的总销量
     * @return 价格区间和总销量的映射列表
     */
    @GetMapping("/price-range-sales")
    public DataEvent<List<Map<String, Object>>> getSalesByPriceRange() {
        try {
            List<Map<String, Object>> data = productService.getSalesByPriceRange();
            return new DataEvent<>(200, "查询成功", data, data.size());
        } catch (Exception e) {
            e.printStackTrace();
            return new DataEvent<>(500, "查询失败", null, 0);
        }
    }
}
