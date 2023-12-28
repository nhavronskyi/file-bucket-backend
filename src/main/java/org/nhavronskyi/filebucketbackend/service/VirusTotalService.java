package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.service.impl.VirusTotalServiceImpl;
import org.springframework.web.multipart.MultipartFile;

public interface VirusTotalService {
    VirusTotalServiceImpl.Analysis checkFile(MultipartFile file);
}
