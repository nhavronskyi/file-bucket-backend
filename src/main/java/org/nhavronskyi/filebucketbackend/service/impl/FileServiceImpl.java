package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.Analysis;
import org.nhavronskyi.filebucketbackend.entities.S3File;
import org.nhavronskyi.filebucketbackend.service.FileService;
import org.nhavronskyi.filebucketbackend.service.UserService;
import org.nhavronskyi.filebucketbackend.service.VirusTotalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final VirusTotalService virusTotalService;
    private final AwsS3ServiceImpl awsS3Service;

    @Override
    public Analysis save(MultipartFile file) {
        var analysis = virusTotalService.checkFile(file);

        if (analysis.getMalicious() != 0 || analysis.getSuspicious() != 0) {
            return analysis;
        }

        awsS3Service.save(file, UserService.getCurrentUser().getId());
        return analysis;
    }

    @Override
    public Analysis checkFile(MultipartFile file) {
        return virusTotalService.checkFile(file);
    }

    @Override
    public List<S3File> getAllFiles() {
        return awsS3Service.getDirectoryFilesNamesAndSizes(UserService.getCurrentUser().getId())
                .entrySet()
                .stream()
                .map(o -> new S3File(o.getKey(), o.getValue()))
                .toList();
    }
}
