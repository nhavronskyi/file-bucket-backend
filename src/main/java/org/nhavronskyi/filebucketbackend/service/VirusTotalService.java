package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.Analysis;
import org.springframework.web.multipart.MultipartFile;

public interface VirusTotalService {
    Analysis checkFile(MultipartFile file);
}
