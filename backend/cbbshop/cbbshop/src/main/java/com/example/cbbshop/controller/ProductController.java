package com.example.cbbshop.controller;

import com.example.cbbshop.model.*;
import com.example.cbbshop.service.CategoryService;
import com.example.cbbshop.service.FavoriteService;
import com.example.cbbshop.service.ProductDescriptionService;
import com.example.cbbshop.service.ProductMediaService;
import com.example.cbbshop.service.ProductService;
import com.example.cbbshop.service.CartService;
import com.example.cbbshop.utils.ApiResponse;
import com.example.cbbshop.utils.MediaUtils;
import com.example.cbbshop.utils.ProductValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FilenameUtils;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductMediaService productMediaService;
    private final ProductDescriptionService productDescriptionService;
    private final FavoriteService favoriteService;
    private final CartService cartService;
    // 图片上传目录
    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    public ProductController(ProductDescriptionService productDescriptionService,FavoriteService favoriteService,ProductMediaService productMediaService,ProductService productService, CategoryService categoryService,CartService cartService) {
        this.productService = productService;
        this.categoryService = categoryService; // 确保 categoryService 被注入
        this.productMediaService = productMediaService;
        this.productDescriptionService=productDescriptionService;
        this.favoriteService=favoriteService;
        this.cartService=cartService;

    }


    // 获取某个商品
    // 获取某个商品
    // 查询单个商品及其图片
    @GetMapping("/with-images/{productId}")
    public ResponseEntity<ApiResponse> getProductWithImages(@PathVariable Integer productId, HttpServletRequest request) {
        try {
            ProductWithImages productWithImages = productService.getProductWithImages(productId);
            if (productWithImages == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Product not found", null));
            }

            boolean isFavorite = false;
            Integer quantity = 0;
            Integer favoriteId = null;
            Integer cartId = null;

            // 检查当前用户是否收藏该产品
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                User loggedUser = (User) session.getAttribute("user");
                Integer buyerId = loggedUser.getId();

                isFavorite = favoriteService.checkIfFavorite(buyerId, productId);
                quantity = cartService.checkItemQuantity(buyerId, productId);
                favoriteId = isFavorite ? favoriteService.getFavoriteId(buyerId, productId) : null;
                cartId = cartService.getCartId(buyerId, productId);
            }

            // 只返回不为 null 的字段
            return ResponseEntity.ok(new ApiResponse(true, "Product with images fetched successfully", productWithImages, isFavorite, quantity, favoriteId, cartId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching product with images: " + e.getMessage(), null));
        }
    }

    // 获取商品的描述和富媒体（图片、视频等）
    @GetMapping("/with-media/{productId}")
    public ResponseEntity<ApiResponse> getProductWithMedia(@PathVariable Integer productId, HttpServletRequest request) {
        try {
            // 获取商品基本信息及图片
            ProductWithImages productWithImages = productService.getProductWithImages(productId);

            // 获取商品描述（富文本内容）
            ProductDescription productDescription = productDescriptionService.getProductDescription(productId);

            // 检查当前用户是否收藏该产品
            HttpSession session = request.getSession(false);
            boolean isFavorite = false;

            if (session != null && session.getAttribute("user") != null) {
                User loggedUser = (User) session.getAttribute("user");
                Integer buyerId = loggedUser.getId(); // 获取用户 ID
                isFavorite = favoriteService.checkIfFavorite(buyerId, productId); // 假设这是一个返回 boolean 的方法
            }
            // 组合返回数据
            ProductWithMediaResponse response = new ProductWithMediaResponse();
            response.setProduct(productWithImages);
            response.setProductDescription(productDescription);

            return ResponseEntity.ok(new ApiResponse(true, "Product with media fetched successfully", response,isFavorite));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching product with media: " + e.getMessage()));
        }
    }




    // 获取带图片的所有商品
    @GetMapping("/with-images")
    public ResponseEntity<ApiResponse> getAllProductsWithImages() {
        try {
            List<ProductWithImages> productsWithImages = productService.getAllProductsWithImages();



            return ResponseEntity.ok(new ApiResponse(true, "Products with images fetched successfully", productsWithImages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching products with images: " + e.getMessage()));
        }
    }

    // 获取指定商品ID的所有富媒体信息
    @GetMapping("/{productId}/media")
    public ProductDescription getProductMedia(@PathVariable Integer productId) {
        return productDescriptionService.getProductDescription(productId);
    }

    // 根据商品名称搜索商品
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProducts(@RequestParam String name) {
        try {
            List<Product> products = productService.searchProductsByName(name);
            return ResponseEntity.ok(new ApiResponse(true, "Products search completed", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error searching products: " + e.getMessage()));
        }
    }
    // 根据子类ID获取商品
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable Integer categoryId) {
        try {
            // 获取符合二级分类的商品
            List<ProductWithImages> productsWithImages = productService.getProductsByCategory(categoryId);

            if (productsWithImages.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "No products found for the given category"));
            }

            return ResponseEntity.ok(new ApiResponse(true, "Products fetched successfully", productsWithImages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching products: " + e.getMessage()));
        }
    }


    // 手动下架商品
    @PostMapping("/soldout/{productId}")
    public ResponseEntity<ApiResponse> markProductAsSoldOut(@PathVariable Integer productId) {
        try {
            productService.markProductAsSoldOut(productId);
            return ResponseEntity.ok(new ApiResponse(true, "Product marked as sold out successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error marking product as sold out: " + e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(
            @RequestParam("productJson") String productJson,
           //@RequestBody Product product,
           @RequestParam List<MultipartFile> images,// 上传的图片文件
           @RequestParam(value = "descriptionContent", required = false) String descriptionContent
    ){
        try {

            System.out.println("接收到的商品数据: " + productJson);  // 输出查看传递的 JSON 数据

            //解析商品数据
            Product product=parseProductJson(productJson);
            // 校验商品分类是否存在
            Integer categoryId = product.getCategoryId();
            Category category = categoryService.getCategoryById(categoryId);

            if (category == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid categoryId. Please select a valid category"));
            }

            // 验证商品数据
            if (!ProductValidator.validateProductName(product.getName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid product name"));
            }

            if (!ProductValidator.validateProductPrice(product.getPrice())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid product price"));
            }

            if (images != null && !images.isEmpty()) {
                // 检查图片是否合法
                if (!ProductValidator.validateProductImages(images)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse(false, "Invalid image(s) uploaded. Please ensure they are in valid format (JPEG/PNG) and under 5MB."));
                }

                List<String> imagePaths = uploadImages(images); // 上传图片并获取路径
                productService.addProduct(product, imagePaths);
            } else {
                // 如果没有图片，设置空的 imagePaths 或者跳过图片处理
                List<String> imagePaths = new ArrayList<>();
                productService.addProduct(product, imagePaths);
            }

            // 保存商品
            // 校验描述内容长度
            if (descriptionContent == null || descriptionContent.length() < 1 || descriptionContent.length() > 1000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Description must be between 1 and 1000 characters"));
            }

            // 保存商品描述

            ProductDescription description = new ProductDescription();
            description.setProductId(product.getProductId());
            description.setContent(descriptionContent);
            productDescriptionService.save(description);

           // return ResponseEntity.ok(new ApiResponse(true, "Product description added successfully"));


            return ResponseEntity.ok(new ApiResponse(true, "Product added successfully"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error while uploading images: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error while adding product: " + e.getMessage()));
        }
    }

    @PostMapping("/add-description")
    public ResponseEntity<ApiResponse> addProductDescription(
            @RequestParam("productId") Integer productId,  // 商品ID
            @RequestParam("descriptionContent") String descriptionContent  // 商品描述内容
    ) {
        try {

            // 校验商品是否存在
            Product product = productService.getProductById(productId);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Product not found"));
            }

            // 校验描述内容长度
            if (descriptionContent == null || descriptionContent.length() < 1 || descriptionContent.length() > 1000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Description must be between 1 and 1000 characters"));
            }

            // 保存商品描述
            ProductDescription description = new ProductDescription();
            description.setProductId(productId);
            description.setContent(descriptionContent);
            productDescriptionService.save(description);

            return ResponseEntity.ok(new ApiResponse(true, "Product description added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An error occurred while adding the product description"));
        }
    }

    // 解析商品JSON数据
    private Product parseProductJson(String productJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(productJson, Product.class);
    }

    // 上传并验证图片文件
    private List<String> uploadImages(List<MultipartFile> images) throws IOException {
        List<String> imagePaths = new ArrayList<>();

        String baseDir = System.getProperty("user.dir"); // 项目的根路径
        File directory = new File(baseDir + File.separator + uploadDir); // 上传目录路径

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 定义最大文件大小限制 5MB（单位：字节）
        long maxSize = 5 * 1024 * 1024; // 5MB



        for (MultipartFile image : images) {
            // 检查图片合法性
            if (!isValidImage(image)) {
                throw new IOException("Invalid image file: " + image.getOriginalFilename());
            }
            if (image.getSize() > maxSize) {
                throw new IOException("Image size exceeds the maximum limit of 5MB: " + image.getOriginalFilename());
            }
            // 重新命名图片
            String newFileName = generateUniqueFileName(image);
            File destFile = new File(directory, newFileName);
            image.transferTo(destFile);

            // 将图片的相对路径添加到列表
            String relativePath = "/images/" + newFileName;
            imagePaths.add(relativePath);
        }

        return imagePaths;
    }

    // 判断图片是否合法
    private boolean isValidImage(MultipartFile image) {
        try {
            // 使用 ImageIO 判断是否是有效的图片
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            return bufferedImage != null;
        } catch (IOException e) {
            return false; // 如果读取失败，则说明图片不合法
        }
    }

    // 生成唯一的文件名
    private String generateUniqueFileName(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);

        // 使用 UUID 和当前时间戳生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + "." + extension;
        return uniqueFileName;
    }



}



