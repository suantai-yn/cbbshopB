package com.example.cbbshop.mapper;

import com.example.cbbshop.dto.OrderDetailDTO;
import com.example.cbbshop.dto.OrderItemDetailDTO;
import com.example.cbbshop.model.Order;
import com.example.cbbshop.model.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 插入新订单
    @Insert("INSERT INTO Orders (buyer_id, product_id, product_name, product_price, total_price, order_date, status, buyer_name, contact_info, address) " +
            "VALUES (#{buyerId}, #{productId}, #{productName}, #{productPrice}, #{totalPrice}, #{orderDate}, #{status}, #{buyerUsername}, #{buyerPhoneNumber}, #{buyerAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId") // 自动获取生成的 orderId
    void insertOrder(Order order);

    // 创建订单
    @Insert("INSERT INTO Orders (buyer_id, total_price, status, order_date) " +
            "VALUES (#{buyerId}, #{totalPrice}, #{status}, #{orderDate})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void createOrder(Order order);

    // 创建订单项
    @Insert("INSERT INTO OrderItems (order_id, product_id, quantity, unit_price) " +
            "VALUES (#{orderId}, #{productId}, #{quantity}, #{unitPrice})")
    void createOrderItem(OrderItem orderItem);


    // 根据订单 ID 查询订单
    @Select("SELECT * FROM Orders WHERE order_id = #{orderId}")
    Order getOrderById(@Param("orderId") Integer orderId);
    // 根据买家 ID 获取所有订单
    @Select("SELECT * FROM Orders WHERE buyer_id = #{buyerId}")
    @Results({
            @Result(property = "orderId",  column = "order_id"), // 假设数据库的列名是 "order_id"
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "status", column = "status"),
            @Result(property = " orderDate",column = " order_date")
    })
    List<Order> findOrdersByBuyerId(Integer buyerId);
    // 获取某个买家的所有已完成订单
    @Select("SELECT * FROM Orders WHERE buyer_id = #{buyerId} AND status = 'completed'")
    @Results({
            @Result(property = "orderId",  column = "order_id"), // 假设数据库的列名是 "order_id"
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "status", column = "status"),
            @Result(property = " orderDate",column = " order_date"),
    })
    List<Order> findOrdersByBuyerIdAndStatus(Integer buyerId, String status);

    // 查询所有订单
    @Select("SELECT * FROM Orders")
    @Results({
            @Result(property = "orderId",  column = "order_id"), // 假设数据库的列名是 "order_id"
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "status", column = "status"),
            @Result(property = " orderDate",column = " order_date"),
    })
    List<Order> findAllOrders();

    // 根据订单 ID 获取订单信息
    @Select("SELECT * FROM Orders WHERE order_id = #{orderId}")
    @Results({
            @Result(property = "orderId",  column = "order_id"), // 假设数据库的列名是 "order_id"
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "status", column = "status"),
            @Result(property = " orderDate",column = " order_date"),
    })
    Order findOrderById(Integer orderId);

    // 更新订单状态
 //   @Update("UPDATE Orders SET status = #{status} WHERE order_id = #{orderId}")
  //  int updateOrderStatus(Integer orderId, String status);

    // 更新订单状态
    @Update("UPDATE Orders SET status = #{status} WHERE order_id = #{orderId}")
    void updateOrderStatus(@Param("orderId") Integer orderId, @Param("status") String status);

    // 删除订单
    @Delete("DELETE FROM Orders WHERE order_id = #{orderId}")
    void deleteOrder(@Param("orderId") Integer orderId);

    // 查询订单详情
    @Select("""
        SELECT 
            o.order_id,
            u.username AS buyer_name,
            u.preferred_location AS delivery_address,
            u.contact_number AS contact_info,
            o.total_price,
            o.status,
            o.order_date
        FROM Orders o
        JOIN User u ON o.buyer_id = u.id
    """)
    List<OrderDetailDTO> findOrderDetails();

    // 查询订单详细信息，包括商品名称、数量、单价
    @Select("""
        SELECT 
            p.name,
            oi.quantity,
            oi.unit_price
        FROM OrderItems oi
        JOIN Products p ON oi.product_id = p.id
        WHERE oi.order_id = #{orderId}
    """)
    List<OrderItemDetailDTO> findOrderItemsByOrderId(@Param("orderId") Integer orderId);

    // 查询某买家订单详情
    @Select("""
        SELECT 
            o.order_id,
            u.username AS buyer_name,
            u.preferred_location AS delivery_address,
            u.contact_number AS contact_info,
            o.total_price,
            o.status,
            o.order_date
        FROM Orders o
        JOIN User u ON o.buyer_id = u.id
        WHERE u.id = #{buyerId}
    """)
    List<OrderDetailDTO> findOrderDetailsById(@Param("buyerId") Integer buyerId);

}
