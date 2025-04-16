package com.example.cbbshop.service;

import com.example.cbbshop.mapper.ProductMediaMapper;
import com.example.cbbshop.model.ProductMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMediaService {

    private final ProductMediaMapper productMediaMapper;

    @Autowired
    public ProductMediaService(ProductMediaMapper productMediaMapper) {
        this.productMediaMapper = productMediaMapper;
    }
    // 添加富媒体
// 修改后的 service 层代码
    public void addProductMedia(List<ProductMedia> productMediaList) {
        for (ProductMedia productMedia : productMediaList) {
            // 将每个 ProductMedia 添加到数据库
            productMediaMapper.insertProductMedia(productMedia);
        }
    }


    // 获取商品的富媒体
    public List<ProductMedia> getProductMedia(Integer productId) {
        return productMediaMapper.findByProductId(productId);
    }

    // 更新富媒体记录
    public void updateProductMedia(ProductMedia productMedia) {
        productMediaMapper.updateProductMedia(productMedia);
    }
}
