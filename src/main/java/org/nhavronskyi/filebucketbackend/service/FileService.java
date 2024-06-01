package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.files.Analysis;
import org.nhavronskyi.filebucketbackend.entities.files.S3SimpleFile;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    Analysis save(MultipartFile file);

    Analysis checkFile(MultipartFile file);

    List<S3SimpleFile> getAllFiles();

    ResponseEntity<InputStreamResource> getFile(String key);

    FileStatus deleteFile(String key);
}
