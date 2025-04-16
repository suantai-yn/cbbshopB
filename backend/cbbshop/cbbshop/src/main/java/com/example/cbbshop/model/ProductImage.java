package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {
    private Integer imageId; // 图片ID
    private Integer productId; // 关联的商品ID
    private String imagePath; // 图片路径
    private String createdAt;

    

    public ProductImage() {

    }
}