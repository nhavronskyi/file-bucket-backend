package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AwsS3Service {
    Map<String, Long> getDirectoryFilesNamesAndSizes(long dirId);

    Long getDirectorySize(long dirId);

    SavingStatus save(MultipartFile file, long dirId);
}
