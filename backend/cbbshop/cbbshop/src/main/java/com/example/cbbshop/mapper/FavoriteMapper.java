package com.example.cbbshop.mapper;

import com.example.cbbshop.model.Favorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    // 添加收藏项，如果已存在则不进行任何操作
    @Insert("INSERT INTO Favorites(buyer_id, product_id) VALUES(#{buyerId}, #{productId}) " +
            "ON CONFLICT(buyer_id, product_id) DO NOTHING")
    void addFavorite(Favorite favorite);

    // 根据 favoriteId 删除收藏项
    @Delete("DELETE FROM Favorites WHERE favorite_id = #{favoriteId}")
    void removeFavorite(@Param("favoriteId") Integer favoriteId);

    // 根据买家 ID 查询其所有收藏项
    @Select("SELECT * FROM Favorites WHERE buyer_id = #{buyerId}")
    List<Favorite> findByBuyerId(@Param("buyerId") Integer buyerId);

    // 根据 productId 和 buyerId 查询特定的收藏项
    @Select("SELECT * FROM Favorites WHERE buyer_id = #{buyerId} AND product_id = #{productId}")
    Favorite findByBuyerIdAndProductId(@Param("buyerId") Integer buyerId, @Param("productId") Integer productId);

    // 清空一个买家的所有收藏项
    @Delete("DELETE FROM Favorites WHERE buyer_id = #{buyerId}")
    void clearFavoritesByBuyerId(@Param("buyerId") Integer buyerId);

    @Select("SELECT COUNT(*) > 0 FROM Favorites WHERE buyer_id = #{buyerId} AND product_id = #{productId}")
    boolean existsByBuyerIdAndProductId(@Param("buyerId") Integer buyerId, @Param("productId") Integer productId);

    @Select("SELECT favorite_id FROM Favorites WHERE buyer_id = #{buyerId} AND product_id = #{productId}")
    Integer findFavoriteIdByBuyerIdAndProductId(Integer buyerId, Integer productId);
}