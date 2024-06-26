package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AwsS3Service {
    Map<String, Long> getDirectoryFilesNamesAndSizes(long dirId);

    Long getDirectorySize(long dirId);

    FileStatus save(MultipartFile file, long dirId);

    ResponseEntity<InputStreamResource> getFile(long dirId, String fileId);

    FileStatus deleteFile(long dirId, String fileId);

}
