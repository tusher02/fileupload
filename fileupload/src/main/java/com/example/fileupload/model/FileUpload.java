package com.example.fileupload.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Entity
@Table(name = "file_upload")
@Component
public class FileUpload implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String ownedBY;
    private String description;

    @Lob
    private byte[] file;

    @Column(name = "upload_dir")
    private String uploadDir;
}
