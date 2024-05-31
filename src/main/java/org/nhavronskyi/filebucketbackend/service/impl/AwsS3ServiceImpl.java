package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.nhavronskyi.filebucketbackend.entities.S3File;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.nhavronskyi.filebucketbackend.props.AwsS3Props;
import org.nhavronskyi.filebucketbackend.service.AwsS3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsS3ServiceImpl implements AwsS3Service {
    private final S3Client s3Client;
    private final AwsS3Props props;

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
    public FileStatus save(MultipartFile file, long dirId) {
        var fileName = fileNamePattern(dirId, file.getOriginalFilename());
        if (saveHelper(file.getBytes(), fileName)) {
            try {
                getObjectHelper(fileName);
                return FileStatus.SAVED;
            } catch (NoSuchKeyException e) {
                return FileStatus.NO_SUCH_KEY;
            } catch (IOException e) {
                return FileStatus.ERROR;
            }
        }
        return FileStatus.ERROR;
    }

    @SneakyThrows
    @Override
    public S3File getFile(long dirId, String fileId) {
        byte[] content = getObjectHelper(fileNamePattern(dirId, fileId));
        return new S3File(fileId, content);
    }

    @Override
    public FileStatus deleteFile(long dirId, String fileId) {
        var deleteStatus = s3Client.deleteObject(DeleteObjectRequest.builder().build());
        return deleteStatus.deleteMarker() ? FileStatus.DELETED : FileStatus.ERROR;
    }

    private boolean saveHelper(byte[] file, String fileName) {
        try {
            var putObject = PutObjectRequest.builder()
                    .bucket(props.bucket())
                    .key(fileName)
                    .build();

            s3Client.putObject(putObject, RequestBody.fromBytes(file));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private String fileNamePattern(long dirId, String fileName) {
        return "%s/%s".formatted(dirId, fileName);
    }

    private byte[] getObjectHelper(String fileId) throws IOException {
        var getObject = GetObjectRequest.builder()
                .bucket(props.bucket())
                .key(fileId)
                .build();

        return s3Client.getObject(getObject).readAllBytes();
    }
}
