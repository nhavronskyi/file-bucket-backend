package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    SavingStatus save(MultipartFile file);
}
