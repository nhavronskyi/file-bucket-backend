package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.nhavronskyi.filebucketbackend.entities.files.S3File;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.nhavronskyi.filebucketbackend.props.AwsS3Props;
import org.nhavronskyi.filebucketbackend.service.AwsS3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Object;

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
        return getObjectsFromDirectory(dirId)
                .stream()
                .collect(Collectors.toMap(x -> x.key().replace(dirId + "/", ""), S3Object::size));
    }

    @Override
    public Long getDirectorySize(long dirId) {
        return getObjectsFromDirectory(dirId)
                .stream()
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
        return s3Client.deleteObject(builder -> builder.bucket(props.bucket())
                        .key(fileNamePattern(dirId, fileId)))
                .deleteMarker() ? FileStatus.DELETED : FileStatus.ERROR;
    }

    private List<S3Object> getObjectsFromDirectory(long dirId) {
        return s3Client.listObjectsV2(builder -> builder.bucket(props.bucket()))
                .contents()
                .stream()
                .filter(x -> x.key().startsWith(String.valueOf(dirId)))
                .toList();
    }

    private boolean saveHelper(byte[] file, String fileName) {
        try {
            s3Client.putObject(builder -> builder.bucket(props.bucket())
                    .key(fileName), RequestBody.fromBytes(file));
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
        return s3Client.getObject(builder -> builder.bucket(props.bucket())
                        .key(fileId))
                .readAllBytes();
    }
}
