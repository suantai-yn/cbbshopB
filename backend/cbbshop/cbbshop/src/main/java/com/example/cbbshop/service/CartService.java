package com.example.cbbshop.service;

import com.example.cbbshop.mapper.CartMapper;
import com.example.cbbshop.mapper.FavoriteMapper;
import com.example.cbbshop.mapper.UserMapper;
import com.example.cbbshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final UserMapper userMapper ;
    private final CartMapper cartMapper;
    private final FavoriteMapper favoriteMapper;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public CartService(UserMapper userMapper,CartMapper cartMapper, FavoriteMapper favoriteMapper, OrderService orderService, ProductService productService) {
        this.userMapper = userMapper;
        this.cartMapper = cartMapper;
        this.favoriteMapper = favoriteMapper;
        this.orderService = orderService;
        this.productService = productService;
    }

    public CartItem getCartItemById(Integer cartItemId) {
        return cartMapper.findById(cartItemId); // 从数据库获取 CartItem
    }

    public void addToCart(CartItem cartItem) {
        List<CartItem> existingItems = cartMapper.findByBuyerId(cartItem.getBuyerId());

        for (CartItem item : existingItems) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                cartMapper.updateQuantity(item.getCartId(), item.getQuantity());
                return; // 直接返回
            }
        }
        cartMapper.addToCart(cartItem); // 如果不存在则添加
    }

    public Integer checkItemQuantity(Integer buyerId, Integer productId) {
        return cartMapper.findByBuyerId(buyerId).stream()
                .filter(item -> item.getProductId().equals(productId))
                .map(CartItem::getQuantity)
                .findFirst().orElse(0);
    }

    public void moveToFavorites(List<Integer> cartItemIds) {
        for (Integer cartItemId : cartItemIds) {
            CartItem cartItem = cartMapper.findById(cartItemId);

            if (cartItem != null) {
                Favorite favorite = new Favorite();
                favorite.setBuyerId(cartItem.getBuyerId());
                favorite.setProductId(cartItem.getProductId());
                favoriteMapper.addFavorite(favorite); // 添加到收藏
                cartMapper.removeFromCart(cartItemId); // 从购物车中删除
            }
        }
    }



    public void updateCartItem(CartItem cartItem) {
        cartMapper.updateQuantity(cartItem.getCartId(), cartItem.getQuantity());
    }

    public void removeFromCart(Integer cartId) {
        cartMapper.removeFromCart(cartId);
    }

    public List<CartItem> getCartList(Integer buyerId) {
        return cartMapper.findByBuyerId(buyerId);
    }



    private List<CartItem> getCartItemsByIds(List<Integer> cartItemIds) {
        List<CartItem> cartItems = new ArrayList<>();
        for (Integer id : cartItemIds) {
            CartItem item = getCartItemById(id);
            if (item != null) {
                cartItems.add(item);
            }
        }
        return cartItems;
    }

    private void checkProductStock(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            int currentStock = productService.getProductStock(item.getProductId());
            if (item.getQuantity() > currentStock) {
                throw new IllegalArgumentException("商品 ID: " + item.getProductId() + " 库存不足, 当前库存: " + currentStock);
            }
        }
    }

    private void removeCartItems(List<Integer> cartItemIds) {
        for (Integer id : cartItemIds) {
            removeFromCart(id);
        }
    }

    public void clearCart(Integer buyerId) {
        List<CartItem> items = getCartList(buyerId);
        for (CartItem item : items) {
            removeFromCart(item.getCartId());
        }
    }

    public Integer getCartId(Integer buyerId, Integer productId) {
        return cartMapper.findCartIdByBuyerIdAndProductId(buyerId, productId);
    }

    public void updateCartQuantity(CartItem cartItem) {
        List<CartItem> existingItems = cartMapper.findByBuyerId(cartItem.getBuyerId());
        boolean itemExists = false;

        for (CartItem item : existingItems) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                itemExists = true;
                if (cartItem.getQuantity() > 0) {
                    // 更新数量
                    item.setQuantity(cartItem.getQuantity());
                    cartMapper.updateQuantity(item.getCartId(), item.getQuantity());
                } else {
                    // 如果数量 <= 0，进行删除
                    cartMapper.removeFromCart(item.getCartId());
                }
                break; // 找到后可以直接跳出循环
            }
        }

        // 如果商品不存在且数量 > 0，添加到购物车
        if (!itemExists && cartItem.getQuantity() > 0) {
            cartMapper.addToCart(cartItem);
        }
    }
}