package com.example.fileupload.service;

import com.example.fileupload.model.FileUpload;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    FileUpload uploadFile(String ownedBy, String description, MultipartFile file);
}
