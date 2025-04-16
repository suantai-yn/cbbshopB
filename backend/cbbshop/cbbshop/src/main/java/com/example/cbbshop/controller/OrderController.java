package com.example.cbbshop.controller;

import com.example.cbbshop.dto.OrderDetailDTO;
import com.example.cbbshop.dto.OrderItemDetailDTO;
import com.example.cbbshop.model.Order;
import com.example.cbbshop.model.OrderItem;
import com.example.cbbshop.model.OrderRequest;
import com.example.cbbshop.service.OrderService;
import com.example.cbbshop.service.UserService;
import com.example.cbbshop.utils.ApiResponse;
import com.example.cbbshop.utils.OrderValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // 创建订单（包括订单项）
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        // 从请求中提取订单信息
        Order order = orderRequest.getOrder();
        List<OrderItem> orderItems = orderRequest.getOrderItems();
        // 创建订单
        orderService.createOrder(order, orderItems);

        return ResponseEntity.ok(new ApiResponse(true, "Order create successfully"));
    }

    // 更新订单状态接口
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String newStatus) {
        // 调用 service 层的更新订单状态方法
        String result = orderService.updateOrderStatus(orderId, newStatus);

        // 根据返回的状态信息进行响应
        if ("状态变更失败".equals(result) || result.contains("无法修改")) {
            return ResponseEntity.badRequest().body(result);  // 返回 400 错误
        }
        return ResponseEntity.ok(result);  // 返回 200 成功响应
    }



    // 取消订单
    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Integer orderId,@RequestParam String role) {
        return orderService.cancelOrder(orderId, role);
    }

    // 买家确认收货（订单状态需为'开始发货'）
    @PutMapping("/{orderId}/confirm-receive")
    public String confirmReceive(@PathVariable Integer orderId, @RequestParam Integer buyerId) {
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            return "Order not found";
        }

        if (!order.getStatus().equals("shipped")) {
            return "Order not shipped yet";
        }

        // 调用服务层确认收货
        return orderService.confirmReceive(orderId);
    }

    // 获取买家的所有订单
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<ApiResponse> getOrdersByBuyerId(@PathVariable Integer buyerId) {
        try {
            List<OrderDetailDTO> orders = orderService.getOrderDetails(buyerId);
            return ResponseEntity.ok(new ApiResponse(true, "Orders fetched successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to fetch orders: " + e.getMessage()));
        }
    }


    // 获取所有订单
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getOrderDetails() {
        try {
            List<OrderDetailDTO> orderDetails = orderService.getOrderDetails();
            return ResponseEntity.ok(new ApiResponse(true, "Orders fetched successfully", orderDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to fetch all orders: " + e.getMessage()));
        }
    }

    // 查询订单项详情接口
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemDetailDTO>> getOrderItems(@PathVariable Integer orderId) {
        List<OrderItemDetailDTO> orderItems = orderService.getOrderItems(orderId);
        if (orderItems.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 未找到
        }
        return ResponseEntity.ok(orderItems); // 返回查询结果
    }
}
