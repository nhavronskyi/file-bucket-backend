package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.Analysis;
import org.nhavronskyi.filebucketbackend.entities.S3File;
import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    SavingStatus save(MultipartFile file);

    Analysis checkFile(MultipartFile file);

    List<S3File> getAllFiles();
}
