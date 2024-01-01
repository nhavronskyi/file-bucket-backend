package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.nhavronskyi.filebucketbackend.props.AwsS3Props;
import org.nhavronskyi.filebucketbackend.service.AwsS3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsS3ServiceImpl implements AwsS3Service {
    private final S3Client s3Client;
    private final AwsS3Props props;
    private String fileName;


    @Override
    @SneakyThrows
    public SavingStatus save(MultipartFile file) {
        fileNameGenerator(file);
        return saveHelper(file.getBytes()) ? contains() : SavingStatus.ERROR;
    }

    private boolean saveHelper(byte[] file) {
        try {
            var putObject = PutObjectRequest.builder()
                    .bucket(props.bucket())
                    .key(fileName)
                    .build();

            s3Client.putObject(putObject, RequestBody.fromBytes(file));
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    private void fileNameGenerator(MultipartFile file) {
        fileName = file.getOriginalFilename();
    }

    private SavingStatus contains() {
        try {
            var getObject = GetObjectRequest.builder()
                    .bucket(props.bucket())
                    .key(fileName)
                    .build();

            s3Client.getObject(getObject);
            return SavingStatus.SAVED;
        } catch (NoSuchKeyException e) {
            return SavingStatus.NO_SUCH_KEY;
        }
    }
}
