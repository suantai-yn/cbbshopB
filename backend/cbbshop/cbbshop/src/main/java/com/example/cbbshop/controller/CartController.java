package com.example.cbbshop.controller;

import com.example.cbbshop.mapper.CartMapper;
import com.example.cbbshop.model.*;

import com.example.cbbshop.service.*;
import com.example.cbbshop.service.ProductService;

import com.example.cbbshop.service.FavoriteService;
import com.example.cbbshop.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.example.cbbshop.model.User;

import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final FavoriteService favoriteService;

    @Autowired
    public CartController(CartService cartService, ProductService productService, UserService userService,FavoriteService favoriteService) {

        this.cartService = cartService;
        this.productService = productService;
        this.userService = new UserService();
        this.favoriteService = favoriteService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody CartItem cartItem) {
        try {
            cartService.updateCartQuantity(cartItem);
            return ResponseEntity.ok(new ApiResponse(true, "购物车更新成功。", null)); // 简化返回
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "更新购物车时出错：" + e.getMessage(), null)); // 简化返回
        }
    }



    @GetMapping("/list/{buyerId}")
    public ResponseEntity<ApiResponse> getCartList(@PathVariable Integer buyerId, HttpServletRequest request) {
        try {
            // 获取购物车中的商品项
            List<CartItem> cartItems = cartService.getCartList(buyerId);

            // 检查购物车是否为空
            if (cartItems == null || cartItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "No items found in the cart for the specified buyer.", Collections.emptyList(), false, 0));
            }

            // 获取每个商品的详细信息和数量
            List<ProductWithQuantity> productDetails = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                Integer productId = cartItem.getProductId(); // 获取商品 ID
                Integer quantity = cartItem.getQuantity();   // 获取商品数量

                ProductWithImages productWithImages = productService.getProductWithImages(productId);
                if (productWithImages != null) {
                    // 将商品详细信息和数量放入新的返回对象
                    ProductWithQuantity productDetail = new ProductWithQuantity(productWithImages, quantity);
                    productDetails.add(productDetail); // 将商品详细信息和数量添加到列表中
                }
            }

            // 检查当前用户是否有特别的标识（例如，是否登录）
            HttpSession session = request.getSession(false);
            boolean isLoggedIn = session != null && session.getAttribute("user") != null;

            // 返回包含购物车项、商品详细信息和购物车数量的响应
            return ResponseEntity.ok(new ApiResponse(true, "Cart items fetched successfully.", productDetails, isLoggedIn, cartItems.size()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching cart items: " + e.getMessage()));
        }
    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Integer cartId) {
        try {
            cartService.removeFromCart(cartId);
            return ResponseEntity.ok(new ApiResponse(true, "Removed from cart successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error removing from cart: " + e.getMessage()));
        }
    }


    @GetMapping("/exists/{productId}")
    public ResponseEntity<ApiResponse> checkItemExists(
            HttpServletRequest request,
            @PathVariable Integer productId) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {

            return ResponseEntity.ok(new ApiResponse(false, "用户未登录，无法确认商品状态。", null, false, 0, null, null));

        }

        User loggedUser = (User) session.getAttribute("user");
        Integer buyerId = loggedUser.getId(); // 获取用户ID

        try {
            Integer quantity = cartService.checkItemQuantity(buyerId, productId);

            boolean isFavorite = favoriteService.checkIfFavorite(buyerId, productId); // 检查是否为收藏

            Integer favoriteId = isFavorite ? favoriteService.getFavoriteId(buyerId, productId) : null; // 获取 FavoriteId
            Integer cartId = cartService.getCartId(buyerId, productId); // 获取 CartId

            return ResponseEntity.ok(new ApiResponse(true, "状态检查成功。", null, isFavorite, quantity, favoriteId, cartId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "检查商品状态时出错: " + e.getMessage(), null, false, 0, null, null));

        }
    }




}