package com.hoop.api.service;


import com.hoop.api.config.AppConfig;
import com.hoop.api.exception.FileUploadException;
import com.hoop.api.exception.UserNotFound;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ResourceLoader resourceLoader;



    private final AppConfig appConfig;
    @Transactional
    public String saveImage(Long id, String category, MultipartFile file) {
        try {
            String basicPath = appConfig.getPath();
            String profilePath = basicPath + "/profile";
            if (!new File(profilePath).exists()) new File(profilePath).mkdir();
            File directory = new File(profilePath);
            String fileName =id +"_"+ file.getOriginalFilename();
            Path filePath = Paths.get(directory.getAbsolutePath(), fileName);
            file.transferTo(filePath);
            return filePath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException();
        }
    }
}