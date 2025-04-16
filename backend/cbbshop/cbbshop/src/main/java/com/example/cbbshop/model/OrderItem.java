package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderItem {
    private Integer orderItemId;      // 订单项ID
    private Integer orderId;          // 订单ID
    private Integer productId;        // 商品ID
    private Integer quantity;         // 商品数量
    private Double unitPrice;         // 商品单价
}
