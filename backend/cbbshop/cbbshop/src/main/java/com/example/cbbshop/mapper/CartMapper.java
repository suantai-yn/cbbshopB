package com.example.cbbshop.mapper;

import com.example.cbbshop.model.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    // 添加购物车项
    @Insert("INSERT INTO Cart(buyer_id, product_id, quantity) VALUES(#{buyerId}, #{productId}, #{quantity})")
    void addToCart(CartItem cartItem);

    // 根据购物车项 ID 获取购物车项
    @Select("SELECT * FROM Cart WHERE cart_id = #{cartId}")
    CartItem findById(@Param("cartId") Integer cartId);

    // 从购物车中删除购物车项
    @Delete("DELETE FROM Cart WHERE cart_id = #{cartId}")
    void removeFromCart(@Param("cartId") Integer cartId);

    // 根据 buyer_id 查询购物车项
    @Select("SELECT * FROM Cart WHERE buyer_id = #{buyerId}")
    List<CartItem> findByBuyerId(@Param("buyerId") Integer buyerId);

    // 更新购物车项的数量
    @Update("UPDATE Cart SET quantity = #{quantity} WHERE cart_id = #{cartId}")
    void updateQuantity(@Param("cartId") Integer cartId, @Param("quantity") Integer quantity);

    @Select("SELECT cart_id FROM cart WHERE buyer_id = #{buyerId} AND product_id = #{productId}")
    Integer findCartIdByBuyerIdAndProductId(Integer buyerId, Integer productId);
}