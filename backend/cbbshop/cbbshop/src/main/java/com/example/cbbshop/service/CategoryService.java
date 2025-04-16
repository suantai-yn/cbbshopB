package com.example.cbbshop.service;

import com.example.cbbshop.mapper.CategoryMapper;
import com.example.cbbshop.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 根据ID查询分类
    public Category getCategoryById(Integer categoryId) {
        return categoryMapper.findCategoryById(categoryId);
    }
    // 获取所有一级分类
    public List<Category> getAllCategories() {
        return categoryMapper.findAllCategories();
    }

    // 获取某个分类下的二级分类
    public List<Category> getSubCategories(Integer parentId) {
        return categoryMapper.findSubCategories(parentId);
    }
}
