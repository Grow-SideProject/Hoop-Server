package com.hoop.api.service;


import com.hoop.api.domain.Profile;
import com.hoop.api.exception.FileUploadException;
import com.hoop.api.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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

    @Transactional
    public String saveImage(Long id, String category, MultipartFile file) {
        try {
            String basicPath = System.getProperty("user.dir");
            String relPath = "/images";
            if (!new File(basicPath+relPath).exists()) {
                new File(basicPath+relPath).mkdir();
            }
            relPath = relPath+ "/" + category;
            if (!new File(basicPath+relPath).exists()) {
                new File(basicPath+relPath).mkdir();
            }
            String filename = id +"_"+ file.getOriginalFilename();
            String filePath =  basicPath + relPath + "/" + filename;
            file.transferTo(new File(filePath));
            return relPath + "/" + filename;
        } catch (Exception e) {
            throw new FileUploadException();
        }
    }

    public String getImage(String imagePath) {
        try {
            // 이미지 파일 경로를 지정합니다.
            String basicPath = System.getProperty("user.dir");
            Path absPath = Paths.get(basicPath + imagePath).toAbsolutePath().normalize();
            return absPath.toString();
        } catch (Exception e) {
            return null;
        }
    }

}