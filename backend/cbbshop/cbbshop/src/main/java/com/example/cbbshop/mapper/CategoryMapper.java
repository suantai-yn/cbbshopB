package com.example.cbbshop.mapper;

import com.example.cbbshop.model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 获取所有一级分类（parent_id 为 NULL 或 0）
    @Select("SELECT * FROM Categories WHERE parent_id IS NULL OR parent_id = 0")
    @Results({
            @Result(property = "categoryId", column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "parentId", column = "parent_id"),
    })
    List<Category> findAllCategories();

    // 获取某个分类下的二级分类
    @Select("SELECT * FROM Categories WHERE parent_id = #{parentId}")
    @Results({
            @Result(property = "categoryId", column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "parentId", column = "parent_id"),
    })
    List<Category> findSubCategories(Integer parentId);

    // 获取单个分类
    @Select("SELECT * FROM Categories WHERE id = #{categoryId}")
    Category findCategoryById(Integer categoryId);

    // 插入分类（可以在后台管理中使用）
    @Insert("INSERT INTO Categories (name, parent_id) VALUES (#{name}, #{parentId})")
    void insertCategory(Category category);

    // 更新分类信息
    @Update("UPDATE Categories SET name = #{name}, parent_id = #{parentId} WHERE category_id = #{categoryId}")
    void updateCategory(Category category);
}
