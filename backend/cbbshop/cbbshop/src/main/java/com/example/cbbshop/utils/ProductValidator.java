package com.example.cbbshop.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductValidator {

    // 检查商品名称是否合法
    public static boolean validateProductName(String name) {
        // 名称长度限制：1-30个字符
        if (name.length() < 1 || name.length() > 30) {
            return false;
        }

        // 不允许特殊字符：< > 等
        String regex = "^[a-zA-Z0-9\\u4e00-\\u9fa5_\\-\\s]+$"; // 允许字母、数字、汉字、下划线、连字符和空格
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // 检查商品描述是否合法
    public static boolean validateProductDescription(String description) {
        // 描述长度限制：1-1000个字符
        return description.length() >= 1 && description.length() <= 1000;
    }

    // 检查商品价格是否合法
    public static boolean validateProductPrice(Double price) {
        // 价格范围：0.01 到 9999.99
        if (price < 0.01 || price > 9999.99) {
            return false;
        }

        // 小数位数限制：最多两位小数
        String priceStr = String.format("%.2f", price); // 保留两位小数
        return priceStr.equals(String.format("%.2f", price));
    }

    // 检查商品图片是否合法
    public static boolean validateProductImages(List<MultipartFile> images) {
        // 图片数量限制：至少一张，最多五张
        if (images.size() < 1 || images.size() > 5) {
            return false;
        }

        // 检查每张图片的格式和大小
        for (MultipartFile image : images) {
            // 图片格式限制：JPEG 或 PNG
            String fileType = image.getContentType();
            if (!(fileType.equals("image/jpeg") || fileType.equals("image/png"))) {
                return false;
            }

            // 图片大小限制：不超过5MB
            if (image.getSize() > 5 * 1024 * 1024) { // 5MB
                return false;
            }
        }

        return true;
    }
    // 获取文件扩展名
    private static String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
