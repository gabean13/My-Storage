package org.c4marathon.assignment.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContentTypeToExtensionMapper {
    private Map<String, String> contentTypeToExtensionMap;

    public ContentTypeToExtensionMapper() {
        contentTypeToExtensionMap = new HashMap<>();
        initializeMap();
    }

    public String getExtension(String contentType) {
        return contentTypeToExtensionMap.get(contentType);
    }

    public boolean isContainsContentType(String contentType){
        return contentTypeToExtensionMap.containsKey(contentType);
    }

    private void initializeMap() {
        contentTypeToExtensionMap.put("image/avif", ".avif");
        contentTypeToExtensionMap.put("image/png", ".png");
        contentTypeToExtensionMap.put("application/vnd.amazon.ebook", ".azw");
        contentTypeToExtensionMap.put("application/octet-stream", ".bin");
        contentTypeToExtensionMap.put("image/bmp", ".bmp");
        contentTypeToExtensionMap.put("application/x-csh", ".csh");
        contentTypeToExtensionMap.put("text/css", ".css");
        contentTypeToExtensionMap.put("text/csv", ".csv");
        contentTypeToExtensionMap.put("application/msword", ".doc");
        contentTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");
        contentTypeToExtensionMap.put("application/gzip", ".gz");
        contentTypeToExtensionMap.put("image/gif", ".gif");
        contentTypeToExtensionMap.put("text/html", ".html");
        contentTypeToExtensionMap.put("text/calendar", ".ics");
        contentTypeToExtensionMap.put("application/java-archive", ".jar");
        contentTypeToExtensionMap.put("image/jpeg", ".jpg");
        contentTypeToExtensionMap.put("text/javascript", ".js");
        contentTypeToExtensionMap.put("application/json", ".json");
        contentTypeToExtensionMap.put("audio/mpeg", ".mp3");
        contentTypeToExtensionMap.put("video/mp4", ".mp4");
        contentTypeToExtensionMap.put("video/mpeg", ".mpeg");
        contentTypeToExtensionMap.put("application/vnd.apple.installer+xml", ".mpkg");
        contentTypeToExtensionMap.put("application/pdf", ".pdf");
        contentTypeToExtensionMap.put("application/x-httpd-php", ".php");
        contentTypeToExtensionMap.put("application/vnd.ms-powerpoint", ".ppt");
        contentTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx");
        contentTypeToExtensionMap.put("application/x-sh", ".sh");
        contentTypeToExtensionMap.put("image/svg+xml", ".svg");
        contentTypeToExtensionMap.put("application/x-tar", ".tar");
        contentTypeToExtensionMap.put("text/plain", ".txt");
        contentTypeToExtensionMap.put("audio/wav", ".wav");
        contentTypeToExtensionMap.put("image/webp", ".webp");
        contentTypeToExtensionMap.put("application/vnd.ms-excel", ".xls");
        contentTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
        contentTypeToExtensionMap.put("application/xml", ".xml");
        contentTypeToExtensionMap.put("application/zip", ".zip");
    }
}