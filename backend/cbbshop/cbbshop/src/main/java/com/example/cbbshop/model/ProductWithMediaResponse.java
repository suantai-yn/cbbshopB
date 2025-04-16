package com.example.cbbshop.model;

import java.util.List;

public class ProductWithMediaResponse {
    private ProductWithImages product;
    private ProductDescription productDescription;

    // Getters and Setters
    public ProductWithImages getProduct() {
        return product;
    }

    public void setProduct(ProductWithImages product) {
        this.product = product;
    }


    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

}
