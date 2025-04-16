package com.example.cbbshop.mapper;

import com.example.cbbshop.model.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    // 插入订单项
    @Insert("INSERT INTO OrderItems (order_id, product_id, quantity, unit_price) " +
            "VALUES (#{orderId}, #{productId}, #{quantity}, #{unitPrice})")
    void createOrderItem(OrderItem orderItem);

    // 根据订单ID查询订单项
    @Select("SELECT * FROM OrderItems WHERE order_id = #{orderId}")
    @Results({
            @Result(property = "orderItemId",  column = "order_item_id "), // 假设数据库的列名是 "order_id"
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "unitPrice", column = "unit_Price"),
    })
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);
}
