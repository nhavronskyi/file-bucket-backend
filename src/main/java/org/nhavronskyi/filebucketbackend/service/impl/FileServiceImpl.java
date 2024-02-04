package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.Analysis;
import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.nhavronskyi.filebucketbackend.service.FileService;
import org.nhavronskyi.filebucketbackend.service.VirusTotalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final VirusTotalService virusTotalService;
    private final AwsS3ServiceImpl awsS3Service;

    @Override
    public SavingStatus save(MultipartFile file, long userId) {
        var analysis = virusTotalService.checkFile(file);

        if (analysis.getMalicious() != 0 || analysis.getSuspicious() != 0) {
            return SavingStatus.MALICIOUS;
        }

        return awsS3Service.save(file, userId);
    }

    @Override
    public Analysis checkFile(MultipartFile file) {
        return virusTotalService.checkFile(file);
    }
}
