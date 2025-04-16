package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CartItem {
    private Integer cartId;      // 购物车ID
    private Integer buyerId;     // 买家ID
    private Integer productId;   // 商品ID
    private Integer quantity;     // 商品数量
    private Date addedDate;      // 添加日期

    // 构造函数
    public CartItem() {
        this.addedDate = new Date(); // 默认添加当前时间
    }

    public CartItem(Integer buyerId, Integer productId, Integer quantity) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedDate = new Date(); // 默认添加当前时间
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartId=" + cartId +
                ", buyerId=" + buyerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", addedDate=" + addedDate +
                '}';
    }
}