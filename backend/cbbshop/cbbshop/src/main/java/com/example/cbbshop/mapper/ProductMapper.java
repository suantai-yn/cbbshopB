package com.example.cbbshop.mapper;

import com.example.cbbshop.model.Product;
import com.example.cbbshop.model.ProductImage;
import com.example.cbbshop.model.ProductWithImages;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    /*@Insert("INSERT INTO Products (name, description, price, status, seller_id, category_id, stock) " +
            "VALUES (#{name}, #{description}, #{price}, #{status}, #{sellerId}, #{categoryId}, #{stock})")
    void insertProduct(Product product);*/ // 插入商品
    @Insert("INSERT INTO Products (name, price, status, category_id, stock_quantity) " +
            "VALUES (#{name}, #{price}, #{status}, #{categoryId}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "productId") // 自动获取生成的 productId
    void insertProduct(Product product); // 插入商品


    @Insert("INSERT INTO Product_Images (product_id, image_path) VALUES (#{productId}, #{imagePath})")
    void insertProductImage(ProductImage image);//插入商品图片

    @Select("SELECT * FROM Products")
    @Results({
            @Result(property = "productId",  column = "id"), // 假设数据库的列名是 "id"
            //@Result(property = "sellerId", column = "seller_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name"),
           // @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "stock", column = "stock_quantity"),
            @Result(property = "status", column = "status")
    })
    List<Product> findAllProducts(); // 查找所有商品

  /*  @Select("""
SELECT p.*, pi.image_path 
FROM Products p 
LEFT JOIN Product_Images pi ON p.product_id = pi.product_id
""")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "sellerId", column = "seller_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "stock", column = "stock"),
            @Result(property = "status", column = "status"),
            @Result(property = "images", column = "product_id",
                    javaType = List.class,
                    many = @Many(select = "findImagesByProductId")) // 获取图片列表
    })
    List<ProductWithImages> findAllProductsWithImages(); // 查找所有带图片的商品
*/
  @Select("SELECT image_path FROM Product_Images WHERE product_id = #{productId}")
  List<String> findImagesByProductId(Integer id);


    @Select("SELECT * FROM Products WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
            @Result(property = "productId",  column = "id"), // 假设数据库的列名是 "id"
            //@Result(property = "sellerId", column = "seller_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name"),
            // @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "stock", column = "stock_quantity"),
            @Result(property = "status", column = "status")
    })
    List<Product> searchByName(String name); // 根据名称模糊搜索

   /* // 根据ID查找商品
   @Select("SELECT * FROM Products WHERE id = #{productId}")
    @Results({
            @Result(property = "productId",  column = "id"), // 假设数据库的列名是 "id"
            //@Result(property = "sellerId", column = "seller_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name"),
            // @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "stock", column = "stock_quantity"),
            @Result(property = "status", column = "status")
    })
    Product findById(Integer id);

    */
    // new  #根据 ID 查找商品：返回商品基本信息及类别和父类信息
   @Select("""
        SELECT p.id AS product_id,
               p.name AS product_name,
               p.price,
               p.stock_quantity,
               p.status,
               p.category_id AS category_id,  -- 这里添加了 category_id 字段的映射
               c.name AS category_name,
               c.parent_id,
               parent_c.name AS parent_category_name
        FROM Products p
        JOIN Categories c ON p.category_id = c.id
        LEFT JOIN Categories parent_c ON c.parent_id = parent_c.id
        WHERE p.id = #{productId}
        """)
   @Results({
           @Result(property = "productId", column = "product_id"),
           @Result(property = "name", column = "product_name"),
           @Result(property = "price", column = "price"),
           @Result(property = "stock", column = "stock_quantity"),
           @Result(property = "status", column = "status"),
           @Result(property = "categoryId", column = "category_id"),  // 映射 categoryId
           @Result(property = "categoryName", column = "category_name"),
           @Result(property = "parentId", column = "parent_id"),
           @Result(property = "parentCategoryName", column = "parent_category_name")
   })
   Product findById(Integer productId);

    /*@Select("SELECT * FROM Product_Images WHERE product_id = #{productId}")
    List<ProductImage> findImagesByProductId(Integer productId); // 根据商品ID查找相关图片
    */

    @Update("UPDATE Products SET stock_quantity = #{stock} WHERE id = #{productId}")
    void updateProductStock(@Param("productId") Integer productId, @Param("stock") Integer stock);


    @Update("UPDATE Products SET status = #{status} WHERE id = #{productId}")
    void updateProductStatus(Integer id, String status);

    @Update("UPDATE Products SET status = 'sold' WHERE id = #{productId}")
    void markProductAsSoldOut(Integer id);

    @Select("SELECT * FROM Products WHERE category_id = #{categoryId}")
    @Results({
            @Result(property = "productId", column = "id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "stock", column = "stock_quantity"),
            @Result(property = "status", column = "status")
    })
    List<Product> findByCategoryId(Integer categoryId); // 根据分类查询商品



}

