package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter




public class Product {

    private Integer productId;
    //private Integer sellerId;
    private Integer categoryId;
    private String name;
    //private String description;
    private double price;
    private int stock;
    private String status;

    // 新增字段：类别名称
    private String categoryName;
    // 新增字段：父类ID
    private Integer parentId;
    // 新增字段：父类名称
    private String parentCategoryName;

    // Getters and Setters
}
