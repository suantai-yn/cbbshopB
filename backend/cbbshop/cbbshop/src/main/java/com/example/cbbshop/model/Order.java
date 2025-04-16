package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private Integer orderId;
    private Integer buyerId;
   // private Integer sellerId;
   // private Integer productId;
    private double totalPrice;
    private String orderDate;
    private String status;
    private List<OrderItem> items;    // 订单项列表


    // 新增字段
    //private String productName;         // 商品名称
    //private double productPrice;        // 商品价格
    //private String buyerUsername;       // 购买人用户名
   // private String buyerPhoneNumber;    // 购买人联系电话
  //  private String buyerAddress;        // 购买人地址
    // Getters and Setters
}
