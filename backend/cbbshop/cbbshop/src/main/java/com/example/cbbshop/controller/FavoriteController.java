package com.example.cbbshop.controller;

import com.example.cbbshop.model.Favorite;
import com.example.cbbshop.model.ProductWithImages;
import com.example.cbbshop.service.FavoriteService;
import com.example.cbbshop.service.ProductService;
import com.example.cbbshop.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.example.cbbshop.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ProductService productService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, ProductService productService) {
        this.productService = productService;
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFavorite(@RequestBody Favorite favorite) {
        try {
            favoriteService.addFavorite(favorite);
            return ResponseEntity.ok(new ApiResponse(true, "Added to favorites successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error adding to favorites: " + e.getMessage()));
        }
    }

    @DeleteMapping("/remove/{favoriteId}")
    public ResponseEntity<ApiResponse> removeFavorite(@PathVariable Integer favoriteId) {
        try {
            favoriteService.removeFavorite(favoriteId);
            return ResponseEntity.ok(new ApiResponse(true, "Removed from favorites successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error removing from favorites: " + e.getMessage()));
        }
    }

    @GetMapping("/list/{buyerId}")
    public ResponseEntity<ApiResponse> getFavoriteList(@PathVariable Integer buyerId) {
        try {
            // 获取用户的收藏列表
            List<Favorite> favorites = favoriteService.getFavoriteList(buyerId);

            if (favorites == null || favorites.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "No favorite items found for the specified buyer."));
            }

            // 获取每个商品的详细信息
            List<ProductWithImages> favoriteProductDetails = new ArrayList<>();
            for (Favorite favorite : favorites) {
                Integer productId = favorite.getProductId(); // 获取商品 ID
                ProductWithImages productWithImages = productService.getProductWithImages(productId);

                if (productWithImages != null) {
                    favoriteProductDetails.add(productWithImages); // 将商品详细信息添加到列表中
                }
            }

            // 返回包含收藏商品详细信息的响应
            return ResponseEntity.ok(new ApiResponse(true, "Favorite items fetched successfully.", favoriteProductDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching favorites: " + e.getMessage()));
        }
    }


}