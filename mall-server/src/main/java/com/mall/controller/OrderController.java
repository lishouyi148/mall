package com.mall.controller;

import com.github.pagehelper.PageInfo;
import com.mall.entity.Order;
import com.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 分页获取所有订单
    @GetMapping
    public ResponseEntity<PageInfo<Order>> getAllOrders(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Order> pageInfo = orderService.getAllOrders(pageNum, pageSize);
        return ResponseEntity.ok(pageInfo);
    }

    // 根据订单ID查询订单
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // 新增订单
    @PostMapping
    public ResponseEntity<Integer> addOrder(@RequestBody Order order) {
        int result = orderService.addOrder(order);
        return result > 0 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    // 删除订单
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Integer> deleteOrder(@PathVariable Long orderId) {
        int result = orderService.deleteOrder(orderId);
        return result > 0 ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // 更新订单
    @PutMapping("/{orderId}")
    public ResponseEntity<Integer> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        order.setOrderId(orderId);
        int result = orderService.updateOrder(order);
        return result > 0 ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}