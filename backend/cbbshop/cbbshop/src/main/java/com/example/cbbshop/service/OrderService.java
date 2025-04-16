package com.example.cbbshop.service;

import com.example.cbbshop.dto.OrderDetailDTO;
import com.example.cbbshop.dto.OrderItemDetailDTO;
import com.example.cbbshop.mapper.OrderItemMapper;
import com.example.cbbshop.mapper.OrderMapper;
import com.example.cbbshop.mapper.ProductMapper;
import com.example.cbbshop.mapper.UserMapper;
import com.example.cbbshop.model.Order;
import com.example.cbbshop.model.OrderItem;
import com.example.cbbshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    private final OrderItemMapper orderItemMapper;
    @Autowired
    public OrderService(OrderMapper orderMapper, UserMapper userMapper,ProductMapper productMapper,OrderItemMapper orderItemMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.productMapper=productMapper;
        this.orderItemMapper=orderItemMapper;

    }



    // 创建订单
    @Transactional
    public void createOrder(Order order, List<OrderItem> orderItems) {
        // 1. 检查库存
        for (OrderItem orderItem : orderItems) {
            Product product = productMapper.findById(orderItem.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("商品不存在.");
            }
            // 如果商品库存不足
            if (product.getStock() < orderItem.getQuantity()) {
                throw new IllegalArgumentException("商品 " + product.getName() + " 库存不足");
            }
        }

        // 2. 创建订单
        order.setOrderDate(LocalDateTime.now().toString());  // 设置订单日期为当前时间
        orderMapper.createOrder(order);

        // 3. 插入订单项
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getOrderId());  // 设置订单ID
            orderItemMapper.createOrderItem(orderItem);

            // 4. 更新商品库存
            Product product = productMapper.findById(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();; // 减少库存
            productMapper.updateProductStock(product.getProductId(),stock); // 更新商品库存
        }

    }

    // 更新订单状态
    @Transactional
    public String updateOrderStatus(Integer orderId, String newStatus) {
        Order order = orderMapper.findOrderById(orderId);
        if (order == null) {
            return "订单不存在";
        }

        String currentStatus = order.getStatus();

        // 确保状态变更符合规则
        switch (currentStatus) {
            case "customer_ordered":
                if ("seller_confirmed".equals(newStatus)) {
                    orderMapper.updateOrderStatus(orderId, newStatus);
                    return "商家已确认订单";
                }
                break;
            case "seller_confirmed":
                if ("stock_prepared".equals(newStatus)) {
                    orderMapper.updateOrderStatus(orderId, newStatus);
                    return "商家已备货完成";
                }
                break;
            case "stock_prepared":
                if ("shipped".equals(newStatus)) {
                    orderMapper.updateOrderStatus(orderId, newStatus);
                    return "商家已开始发货";
                }
                break;
            case "shipped":
                if ("completed".equals(newStatus)) {
                    orderMapper.updateOrderStatus(orderId, newStatus);
                    return "交易完成，订单交付给客户";
                }
                break;
            case "completed":
                return "订单已完成，无法再次修改";
            case "cancelled":
                return "订单已取消，无法修改";
            default:
                return "无效状态变更";
        }

        return "状态变更失败";
    }

    // 取消订单
    @Transactional
    public String cancelOrder(Integer orderId, String role) {
        Order order = orderMapper.findOrderById(orderId);
        if (order == null) {
            return "订单不存在";
        }

        // 检查订单状态，只有在发货前才能取消订单
        if ("completed".equals(order.getStatus()) || "cancelled".equals(order.getStatus())) {
            return "订单已完成或已取消，无法取消";
        }

        // 买家取消订单：只有在开始发货前才能取消
        if (role.equals("buyer") && "shipped".equals(order.getStatus())) {
            return "订单已发货，无法取消";
        }

        // 商家取消订单：任何状态下都可以取消
        if (role.equals("seller")) {
            // 1. 取消订单前，恢复库存
            List<OrderItem> orderItems = orderItemMapper.findOrderItemsByOrderId(orderId);
            for (OrderItem orderItem : orderItems) {
                Product product = productMapper.findById(orderItem.getProductId());
                if (product != null) {
                    // 恢复库存
                    int stock=product.getStock() + orderItem.getQuantity();
                    productMapper.updateProductStock(product.getProductId(),stock);  // 更新库存
                }
            }

            // 2. 更新订单状态为取消
            orderMapper.updateOrderStatus(orderId, "cancelled");
            return "订单已取消";
        }

        // 如果是买家取消订单：判断订单是否已经发货
        if (role.equals("buyer")) {
            if ("shipped".equals(order.getStatus())) {
                return "订单已发货，无法取消";
            }

            // 恢复库存
            List<OrderItem> orderItems = orderItemMapper.findOrderItemsByOrderId(orderId);
            for (OrderItem orderItem : orderItems) {
                Product product = productMapper.findById(orderItem.getProductId());
                if (product != null) {
                    // 恢复库存
                    int stock=product.getStock() + orderItem.getQuantity();
                    productMapper.updateProductStock(product.getProductId(),stock);  // 更新库存
                }
            }

            // 更新订单状态为取消
            orderMapper.updateOrderStatus(orderId, "cancelled");
            return "订单已取消";
        }

        return "无法取消订单";
    }

    // 根据订单ID查询订单项
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        return orderItemMapper.findOrderItemsByOrderId(orderId);
    }


    // 查询某个买家的所有订单
// 查询订单详情
    public List<OrderDetailDTO> getOrderDetails(Integer orderId) {
        return orderMapper.findOrderDetailsById(orderId);
    }



    // 获取所有订单
    public List<Order> getAllOrders() {
        return orderMapper.findAllOrders();
    }

    //根据订单id获取订单

    public Order getOrderById(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    // 查询订单详情
    public List<OrderDetailDTO> getOrderDetails() {
        return orderMapper.findOrderDetails();
    }

    // 查询订单项详情
    public List<OrderItemDetailDTO> getOrderItems(Integer orderId) {
        return orderMapper.findOrderItemsByOrderId(orderId);
    }

    //买家完成订单
    public String confirmReceive(Integer orderId) {
        // 获取订单
        Order order = orderMapper.getOrderById(orderId);

        if (order == null) {
            return "Order not found";  // 如果订单不存在，返回错误信息
        }

        // 检查订单状态是否为 'shipped'，如果不是，则买家不能确认收货
        if (!order.getStatus().equals("shipped")) {
            return "Order not shipped yet, cannot confirm receipt";  // 订单状态不符合，返回错误信息
        }

        // 更新订单状态为 'completed'，表示订单已完成
        orderMapper.updateOrderStatus(orderId,"completed");


        return "Order successfully completed";  // 返回成功消息
    }
}
