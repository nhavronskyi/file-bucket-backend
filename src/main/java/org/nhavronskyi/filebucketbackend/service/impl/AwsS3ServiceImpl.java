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
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsS3ServiceImpl implements AwsS3Service {
    private final S3Client s3Client;
    private final AwsS3Props props;
    private String fileName;

    @Override
    public Map<String, Long> getDirectoryFilesNamesAndSizes(long dirId) {
        var directory = ListObjectsV2Request.builder()
                .bucket(props.bucket())
                .build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(directory);

        List<S3Object> contents = listObjectsV2Response.contents();

        return contents.stream()
                .filter(x -> x.key().startsWith(String.valueOf(dirId)))
                .collect(Collectors.toMap(x -> x.key().replace(dirId + "/", ""), S3Object::size));
    }

    @Override
    public Long getDirectorySize(long dirId) {
        var directory = ListObjectsV2Request.builder()
                .bucket(props.bucket())
                .build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(directory);

        List<S3Object> contents = listObjectsV2Response.contents();
        return contents.stream()
                .filter(x -> x.key().startsWith(String.valueOf(dirId)))
                .mapToLong(S3Object::size)
                .sum();
    }

    @Override
    @SneakyThrows
    public SavingStatus save(MultipartFile file, long dirId) {
        fileNameGenerator(file, dirId);
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

    private void fileNameGenerator(MultipartFile file, long dirId) {
        fileName = dirId + "/" + file.getOriginalFilename();
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
