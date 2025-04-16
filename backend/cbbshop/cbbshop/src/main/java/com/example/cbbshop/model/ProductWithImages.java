package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductWithImages {
    // Getter 和 Setter 方法

    private Product product;
    private List<String> imagePaths; // 存储商品图片路径


}
