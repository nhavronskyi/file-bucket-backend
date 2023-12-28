package org.nhavronskyi.filebucketbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface VirusTotalService {
    String checkFile(MultipartFile file);
}
