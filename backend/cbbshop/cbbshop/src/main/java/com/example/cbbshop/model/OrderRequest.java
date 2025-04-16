package com.example.cbbshop.model;

import java.util.List;

public class OrderRequest {
    private Order order;                // 订单信息
    private List<OrderItem> orderItems; // 订单项列表

    // Getter and Setter Methods
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
