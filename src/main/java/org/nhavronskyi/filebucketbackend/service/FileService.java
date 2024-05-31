package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.files.Analysis;
import org.nhavronskyi.filebucketbackend.entities.files.S3File;
import org.nhavronskyi.filebucketbackend.entities.files.S3SimpleFile;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    Analysis save(MultipartFile file);

    Analysis checkFile(MultipartFile file);

    List<S3SimpleFile> getAllFiles();

    S3File getFile(String key);

    FileStatus deleteFile(String key);
}
