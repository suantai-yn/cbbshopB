package com.example.cbbshop.service;

import com.example.cbbshop.mapper.ProductMapper;
import com.example.cbbshop.model.Product;
import com.example.cbbshop.model.ProductImage;
import com.example.cbbshop.model.ProductWithImages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 添加商品和图片
    @Transactional
    public void addProduct(Product product, List<String> imagePaths) {
        // 插入商品
        productMapper.insertProduct(product);

        // 确保插入商品后获取了 productId
        Integer productId = product.getProductId();
        if (productId == null) {
            throw new RuntimeException("Failed to insert product, productId is null");
        }


        // 遍历图片路径，创建 ProductImage 对象并插入数据库
        for (String imagePath : imagePaths) {
            ProductImage image = new ProductImage();
            image.setProductId(productId); // 使用刚获取的 productId
            image.setImagePath(imagePath); // 设置图片路径
            productMapper.insertProductImage(image); // 插入商品图片
        }
    }


    // 上架商品
    public void markProductAsSoldOut(Integer productId) {
        productMapper.markProductAsSoldOut(productId);
    }

    // 根据商品名称搜索商品
    public List<Product> searchProductsByName(String name) {
        return productMapper.searchByName(name);
    }

    // 获取所有商品
    public List<Product> getAllProducts() {
        return productMapper.findAllProducts();
    }

    // 获取带图片的商品
    public List<ProductWithImages> getAllProductsWithImages() {
        List<Product> products = productMapper.findAllProducts();
        List<ProductWithImages> productsWithImages = new ArrayList<>();

        for (Product product : products) {
            ProductWithImages productWithImages = new ProductWithImages();
            productWithImages.setProduct(product);
            productWithImages.setImagePaths(productMapper.findImagesByProductId(product.getProductId()));
            productsWithImages.add(productWithImages);
        }

        return productsWithImages;
    }
    public ProductWithImages getProductWithImages(Integer productId) {
        // 获取商品信息
        Product product = productMapper.findById(productId);
        if (product == null) {
            return null; // 商品不存在，返回null
        }

        // 获取商品图片信息
        ProductWithImages productWithImages = new ProductWithImages();
        productWithImages.setProduct(product);
        productWithImages.setImagePaths(productMapper.findImagesByProductId(productId));

        return productWithImages;
    }
    // 根据子类ID获取商品
    public List<ProductWithImages> getProductsByCategory(Integer categoryId) {
        // 获取所有符合条件的商品
        List<Product> products = productMapper.findByCategoryId(categoryId);
        List<ProductWithImages> productsWithImages = new ArrayList<>();

        for (Product product : products) {
            // 通过商品 ID 获取该商品的图片
            List<String> imagePaths = productMapper.findImagesByProductId(product.getProductId());
            ProductWithImages productWithImages = new ProductWithImages();
            productWithImages.setProduct(product);
            productWithImages.setImagePaths(imagePaths);
            productsWithImages.add(productWithImages);
        }

        return productsWithImages;
    }

    public int getProductStock(int productId) {
        Product product = productMapper.findById(productId);
        return product != null ? product.getStock() : 0;
    }

    public Product getProductById(Integer productId) {
        return productMapper.findById(productId);
    }
}
