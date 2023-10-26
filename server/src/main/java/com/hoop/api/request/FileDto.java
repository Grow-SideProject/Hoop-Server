package com.hoop.api.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String fileName;
    private String filePath;

    @Builder
    public FileDto(Long id, String fileName, String filePath ) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
    }


}