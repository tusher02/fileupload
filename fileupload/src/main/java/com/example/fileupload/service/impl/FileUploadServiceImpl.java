package com.example.fileupload.service.impl;

import com.example.fileupload.exception.FileStorageException;
import com.example.fileupload.model.FileUpload;
import com.example.fileupload.repository.FileUploadRepository;
import com.example.fileupload.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final FileUploadRepository fileUploadRepository;
    private final FileUpload fileUpload;
    private final Path uploadLocation;

    public FileUploadServiceImpl(FileUpload fileUpload,
                                 Path uploadLocation,
                                 FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
        this.fileUpload = fileUpload;
        this.uploadLocation = Paths.get(fileUpload.getUploadDir())
                                    .toAbsolutePath()
                                    .normalize();
        try {
            Files.createFile(this.uploadLocation);
        }
        catch (Exception ex) {
            throw new FileStorageException("Could not create directory!", ex);
        }
    }

    @Override
    public FileUpload uploadFile(String ownedBy, String description, MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path targetLocation = this.uploadLocation.resolve(originalFileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileUpload theFile = new FileUpload();
        theFile.setOwnedBY(ownedBy);
        theFile.setDescription(description);
        theFile.setType(file.getContentType());
        theFile.setName(originalFileName);
        try {
            theFile.setFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUploadRepository.save(theFile);
    }
}
