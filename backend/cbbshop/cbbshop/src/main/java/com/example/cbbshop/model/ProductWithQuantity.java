package com.example.cbbshop.model;

public class ProductWithQuantity {
    private ProductWithImages product; // 商品的详细信息
    private Integer quantity;           // 该商品在购物车中的数量

    public ProductWithQuantity() {
        // 无参构造函数
    }

    public ProductWithQuantity(ProductWithImages product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductWithImages getProduct() {
        return product;
    }

    public void setProduct(ProductWithImages product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}