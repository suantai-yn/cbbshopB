package com.example.cbbshop.utils;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private Boolean isFavorite; // 新增字段
    private Integer quantity; // 新增字段，用于表示购物车中的商品数量
    private Integer favoriteId; // 新增字段
    private Integer cartId;

    // 构造函数
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }


    // 新构造函数，包含 isFavorite
    public ApiResponse(boolean success, String message, Object data, Boolean isFavorite) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.isFavorite = isFavorite;
    }

    // 新构造函数，包含 isFavorite 和 quantity
    public ApiResponse(boolean success, String message, Object data, Boolean isFavorite, Integer quantity) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.isFavorite = isFavorite;
        this.quantity = quantity;
    }

    public ApiResponse(boolean success, String message, Object data, boolean isFavorite, int quantity, Integer favoriteId, Integer cartId) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.isFavorite = isFavorite;
        this.quantity = quantity;
        this.favoriteId = favoriteId;
        this.cartId = cartId;
    }



    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Boolean getIsFavorite() {
        return isFavorite; // Getter for isFavorite
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite; // Setter for isFavorite
    }

    public Integer getQuantity() {
        return quantity; // Getter for quantity
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity; // Setter for quantity
    }
}