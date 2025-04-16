package com.example.cbbshop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class MediaUtils {

    // 根据 HTML 内容提取媒体信息（图片、视频等）
    public static List<Map<String, String>> extractMediaTypesFromHtml(String content) {
        List<Map<String, String>> mediaList = new ArrayList<>();

        // 定义正则表达式来匹配 img 和 video 标签
        String imgRegex = "<img[^>]+src=\"([^\"]+)\"[^>]*>";
        String videoRegex = "<video[^>]+src=\"([^\"]+)\"[^>]*>";

        // 使用 Pattern 和 Matcher 来匹配 HTML 内容中的图片和视频
        Pattern imgPattern = Pattern.compile(imgRegex);
        Matcher imgMatcher = imgPattern.matcher(content);
        while (imgMatcher.find()) {
            Map<String, String> mediaMap = new HashMap<>();
            String mediaUrl = imgMatcher.group(1);  // 提取图片链接
            mediaMap.put("media_url", mediaUrl);
            mediaMap.put("media_type", determineMediaTypeFromHtml(mediaUrl)); // 从 URL 判断类型
            mediaList.add(mediaMap);
        }

        Pattern videoPattern = Pattern.compile(videoRegex);
        Matcher videoMatcher = videoPattern.matcher(content);
        while (videoMatcher.find()) {
            Map<String, String> mediaMap = new HashMap<>();
            String mediaUrl = videoMatcher.group(1);  // 提取视频链接
            mediaMap.put("media_url", mediaUrl);
            mediaMap.put("media_type", determineMediaTypeFromHtml(mediaUrl)); // 从 URL 判断类型
            mediaList.add(mediaMap);
        }

        return mediaList;
    }

    // 根据 URL 判断文件类型（通过扩展名）
    public static String determineMediaTypeFromHtml(String url) {
        // 判断图片格式
        if (url.matches(".*\\.(jpg|jpeg|png|gif|bmp)$")) {
            return "JPEG";  // 图片
        }
        // 判断视频格式
        else if (url.matches(".*\\.(mp4|avi|mov|mkv|flv)$")) {
            return "MP4";  // 视频
        }
        // 判断音频格式
        else if (url.matches(".*\\.(mp3|wav|aac)$")) {
            return "MP3" ;
                     // 音频
        }
        else {
            return "other";  // 其他类型
        }
    }
}
