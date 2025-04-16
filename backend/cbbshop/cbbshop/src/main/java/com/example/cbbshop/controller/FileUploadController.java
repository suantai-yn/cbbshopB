package com.example.cbbshop.controller;

import com.example.cbbshop.utils.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/editor")
public class FileUploadController {

    // 上传文件的根路径
    private static final String ROOT_PATH = "./cbbshop/static/upload/directory";  // 根据实际情况修改路径

    // 图片和视频上传接口
    @PostMapping("/upload")
    public Map<String, Object>  editorUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam("type") String type) throws IOException {

        // 获取文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return createErrorResponse("No file uploaded");
        }

        // 获取文件的主文件名和扩展名
        String mainName = FileUtil.mainName(originalFilename);
        String extName = FileUtil.extName(originalFilename);

        // 判断上传文件类型（图片或视频）
        if ("img".equals(type)) {
            // 如果是图片，先校验图片格式
            if (!isValidImageFile(extName)) {
                return createErrorResponse("Invalid image file type");
            }
        } else if ("video".equals(type)) {
            // 如果是视频，校验视频格式
            if (!isValidVideoFile(extName)) {
                return createErrorResponse("Invalid video file type");
            }
        } else {
            return createErrorResponse("Unsupported file type");
        }

        // 创建上传文件的存储目录（如果目录不存在的话）
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 创建目录
        }

        // 生成新的文件名，避免文件重名
        String newFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;

        // 保存文件到本地
        File saveFile = new File(ROOT_PATH + File.separator + newFilename);
        file.transferTo(saveFile);

        // 返回上传文件的 URL 地址（通过相对路径）
        String fileUrl = "http://localhost:8081/uploads/" + newFilename;  // 修改为你的域名和路径

        // 根据文件类型返回相应的 URL
        if ("img".equals(type)) {
            Map<String, Object> response = new HashMap<>();
            response.put("errno", 0); // 上传成功，errno 为 0
            Map<String, Object> data = new HashMap<>();
            data.put("url", fileUrl); // 图片的 URL
            response.put("data", data);
            return response;
        } else if ("video".equals(type)) {
            Map<String, Object> response = new HashMap<>();
            response.put("errno", 0); // 上传成功，errno 为 0
            Map<String, Object> data = new HashMap<>();
            data.put("url", fileUrl); // 视频的 URL
            response.put("data", data);
            return response;
        }

        return createErrorResponse("Unsupported file type");
    }

    // 校验图片格式
    private boolean isValidImageFile(String extName) {
        List<String> validImageTypes = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
        return validImageTypes.contains(extName.toLowerCase());
    }

    // 校验视频格式
    private boolean isValidVideoFile(String extName) {
        List<String> validVideoTypes = Arrays.asList("mp4", "avi", "mov", "wmv");
        return validVideoTypes.contains(extName.toLowerCase());
    }


    private Map<String, Object> createSuccessResponse(String url) {
        Map<String, Object> response = new HashMap<>();
        response.put("errno", 0); // 上传成功的 errno 必须是数字 0
        Map<String, Object> data = new HashMap<>();
        data.put("url", url); // 图片的 src URL
        data.put("alt", "Image description"); // 可选：图片描述文字
        data.put("href", "http://your-link.com"); // 可选：图片的链接
        response.put("data", data);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("errno", 1); // 上传失败的 errno 必须是数字 1
        response.put("message", message); // 错误信息
        return response;
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

