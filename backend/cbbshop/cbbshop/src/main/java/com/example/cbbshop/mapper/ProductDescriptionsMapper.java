package com.example.cbbshop.mapper;

import com.example.cbbshop.model.ProductDescription;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ProductDescriptionsMapper {

    // 插入商品描述
    @Insert("INSERT INTO ProductDescriptions (product_id, content, created_at) VALUES (#{productId}, #{content}, datetime('now'))")
    void insertProductDescription(ProductDescription description);

    @Select("SELECT * FROM ProductDescriptions WHERE product_id = #{productId}")
    ProductDescription findByProductId(Integer productId);
    // 更新商品描述
    @Update("UPDATE ProductDescriptions SET content = #{content} WHERE product_id = #{productId}")
    void updateProductDescription(ProductDescription productDescription);
}
