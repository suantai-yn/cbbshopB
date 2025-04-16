package com.example.cbbshop.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    // 根路径，实际使用时请根据你的项目设置正确的路径
    public static final String ROOT_PATH = "/path/to/your/upload/directory";

    // 检查路径是否存在
    public static boolean exist(String path) {
        File file = new File(path);
        return file.exists();
    }

    // 创建目录
    public static void mkdir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean success = dir.mkdirs(); // 创建多级目录
            if (!success) {
                throw new RuntimeException("Failed to create directories");
            }
        }
    }

    // 获取文件扩展名
    public static String extName(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastIndexOfDot = filename.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // 没有扩展名
        }
        return filename.substring(lastIndexOfDot + 1).toLowerCase(); // 获取扩展名并转为小写
    }

    // 获取文件主文件名（去掉扩展名）
    public static String mainName(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastIndexOfDot = filename.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return filename; // 没有扩展名时返回整个文件名
        }
        return filename.substring(0, lastIndexOfDot);
    }

    // 获取文件大小
    public static long getFileSize(String path) {
        File file = new File(path);
        return file.length();
    }

    // 文件保存
    public static void saveFile(String path, byte[] data) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        java.nio.file.Files.write(file.toPath(), data);
    }
}
