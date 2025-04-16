package com.example.cbbshop.controller;

import com.example.cbbshop.model.Category;
import com.example.cbbshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 获取所有一级分类
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // 获取某个分类下的二级分类
    @GetMapping("/{parentId}")
    public ResponseEntity<List<Category>> getSubCategories(@PathVariable Integer parentId) {
        List<Category> subCategories = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(subCategories);
    }
}
