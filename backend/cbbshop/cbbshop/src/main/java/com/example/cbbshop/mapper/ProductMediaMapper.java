package com.example.cbbshop.mapper;

import com.example.cbbshop.model.ProductMedia;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ProductMediaMapper {

    // 插入富媒体信息
    @Insert("INSERT INTO ProductMedia (product_id, media_type, media_url, media_format, media_size, created_at) " +
            "VALUES (#{productId}, #{mediaType}, #{mediaUrl}, #{mediaFormat}, #{mediaSize}, datetime('now'))")
    void insertProductMedia(ProductMedia media);

    // 根据商品ID查询所有富媒体
    @Select("SELECT * FROM ProductMedia WHERE product_id = #{productId}")
    List<ProductMedia> findByProductId(@Param("productId") Integer productId);


    // 更新富媒体记录
    @Update("UPDATE ProductMedia SET media_type = #{mediaType}, media_url = #{mediaUrl}, media_format = #{mediaFormat}, " +
            "media_size = #{mediaSize} WHERE id = #{id}")
    void updateProductMedia(ProductMedia productMedia);
}

