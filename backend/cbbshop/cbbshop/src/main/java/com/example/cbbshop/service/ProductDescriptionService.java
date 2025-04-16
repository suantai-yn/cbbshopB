package com.example.cbbshop.service;

import com.example.cbbshop.mapper.ProductDescriptionsMapper;
import com.example.cbbshop.model.ProductDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDescriptionService {

    private final ProductDescriptionsMapper productDescriptionsMapper;

    @Autowired
    public ProductDescriptionService(ProductDescriptionsMapper productDescriptionsMapper) {
        this.productDescriptionsMapper = productDescriptionsMapper;
    }

    // 添加商品描述
    public void addProductDescription(Integer productId, String descriptionContent) {

        ProductDescription description = new ProductDescription();
        description.setProductId(productId);
        description.setContent(descriptionContent);
        productDescriptionsMapper.insertProductDescription(description);
    }

    // 根据商品ID获取商品描述
    public ProductDescription getProductDescription(Integer productId) {
        return productDescriptionsMapper.findByProductId(productId);
    }

    // 更新商品描述
    public void updateProductDescription(ProductDescription productDescription) {
        productDescriptionsMapper.updateProductDescription(productDescription);
    }

    public void save(ProductDescription description) {
        productDescriptionsMapper.insertProductDescription(description);
    }
}
