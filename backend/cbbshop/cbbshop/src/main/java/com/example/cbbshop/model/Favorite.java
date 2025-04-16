package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Favorite {
    private Integer favoriteId; // 收藏ID
    private Integer buyerId;    // 买家ID
    private Integer productId;  // 商品ID
    private Date addedDate;     // 添加日期

    // 构造函数
    public Favorite() {
        this.addedDate = new Date(); // 默认添加当前时间
    }

    public Favorite(Integer buyerId, Integer productId) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.addedDate = new Date(); // 默认添加当前时间
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId=" + favoriteId +
                ", buyerId=" + buyerId +
                ", productId=" + productId +
                ", addedDate=" + addedDate +
                '}';
    }
}