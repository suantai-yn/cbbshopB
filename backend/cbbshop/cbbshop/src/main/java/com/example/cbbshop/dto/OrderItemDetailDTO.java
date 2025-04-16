package com.example.cbbshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDetailDTO {

    private String name;      // 商品名称
    private Integer quantity;        // 商品数量
    private Double unitPrice;        // 单价

}
