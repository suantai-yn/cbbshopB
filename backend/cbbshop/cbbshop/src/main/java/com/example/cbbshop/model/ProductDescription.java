package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDescription {
    private Integer id;
    private Integer productId;
    private String content;
    private String createdAt;
}
