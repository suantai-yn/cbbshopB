package com.example.cbbshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    // 配置上传文件的存储路径
    @Value("${upload.dir}")
    private String uploadDir;

    // 上传文件并重命名
    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        // 获取文件的原始扩展名
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 检查文件扩展名是否合法（如JPEG、PNG、MP4等）
        if (!isValidMediaFile(fileExtension)) {
            throw new IllegalArgumentException("Invalid file format");
        }

        // 生成新的文件名（UUID避免重名）
        String newFileName = prefix + "_" + UUID.randomUUID().toString() + fileExtension;

        // 构建文件存储路径
        Path filePath = Paths.get(uploadDir, newFileName);

        // 创建文件夹（如果不存在）
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 保存文件到目标路径
        file.transferTo(filePath.toFile());

        return newFileName;  // 返回新的文件名
    }

    // 验证文件类型是否合法
    private boolean isValidMediaFile(String fileExtension) {
        String[] validImageFormats = {".jpg", ".jpeg", ".png", ".gif"};
        String[] validVideoFormats = {".mp4", ".mov", ".avi"};
        String[] validAudioFormats = {".mp3", ".wav"};

        // 将所有合法格式存放到数组中进行检查
        for (String format : validImageFormats) {
            if (fileExtension.equalsIgnoreCase(format)) return true;
        }
        for (String format : validVideoFormats) {
            if (fileExtension.equalsIgnoreCase(format)) return true;
        }
        for (String format : validAudioFormats) {
            if (fileExtension.equalsIgnoreCase(format)) return true;
        }
        return false;  // 不支持的文件格式
    }
}
