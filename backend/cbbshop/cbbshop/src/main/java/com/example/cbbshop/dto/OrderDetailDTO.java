package com.example.cbbshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private Integer orderId;          // 订单编号
    private String buyerName;         // 买家名称
    private String deliveryAddress;   // 送货地址
    private String contactInfo;       // 联系方式
    private Double totalPrice;        // 总价
    private String status;            // 状态
    private String orderDate;         // 下单时间

}
