package com.hoop.api.service;


import com.hoop.api.config.AppConfig;
import com.hoop.api.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final AppConfig appConfig;

    @Transactional
    public String saveImage(Long id, String category, MultipartFile file) {
        try {
            String basicPath = appConfig.getSourcePath();
            String categoryPath = "/" + category;
            String resourcePath = basicPath + categoryPath;
            if (!new File(resourcePath).exists()) new File(resourcePath).mkdir();
            File directory = new File(resourcePath);
            String fileName =id +"_"+ file.getOriginalFilename();
            Path filePath = Paths.get(directory.getAbsolutePath(), fileName);
            file.transferTo(filePath);
            return categoryPath + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException();
        }
    }

    public String getImagePath(String path) {
        return appConfig.getUri() + "/image" + appConfig.getSourcePath() + path;
    }
}