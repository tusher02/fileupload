package com.example.fileupload.controller;

import com.example.fileupload.model.FileUpload;
import com.example.fileupload.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    public ResponseEntity<FileUpload> uploadFile (
            @RequestParam("ownedBy") String ownedBy,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file
    ) {
        FileUpload theFile =  fileUploadService.uploadFile(ownedBy, description, file);
        return new ResponseEntity<>(theFile, HttpStatus.OK);
    }
}
